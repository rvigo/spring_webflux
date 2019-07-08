package com.apirest.webflux.service;

import static com.apirest.webflux.composer.TweetComposer.createTweet;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Example.of;
import static reactor.core.publisher.Flux.just;
import static reactor.test.StepVerifier.create;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.apirest.webflux.document.Tweet;
import com.apirest.webflux.repository.TweetRepository;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
public class TweetServiceImplTest {

	@Mock
	private TweetRepository repo;

	@InjectMocks
	private TweetServiceImpl service;

	@Test
	public void shouldReturnAFluxOfAllTweets() {
		when(repo.findAll()).thenReturn(just(createTweet(), createTweet()));
		create(service.findAll()).assertNext(t -> assertTrue(t instanceof Tweet));
	}

	@Test
	public void shouldReturnAFluxOfAllTweetsByUsername() {
		Tweet tweet = createTweet();
		tweet.setUser("testUserForThisTest");

		when(repo.findAll(of(tweet))).thenReturn(just(new Tweet("testUserForThisTest", "message 1"), new Tweet("testUserForThisTest", "message 2")));
		create(service.findAllByUser(tweet.getUser()))
			.assertNext(t -> assertTrue(t instanceof Tweet))
			.assertNext(t -> t.getUser().equals(tweet.getUser()));
	}

	@Test
	public void shouldReturnAMonoWhenSearchById() {
		Tweet tweet = createTweet();
		when(repo.findById(tweet.getId())).thenReturn(Mono.just(tweet));
		create(service.findById(tweet.getId()))
			.assertNext(t -> assertTrue(t instanceof Tweet))
			.assertNext(t -> t.getId().equals(tweet.getId()));
	}

	@Test
	public void shouldSaveATweetAndThenReturnAMono() {
		Tweet tweet = createTweet();
		when(repo.save(Mockito.any(Tweet.class))).thenReturn(Mono.just(tweet));
		create(service.save(tweet))
			.assertNext(t -> assertTrue(t instanceof Tweet))
			.assertNext(t -> t.getId().equals(tweet.getId()))
			.assertNext(t -> t.getUser().equals(tweet.getUser()));
	}
}
