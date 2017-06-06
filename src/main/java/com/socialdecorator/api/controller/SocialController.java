package com.socialdecorator.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialdecorator.api.component.enteties.enteties_search.ActivitySearch;
import com.socialdecorator.api.component.enteties.enteties_search.CommentSearch;
import com.socialdecorator.api.component.enteties.enteties_search.UserSearch;
import com.socialdecorator.api.component.enteties.enteties_social.Activity;
import com.socialdecorator.api.component.enteties.enteties_social.Comment;
import com.socialdecorator.api.component.enteties.enteties_social.User;
import com.socialdecorator.api.service.SocialService;

@RestController
public class SocialController {
	@Autowired
	private SocialService socialService;

	@RequestMapping(value = "/{social}/getUsers")
	public List<User> getUser(@PathVariable(value = "social") String social,
			@RequestParam(value = "site", required = false) String site,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "numberOfPages", required = false) Integer numberOfPages) throws Exception {
		return socialService.getUser(social, new UserSearch(Optional.ofNullable(site).orElse(""),
				Optional.ofNullable(name).orElse(""), Optional.ofNullable(numberOfPages).orElse(0)));
	}

	@RequestMapping(value = "/{social}/getActivities")
	public List<Activity> getActivity(@PathVariable(value = "social") String social,
			@RequestParam(value = "user", required = false) String user,
			@RequestParam(value = "site", required = false) String site,
			@RequestParam(value = "numberOfPages", required = false) Integer numberOfPages,
			@RequestParam(value = "text", required = false) String text) throws Exception {
		return socialService.getActivity(social,
				new ActivitySearch(Optional.ofNullable(user).orElse(""), Optional.ofNullable(site).orElse(""),
						Optional.ofNullable(numberOfPages).orElse(0), Optional.ofNullable(text).orElse("")));
	}

	@RequestMapping(value = "/{social}/getComments")
	public List<Comment> getComment(@PathVariable(value = "social") String social,
			@RequestParam(value = "user", required = false) String user,
			@RequestParam(value = "repository", required = false) String repository,
			@RequestParam(value = "site", required = false) String site,
			@RequestParam(value = "numberOfPages", required = false) Integer numberOfPages,
			@RequestParam(value = "text", required = false) String text) throws Exception {
		return socialService.getComment(social,
				new CommentSearch(Optional.ofNullable(user).orElse(""), Optional.ofNullable(repository).orElse(""),
						Optional.ofNullable(site).orElse(""), Optional.ofNullable(numberOfPages).orElse(0),
						Optional.ofNullable(text).orElse("")));
	}

}
