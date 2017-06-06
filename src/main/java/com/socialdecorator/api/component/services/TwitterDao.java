package com.socialdecorator.api.component.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.socialdecorator.api.component.enteties.enteties_search.ActivitySearch;
import com.socialdecorator.api.component.enteties.enteties_search.CommentSearch;
import com.socialdecorator.api.component.enteties.enteties_search.UserSearch;
import com.socialdecorator.api.component.enteties.enteties_social.Activity;
import com.socialdecorator.api.component.enteties.enteties_social.Comment;
import com.socialdecorator.api.component.enteties.enteties_social.User;
import com.socialdecorator.api.component.interfaces.Social;
import com.socialdecorator.api.configuration.TwitterConfiguration;
import com.socials.api.twitter.component.services.TwitterAuthorization;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Access to Twitter API data.
 * 
 * @author alex_alokhin
 *
 */
@EnableConfigurationProperties(TwitterConfiguration.class)
@Service
public class TwitterDao implements Social {
	private static final Logger LOGGER = Logger.getLogger(TwitterDao.class);
	@Autowired
	private TwitterConfiguration twitterConfiguration;
	@Autowired
	private TwitterAuthorization twitterAuthorization;
	private int pageNumber;
	private Twitter twitter;
	private ResponseList<Status> tempRetweets, tempTweets;
	private Paging page;
	private ResponseList<twitter4j.User> tempUsers;
	private twitter4j.User userTemp;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public List<User> getUser(UserSearch user) throws Exception {
		List<User> users = new ArrayList<>();
		pageNumber = 0;
		authorizeClient();
		do {
			if (isFullRateLimitStatus(twitter)){
				LOGGER.error("Rate limit exceed.");
				return users;
			}
				
			if (isFullRateLimitEndpoint(twitter, "users")){
				LOGGER.error("Rate limit exceed.");
				return users;}
			tempUsers = null;
			tempUsers = twitter.searchUsers(user.getName(), ++pageNumber);
			if (tempUsers == null)
				break;
			for (twitter4j.User userT : tempUsers) {
				users.add(new User((int) userT.getId(), userT.getName(),
						Optional.ofNullable(userT.getLang()).orElse(""), userT.getLocation(), userT.getFollowersCount(),
						userT.getFavouritesCount(), userT.getURL(), sdf.format(userT.getCreatedAt())));
			}
		} while (pageNumber < user.getNumberOfPages());
		return users;
	}

	@Override
	public List<Comment> getComment(CommentSearch comment) throws Exception {
		List<Comment> retweets = new ArrayList<>();
		pageNumber = 0;
		authorizeClient();
		do {
			if (isFullRateLimitStatus(twitter)){
				LOGGER.error("Rate limit exceed.");
				return retweets;
			}
			if (isFullRateLimitEndpoint(twitter, "statuses")){
				LOGGER.error("Rate limit exceed.");
				return retweets;
			}
			tempTweets = null;
			tempRetweets = null;
			page = new Paging(++pageNumber, 20);
			tempTweets = twitter.getUserTimeline(comment.getUser(), page);
			if (tempTweets == null)
				break;
			for (Status tweetTemp : tempTweets) {
				if (isFullRateLimitStatus(twitter)){
					LOGGER.error("Rate limit exceed.");
					return retweets;
				}
				if (isFullRateLimitEndpoint(twitter, "statuses")){
					LOGGER.error("Rate limit exceed.");
					return retweets;
				}
				tempRetweets = twitter.getRetweets(tweetTemp.getId());
				if (tempRetweets == null)
					break;
				for (Status retweetTemp : tempRetweets) {
					userTemp = retweetTemp.getUser();
					retweets.add(new Comment((int) retweetTemp.getId(), retweetTemp.getText(), retweetTemp.getLang(),
							new User((int) userTemp.getId(), userTemp.getName(), userTemp.getLang(),
									userTemp.getLocation(), userTemp.getFollowersCount(), userTemp.getFavouritesCount(),
									userTemp.getURL(), sdf.format(userTemp.getCreatedAt())),
							retweetTemp.getInReplyToScreenName(), retweetTemp.getFavoriteCount(),
							sdf.format(retweetTemp.getCreatedAt())));
				}
			}
		} while (pageNumber < comment.getNumberOfPages());
		return retweets;
	}

	@Override
	public List<Activity> getActivity(ActivitySearch activity) throws Exception {
		List<Activity> tweets = new ArrayList<>();
		pageNumber = 0;
		authorizeClient();
		do {
			tempTweets = null;
			if (isFullRateLimitStatus(twitter)){
				LOGGER.error("Rate limit exceed.");
				return tweets;
			}
			if (isFullRateLimitEndpoint(twitter, "statuses")){
				LOGGER.error("Rate limit exceed.");
				return tweets;
			}
			page = new Paging(++pageNumber, 100);
			tempTweets = twitter.getUserTimeline(activity.getUser(), page);
			if (tempTweets == null)
				break;
			for (Status activityTemp : tempTweets) {
				userTemp = activityTemp.getUser();
				tweets.add(new Activity((int) activityTemp.getId(), activityTemp.getText(), activityTemp.getLang(),
						new User((int) userTemp.getId(), userTemp.getName(), userTemp.getLang(), userTemp.getLocation(),
								userTemp.getFollowersCount(), userTemp.getFavouritesCount(), userTemp.getURL(),
								sdf.format(userTemp.getCreatedAt())),
						activityTemp.getInReplyToScreenName(), activityTemp.getFavoriteCount(),
						sdf.format(activityTemp.getCreatedAt())));
			}
		} while (pageNumber < activity.getNumberOfPages());
		return tweets;
	}

	private Boolean isFullRateLimitEndpoint(Twitter twitter, String endpoint) throws TwitterException {
		return twitter.getRateLimitStatus(endpoint).entrySet().stream()
				.filter(key -> key.getValue().getRemaining() <= 1).findFirst().orElse(null) != null;
	}

	//180 requests per 15 min on getting rate limit
	private Boolean isFullRateLimitStatus(Twitter twitter) throws Exception {
		return twitter.getRateLimitStatus().get("/application/rate_limit_status").getRemaining() <= 2;
	}

	private void authorizeClient() {
		twitter = twitterAuthorization.authorize(twitterConfiguration.getConsumerKey(),
				twitterConfiguration.getConsumerSecret(), twitterConfiguration.getAccessToken(),
				twitterConfiguration.getAccessSecret());
	}
}
