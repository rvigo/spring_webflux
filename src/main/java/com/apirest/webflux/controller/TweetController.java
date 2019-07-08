package com.apirest.webflux.controller;

import static java.time.Duration.ofMillis;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.webflux.document.Tweet;
import com.apirest.webflux.service.TweetService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tweet")
public class TweetController {

	@Autowired
	private TweetService service;

	@GetMapping
	public Flux<Tweet> getAllTweets() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Mono<Tweet> getTweetById(@PathVariable String id) {
		return service.findById(id);
	}

	@GetMapping("/user/{user}")
	public Flux<Tweet> getTweetByUser(@PathVariable String user) {
		return service.findAllByUser(user);
	}

	@PostMapping
	public Mono<Tweet> saveTweet(@RequestBody Tweet playlist) {
		return service.save(playlist);
	}

	@GetMapping(value = "/events/user/{user}", produces = TEXT_EVENT_STREAM_VALUE)
	public Flux<Tweet> getPlaylistByEvents(@PathVariable String user) {
		return service.findAllByUser(user).delayElements(ofMillis(500));
	}

}
