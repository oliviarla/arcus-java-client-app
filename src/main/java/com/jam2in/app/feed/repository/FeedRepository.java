package com.jam2in.app.feed.repository;

import com.jam2in.app.feed.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
	List<Feed> findTop10ByOrderByCreatedAtDesc();
}
