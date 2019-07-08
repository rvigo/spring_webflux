package com.apirest.webflux.service;

import static org.springframework.data.domain.Example.of;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.webflux.document.Tweet;
import com.apirest.webflux.repository.TweetRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TweetServiceImpl implements TweetService {

	@Autowired
	private TweetRepository tr;

	@Override
	public Flux<Tweet> findAll() {
		return tr.findAll();
	}

	@Override
	public Mono<Tweet> findById(String id) {
		return tr.findById(id);
	}

	@Override
	public Mono<Tweet> save(Tweet tweet) {
		tweet.setTimeStamp(LocalDate.now());
		return tr.save(tweet);
	}

	@Override
	public Flux<Tweet> findAllByUser(String user) {
		return tr.findAll(of(new Tweet(user, null)));
	}

}
