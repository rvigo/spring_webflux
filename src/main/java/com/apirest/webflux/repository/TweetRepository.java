package com.apirest.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.apirest.webflux.document.Tweet;

public interface TweetRepository extends ReactiveMongoRepository<Tweet, String> {

}
