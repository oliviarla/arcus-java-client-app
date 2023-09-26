package com.jam2in.app.feed.controller;

import com.jam2in.app.feed.domain.FeedResponse;
import com.jam2in.app.feed.domain.FeedRequest;
import com.jam2in.app.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

	private final FeedService feedService;
	@GetMapping("/all")
	public List<FeedResponse> getAll() {
		return feedService.getFeeds();
	}

	@GetMapping("/{id}")
	public FeedResponse get(@PathVariable Long id) {
		return feedService.getFeed(id);
	}

	@PostMapping("/add")
	public Long addFeed(@RequestBody FeedRequest feedRequest) {
		return feedService.addFeed(feedRequest);
	}
}
