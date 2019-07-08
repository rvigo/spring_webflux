package com.apirest.webflux.service;

import com.apirest.webflux.document.Tweet;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TweetService {
	Flux<Tweet> findAll();

	Mono<Tweet> findById(String id);

	Flux<Tweet> findAllByUser(String user);

	Mono<Tweet> save(Tweet tweet);

}
