package com.socialdecorator.api.configuration;

import org.springframework.stereotype.Repository;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
/**
 * Makes authorization for Twitter API
 * 
 * @author alex_alokhin
 */
@Repository
public class TwitterAuthorization {

	public Twitter authorize(String consumerKey, String consumerSecret, String accessToken, String accessSecret) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf.getInstance();
	}
}
