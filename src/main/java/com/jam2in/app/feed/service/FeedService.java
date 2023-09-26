package com.jam2in.app.feed.service;

import com.jam2in.app.feed.domain.FeedRequest;
import com.jam2in.app.feed.domain.FeedResponse;

import java.util.List;

public interface FeedService {
	/**
	 * 최신 피드를 10개만 조회한다.
	 */
	List<FeedResponse> getFeeds();

	/**
	 * 피드 ID에 해당하는 피드를 조회한다.
	 */
	FeedResponse getFeed(Long feedId);

	/**
	 * 피드를 새로 생성한다.
	 */
	Long addFeed(FeedRequest feedRequest);
}
