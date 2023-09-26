package com.jam2in.app.feed.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public record FeedResponse(Long id, Long userId, String content, LocalDateTime createdAt) implements Serializable {
	private static final long serialVersionUID = 31562;
	public static FeedResponse of(Feed feed) {
		return new FeedResponse(feed.getId(), feed.getUserId(), feed.getContent(), feed.getCreatedAt());
	}
}
