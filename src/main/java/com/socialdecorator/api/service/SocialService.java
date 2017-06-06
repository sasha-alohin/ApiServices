package com.socialdecorator.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialdecorator.api.component.enteties.enteties_search.ActivitySearch;
import com.socialdecorator.api.component.enteties.enteties_search.CommentSearch;
import com.socialdecorator.api.component.enteties.enteties_search.UserSearch;
import com.socialdecorator.api.component.enteties.enteties_social.Activity;
import com.socialdecorator.api.component.enteties.enteties_social.Comment;
import com.socialdecorator.api.component.enteties.enteties_social.User;
import com.socialdecorator.api.component.interfaces.Social;
import com.socialdecorator.api.component.services.GitHubDao;
import com.socialdecorator.api.component.services.GooglePlusDao;
import com.socialdecorator.api.component.services.StackOverflowDao;
import com.socialdecorator.api.component.services.TwitterDao;

@Service
public class SocialService {

	private Social social;

	@Autowired
	private GitHubDao github;
	@Autowired
	private StackOverflowDao stack;
	@Autowired
	private TwitterDao twitter;
	@Autowired
	private GooglePlusDao google;

	public List<User> getUser(String socialName, UserSearch userSearch) throws Exception {
		UserSearch user = null;
		switch (socialName.toLowerCase()) {
		case "github":
			social = github;
			user = new UserSearch("", userSearch.getName(), userSearch.getNumberOfPages());
			break;
		case "stack":
			social = stack;
			user = new UserSearch(userSearch.getSite(), "", userSearch.getNumberOfPages());
			break;
		case "twitter":
			social = twitter;
			user = new UserSearch("", userSearch.getName(), userSearch.getNumberOfPages());
			break;
		case "google":
			social = google;
			user = new UserSearch("", userSearch.getName(), userSearch.getNumberOfPages());
			break;
		default:
			social = null;
			break;
		}
		return social.getUser(user);
	}

	public List<Comment> getComment(String socialName, CommentSearch commentSearch) throws Exception {
		CommentSearch comment = null;
		switch (socialName.toLowerCase()) {
		case "github":
			social = github;
			comment = new CommentSearch(commentSearch.getUser(), commentSearch.getRepository(), "",
					commentSearch.getNumberOfPages(), "");
			break;
		case "stack":
			social = stack;
			comment = new CommentSearch("", "", commentSearch.getSite(), commentSearch.getNumberOfPages(), "");
			break;
		case "twitter":
			social = twitter;
			comment = new CommentSearch(commentSearch.getUser(), "", "", commentSearch.getNumberOfPages(), "");
			break;
		case "google":
			social = google;
			comment = new CommentSearch("", "", "", commentSearch.getNumberOfPages(), commentSearch.getText());
			break;
		default:
			social = null;
			break;
		}
		return social.getComment(comment);
	}

	public List<Activity> getActivity(String socialName, ActivitySearch activitySearch) throws Exception {
		ActivitySearch activity = null;
		switch (socialName.toLowerCase()) {
		case "github":
			social = github;
			break;
		case "stack":
			social = stack;
			activity = new ActivitySearch("", activitySearch.getSite(), activitySearch.getNumberOfPages(), "");
			break;
		case "twitter":
			social = twitter;
			activity = new ActivitySearch(activitySearch.getUser(), "", activitySearch.getNumberOfPages(), "");
			break;
		case "google":
			social = google;
			activity = new ActivitySearch("", "", activitySearch.getNumberOfPages(), activitySearch.getText());
			break;
		default:
			social = null;
			break;
		}
		return social.getActivity(activity);
	}

}
