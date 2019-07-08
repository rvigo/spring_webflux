package com.apirest.webflux.controller;

import static com.apirest.webflux.composer.TweetComposer.createTweet;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_UTF8;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;
import static reactor.core.publisher.Flux.just;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.apirest.webflux.WebfluxApplication;
import com.apirest.webflux.document.Tweet;
import com.apirest.webflux.repository.TweetRepository;
import com.apirest.webflux.service.TweetServiceImpl;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(TweetController.class)
@ContextConfiguration(classes = WebfluxApplication.class)
public class TweetControllerTest {

	@Autowired
	private WebTestClient client;

	@MockBean
	private TweetServiceImpl service;

	@Mock
	private TweetRepository repo;

	@Test
	public void shouldFindAllTweetsByUser() {
		Tweet tweet = createTweet();
		Mockito.when(service.findAllByUser(Mockito.anyString())).thenReturn(just(tweet));
		client.get()
			.uri("/tweet/user/".concat(tweet.getUser()))
			.exchange()
			.expectStatus()
			.isOk()
			.expectBodyList(Tweet.class)
			.value(t -> t.get(0).getUser().equals(tweet.getUser()));
	}

	@Test
	public void shouldFindAllTweets() {
		Tweet tweet = createTweet();
		Mockito.when(service.findAll()).thenReturn(just(tweet));
		client.get()
			.uri("/tweet")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBodyList(Tweet.class);
	}
	
	@Test
	public void shouldFindAllTweetsById() {
		Tweet tweet = createTweet();
		Mockito.when(service.findById(Mockito.anyString())).thenReturn(Mono.just(tweet));
		client.get()
			.uri("/tweet/".concat(tweet.getId()))
			.exchange()
			.expectStatus()
			.isOk()
			.expectBodyList(Tweet.class)
			.value(t -> t.get(0).getId().equals(tweet.getId()));
	}
	
	@Test
	public void shouldCreateANewTweet() {
		Tweet tweet = createTweet();
		tweet.setId(null);
		tweet.setTimeStamp(null);
		Mockito.when(service.findAllByUser(Mockito.anyString())).thenReturn(just(tweet));
		client.post()
			.uri("/tweet")
			.contentType(APPLICATION_PROBLEM_JSON_UTF8)
			.body(Mono.just(tweet), Tweet.class)
			.exchange()
			.expectStatus().isOk()
			.expectBody(Tweet.class);
	}

	@Test
	public void shouldFindAllTweetsByUserInAnEventStream() {
		Tweet tweet = createTweet();
		Mockito.when(service.findAllByUser(Mockito.anyString())).thenReturn(just(tweet));
		client.get()
			.uri("/tweet//events/user/".concat(tweet.getUser()))
			.exchange()
			.expectStatus()
			.isOk()
			.expectBodyList(Tweet.class).value(t -> t.get(0).getUser().equals(tweet.getUser()))
			.consumeWith(c -> c.getResponseHeaders().getContentType().toString().equals(TEXT_EVENT_STREAM_VALUE));
	}
}
