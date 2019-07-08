package com.apirest.webflux.composer;

import static java.time.LocalDate.now;

import com.apirest.webflux.document.Tweet;

public class TweetComposer {

	public static Tweet createTweet() {
		Tweet tweet = new Tweet("testuser", "hello");
		tweet.setId("5d224ba8cc76fe2c08c3a0ba");
		tweet.setTimeStamp(now());
		return tweet;
	}

}
