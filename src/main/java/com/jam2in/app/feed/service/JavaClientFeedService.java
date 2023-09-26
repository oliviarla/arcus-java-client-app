package com.jam2in.app.feed.service;

import com.jam2in.app.feed.domain.FeedResponse;
import com.jam2in.app.feed.exception.FailedToCacheException;
import com.jam2in.app.feed.repository.FeedRepository;
import com.jam2in.app.feed.domain.Feed;
import com.jam2in.app.feed.domain.FeedRequest;
import lombok.RequiredArgsConstructor;
import net.spy.memcached.ArcusClientPool;
import net.spy.memcached.collection.CollectionAttributes;
import net.spy.memcached.collection.CollectionOverflowAction;
import net.spy.memcached.internal.CollectionFuture;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.ops.CollectionOperationStatus;
import net.spy.memcached.transcoders.CollectionTranscoder;
import net.spy.memcached.transcoders.Transcoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Caching With Arcus Java Client
 */
@Service
@RequiredArgsConstructor
@Transactional
public class JavaClientFeedService implements FeedService {
	private final FeedRepository feedRepository;
	private final ArcusClientPool arcusClientPool;

	private static final String RECENT_FEED_KEY = "DEV:RecentFeeds";
	private static final String FEED_KEY = "TEST:feed";

	public List<FeedResponse> getFeeds() {
		CollectionFuture<List<Object>> future;
		future = arcusClientPool.asyncLopGet(RECENT_FEED_KEY, 0, 10, false, false);
		if (future == null) {
			return getAndCacheFeeds();
		}

		try {
			List<Object> feedsFromCache = future.get(500L, TimeUnit.MILLISECONDS);
			if (feedsFromCache == null) {
				return getAndCacheFeeds();
			}
			return feedsFromCache.stream().map(o -> (FeedResponse) o).toList();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (TimeoutException | ExecutionException e) {
			future.cancel(true);
		}

		return getAndCacheFeeds();
	}

	private List<FeedResponse> getAndCacheFeeds() {
		List<FeedResponse> feeds = feedRepository.findTop10ByOrderByCreatedAtDesc().stream().map(FeedResponse::of).toList();
		cacheAll(feeds);
		return feeds;
	}

	@Override
	public FeedResponse getFeed(Long feedId) {

		GetFuture<Object> future = arcusClientPool.asyncGet(FEED_KEY + feedId);
		try {
			FeedResponse feedResponse = (FeedResponse) future.get(500, TimeUnit.MILLISECONDS);
			if (feedResponse == null) {
				return getAndCacheFeed(feedId);
			}
			return feedResponse;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (TimeoutException | ExecutionException e) {
			future.cancel(true);
		}

		return getAndCacheFeed(feedId);
	}

	private FeedResponse getAndCacheFeed(Long feedId) {
		Feed feed = feedRepository.findById(feedId).orElseThrow();
		FeedResponse feedResponse = FeedResponse.of(feed);
		arcusClientPool.set(FEED_KEY + feedId, 0, feedResponse);
		return feedResponse;
	}

	public Long addFeed(FeedRequest feedRequest) {
		Feed feed = feedRepository.save(feedRequest.toFeed());

		CollectionFuture<CollectionAttributes> future = arcusClientPool.asyncGetAttr(RECENT_FEED_KEY);
		CollectionAttributes attributes = null;
		try {
			 attributes = future.get(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (TimeoutException | ExecutionException e) {
			future.cancel(true);
		}

		if(attributes != null) {
			cacheAll(List.of(FeedResponse.of(feed)));
		} else {
			getAndCacheFeeds();
		}
		return feed.getId();
	}

	private <T> void cacheAll(List<T> list) {
		Transcoder<T> transcoder = (Transcoder<T>) new CollectionTranscoder();
		CollectionFuture<Map<Integer, CollectionOperationStatus>> future;
		future = arcusClientPool.asyncLopPipedInsertBulk(RECENT_FEED_KEY, 0, list,
				new CollectionAttributes(0, 10L, CollectionOverflowAction.tail_trim), transcoder);

		if (future == null) {
			throw new FailedToCacheException();
		}

		try {
			Map<Integer, CollectionOperationStatus> map = future.get(1000L, TimeUnit.MILLISECONDS);
			if (!CollectionUtils.isEmpty(map)) {
				throw new FailedToCacheException();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new FailedToCacheException();
		} catch (TimeoutException | ExecutionException e) {
			future.cancel(true);
			throw new FailedToCacheException();
		}
	}
}
