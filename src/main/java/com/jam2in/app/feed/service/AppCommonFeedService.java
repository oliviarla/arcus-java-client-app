package com.jam2in.app.feed.service;

import com.jam2in.app.feed.domain.Feed;
import com.jam2in.app.feed.domain.FeedRequest;
import com.jam2in.app.feed.domain.FeedResponse;
import com.jam2in.app.feed.repository.FeedRepository;
import com.jam2in.arcus.app.common.aop.ArcusCache;
import com.jam2in.arcus.app.common.aop.ArcusCacheKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Caching with Arcus App Common
 */
@Primary
@Service
@RequiredArgsConstructor
@Transactional
public class AppCommonFeedService implements FeedService {

	private final FeedRepository feedRepository;

	/**
	 * 현재 공통 모듈으로는 List 자료구조를 사용할 수 없다.
	 * 따라서 최신 피드를 조회하는 메서드에는 적합하지는 않다.
	 */
	@Override
	@ArcusCache(key = "RecentFeeds", expireTime = "60") // Global prefix가 있으므로 prefix 생략
	public List<FeedResponse> getFeeds() {
		return feedRepository.findTop10ByOrderByCreatedAtDesc().stream().map(FeedResponse::of).toList();
	}

	@Override
	@ArcusCache(expireTime = "86400")
	public FeedResponse getFeed(@ArcusCacheKey Long feedId) {
		Feed feed = feedRepository.findById(feedId).orElseThrow();
		return FeedResponse.of(feed);
	}

	@Override
	public Long addFeed(FeedRequest feedRequest) {
		Feed feed = feedRepository.save(feedRequest.toFeed());
		return feed.getId();
	}
}
