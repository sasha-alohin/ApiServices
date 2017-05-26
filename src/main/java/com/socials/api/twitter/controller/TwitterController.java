package com.socials.api.twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socials.api.twitter.services.TwitterService;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

@RequestMapping(value = "/twitter")
@RestController
public class TwitterController {

	@Autowired
	private TwitterService twitterService;

	/**
	 * Get user list by name
	 * 
	 * @param name
	 *            - name of user
	 * @param page
	 *            - number of page to search(20 records per page)
	 * @return user list
	 */
	@RequestMapping(value = "/users/{name}", method = RequestMethod.GET)
	public ResponseList<User> getUsers(@PathVariable(value = "name") String name,
			@RequestParam(value = "page") Integer page) throws TwitterException {
		return twitterService.getUsers(name, page);
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
	@RequestMapping(value = "/tweets/{text}", method = RequestMethod.GET)
	public List<Status> getTweets(@PathVariable(value = "text") String text,
			@RequestParam(value = "number") Integer numberOfTweets) throws TwitterException {
		return twitterService.getTweets(text, numberOfTweets);
	}

	/**
	 * Get favorites of authorize user
	 * 
	 * @return list of favorites
	 * @throws TwitterException
	 */
	@RequestMapping(value = "/favorites", method = RequestMethod.GET)
	public List<Status> getFavorites() throws TwitterException {
		return twitterService.getFavorites();
	}

}
