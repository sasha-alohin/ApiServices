package com.socialdecorator.api.component.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.github.scribejava.apis.StackExchangeApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.socialdecorator.api.component.enteties.enteties_search.ActivitySearch;
import com.socialdecorator.api.component.enteties.enteties_search.CommentSearch;
import com.socialdecorator.api.component.enteties.enteties_search.UserSearch;
import com.socialdecorator.api.component.enteties.enteties_social.Activity;
import com.socialdecorator.api.component.enteties.enteties_social.Comment;
import com.socialdecorator.api.component.enteties.enteties_social.User;
import com.socialdecorator.api.component.interfaces.Social;

/**
 * Access to StackExchange API data. With authentication up to 10 000 requests
 * per day per day. Without - only 300 requests
 * 
 * @author alex_alokhin
 *
 */
@Service
public class StackOverflowDao implements Social {
	private JSONObject json, owner, jsonObject;
	private JSONArray jsonArray;
	private final String baseUrl = "https://api.stackexchange.com/2.2/";
	private final String clientId = "9993";
	private final String clientSecret = "kbKq1EZ3V))NE6eeBDFVhQ((";
	private final String key = "vaTGr9kI7TpzmcyhVbMDKA((";
	private final String callback = " https://stackexchange.com/oauth/login_success";
	private OAuth20Service service;
	private OAuth2AccessToken accessToken;
	private OAuthRequest request;
	private Response response;
	private int pageNumber;

	@Override
	public List<User> getUser(UserSearch user) throws Exception {
		List<User> users = new ArrayList<>();
		pageNumber = 0;
		setAccessToken();
		do {
			if (isFullRateLimit(jsonObject))
				return users;
			request = new OAuthRequest(Verb.GET, baseUrl + "users?pagesize=100&order=desc&sort=reputation&site="
					+ user.getSite() + "&page=" + (++pageNumber) + "&key=" + key);
			service.signRequest(accessToken, request);
			response = service.execute(request);
			jsonObject = new JSONObject(response.getBody());
			if (jsonObject.getJSONArray("items").toList().isEmpty())
				return users;
			jsonArray = new JSONArray(jsonObject.get("items").toString());
			for (Object object : jsonArray) {
				json = new JSONObject(object.toString());
				if (json.has("age") && json.has("location")) {
					users.add(new User(json.getInt("user_id"), json.getString("display_name"), json.getString("link"),
							json.getString("user_type"), json.getInt("age"), json.getString("location"),
							json.getInt("reputation")));
				} else if (json.has("age")) {
					users.add(new User(json.getInt("user_id"), json.getString("display_name"), json.getString("link"),
							json.getString("user_type"), json.getInt("age"), json.getInt("reputation")));
				} else if (json.has("location")) {
					users.add(new User(json.getInt("user_id"), json.getString("display_name"), json.getString("link"),
							json.getString("user_type"), json.getString("location"), json.getInt("reputation")));
				} else {
					users.add(new User(json.getInt("user_id"), json.getString("display_name"), json.getString("link"),
							json.getString("user_type"), json.getInt("reputation")));
				}
			}
		} while (jsonObject.getBoolean("has_more") && pageNumber < user.getNumberOfPages());
		return users;
	}

	@Override
	public List<Comment> getComment(CommentSearch comment) throws Exception {
		List<Comment> comments = new ArrayList<>();
		pageNumber = 0;
		setAccessToken();
		do {
			if (isFullRateLimit(jsonObject))
				return comments;
			request = new OAuthRequest(Verb.GET, baseUrl + "comments?pagesize=100&order=desc&sort=creation&site="
					+ comment.getSite() + "&page=" + (++pageNumber) + "&key=" + key);
			service.signRequest(accessToken, request);
			response = service.execute(request);
			jsonObject = new JSONObject(response.getBody());
			if (jsonObject.getJSONArray("items").toList().isEmpty())
				return comments;
			jsonArray = new JSONArray(jsonObject.get("items").toString());
			generateComments(jsonArray, comments);
		} while (jsonObject.getBoolean("has_more") && pageNumber < comment.getNumberOfPages());
		return comments;
	}

	// Because of backoff 10 seconds aleep on each request
	@Override
	public List<Activity> getActivity(ActivitySearch activity) throws Exception {
		List<Activity> activities = new ArrayList<>();
		pageNumber = 0;
		setAccessToken();
		do {
			if (isFullRateLimit(jsonObject))
				return activities;
			request = new OAuthRequest(Verb.GET, baseUrl + "posts?pagesize=100&order=desc&sort=activity&site="
					+ activity.getSite() + "&page=" + (++pageNumber) + "&key=" + key);
			service.signRequest(accessToken, request);
			response = service.execute(request);
			jsonObject = new JSONObject(response.getBody());
			if (jsonObject.getJSONArray("items").toList().isEmpty())
				return activities;
			jsonArray = new JSONArray(jsonObject.get("items").toString());
			for (Object object : jsonArray) {
				json = new JSONObject(object.toString());
				if (json.has("owner")) {
					owner = json.getJSONObject("owner");
					activities.add(new Activity(json.getInt("post_id"), json.getString("post_type"),
							json.getString("link"), json.getInt("score"),
							new User(owner.getInt("user_id"), owner.getString("display_name"), owner.getString("link"),
									owner.getString("user_type"), owner.getInt("reputation"))));
				} else
					activities.add(new Activity(json.getInt("post_id"), json.getString("post_type"),
							json.getString("link"), json.getInt("score")));
			}
			Thread.sleep(10000);
		} while (jsonObject.getBoolean("has_more") && pageNumber < activity.getNumberOfPages());
		return activities;
	}

	public void generateComments(JSONArray jsonArray, List<Comment> comments) {
		JSONObject replyToUser;
		for (Object object : jsonArray) {
			json = new JSONObject(object.toString());
			owner = json.getJSONObject("owner");
			if (json.has("reply_to_user")) {
				replyToUser = json.getJSONObject("reply_to_user");
				comments.add(new Comment(json.getInt("comment_id"), json.getInt("post_id"),
						new User(owner.getInt("user_id"), owner.getString("display_name"), owner.getString("link"),
								owner.getString("user_type"), owner.getInt("reputation")),
						new User(replyToUser.getInt("user_id"), replyToUser.getString("display_name"),
								replyToUser.getString("link"), replyToUser.getString("user_type"),
								replyToUser.getInt("reputation")),
						json.getInt("score")));
			} else {
				comments.add(new Comment(json.getInt("comment_id"),
						json.getInt("post_id"), new User(owner.getInt("user_id"), owner.getString("display_name"),
								owner.getString("link"), owner.getString("user_type"), owner.getInt("reputation")),
						json.getInt("score")));
			}
		}
	}

	private Boolean isFullRateLimit(JSONObject jsonObject) {
		return jsonObject.getInt("quota_remaining") <= 2;
	}

	public void setAccessToken() throws Exception {
		service = new ServiceBuilder().apiKey(clientId).apiSecret(clientSecret).callback(callback)
				.build(StackExchangeApi.instance());
		final String authorizationUrl = service.getAuthorizationUrl();
		System.out.println("Got the Authorization URL!");
		System.out.println("Now go and authorize ScribeJava here:");
		System.out.println(authorizationUrl);
		System.out.println("And paste the authorization code here");
		System.out.print(">>");
		final String code = new Scanner(System.in, "UTF-8").nextLine();
		this.accessToken = service.getAccessToken(code);
	}

}
