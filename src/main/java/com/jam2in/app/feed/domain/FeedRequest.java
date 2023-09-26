package com.jam2in.app.feed.domain;


public record FeedRequest(Long userId, String content) {
	public Feed toFeed() {
		return new Feed(userId, content);
	}
}
