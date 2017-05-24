package com.softbistro.api.twitter.component.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Repository;

import com.softbistro.api.configuration.TwitterConfiguration;
import com.softbistro.api.twitter.component.interfaces.TwitterInterface;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

@EnableConfigurationProperties(TwitterConfiguration.class)
@Repository
public class TwitterDao implements TwitterInterface {

	@Autowired
	private TwitterConfiguration twitterConfiguration;

	@Autowired
	private TwitterAuthorization twitterAuthorization;

	/**
	 * Get user list by name
	 * 
	 * @param name
	 *            - name of user
	 * @param page
	 *            - number of page to search(20 records per page)
	 * @return user list
	 */
	@Override
	public ResponseList<User> getUsers(String name, Integer page) throws TwitterException {
		Twitter twitter = twitterAuthorization.authorize(twitterConfiguration.getConsumerKey(),
				twitterConfiguration.getConsumerSecret(), twitterConfiguration.getAccessToken(),
				twitterConfiguration.getAccessSecret());
		return twitter.searchUsers(name, page);
	}

	/**
	 * Get tweets by text or tag name(to search by tag use # sign before tag
	 * name)
	 * 
	 * @param text
	 *            - part of tweet
	 * @param numberOfTweets
	 *            - number of tweets to return (100 record max)
	 * @return - list of tweets
	 * @throws TwitterException
	 */
	@Override
	public List<Status> getTweets(String text, Integer numberOfTweets) throws TwitterException {
		Twitter twitter = twitterAuthorization.authorize(twitterConfiguration.getConsumerKey(),
				twitterConfiguration.getConsumerSecret(), twitterConfiguration.getAccessToken(),
				twitterConfiguration.getAccessSecret());
		Query query = new Query(text);
		query.count(numberOfTweets);
		QueryResult queryResultObject = twitter.search(query);
		return queryResultObject.getTweets();
	}

	/**
	 * Get favorites of authorize user
	 * 
	 * @return list of favorites
	 * @throws TwitterException
	 */
	@Override
	public List<Status> getFavorites() throws TwitterException {
		return twitterAuthorization
				.authorize(twitterConfiguration.getConsumerKey(), twitterConfiguration.getConsumerSecret(),
						twitterConfiguration.getAccessToken(), twitterConfiguration.getAccessSecret())
				.getFavorites();
	}
}
