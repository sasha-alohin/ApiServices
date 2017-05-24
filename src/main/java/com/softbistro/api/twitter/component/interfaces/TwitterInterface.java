package com.softbistro.api.twitter.component.interfaces;

import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

public interface TwitterInterface {

	/**
	 * Get favorites of authorize user
	 * 
	 * @return list of favorites
	 * @throws TwitterException
	 */
	public List<Status> getFavorites() throws TwitterException;

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
	public List<Status> getTweets(String text, Integer numberOfTweets) throws TwitterException;

	/**
	 * Get user list by name
	 * 
	 * @param name
	 *            - name of user
	 * @param page
	 *            - number of page to search(20 records per page)
	 * @return user list
	 */
	public ResponseList<User> getUsers(String name, Integer page) throws TwitterException;
}
