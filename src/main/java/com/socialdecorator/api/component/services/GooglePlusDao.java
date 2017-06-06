package com.socialdecorator.api.component.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.socialdecorator.api.component.enteties.enteties_search.ActivitySearch;
import com.socialdecorator.api.component.enteties.enteties_search.CommentSearch;
import com.socialdecorator.api.component.enteties.enteties_search.UserSearch;
import com.socialdecorator.api.component.enteties.enteties_social.Activity;
import com.socialdecorator.api.component.enteties.enteties_social.Comment;
import com.socialdecorator.api.component.enteties.enteties_social.User;
import com.socialdecorator.api.component.interfaces.Social;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;

/**
 * Access to GooglePlus API data. Up to 10 000 requests per day
 * 
 * @author alex_alokhin
 */
@Service
public class GooglePlusDao implements Social {
	private final String urlForUser = "https://www.googleapis.com/plus/v1/people?key=AIzaSyDVZr7PnltQU-7vVWqnaE5d-PWmBrRnjlA&maxResults=50&query=";
	private final String urlForComments = "https://www.googleapis.com/plus/v1/activities/";
	private final String urlForActivities = "https://www.googleapis.com/plus/v1/activities?key=AIzaSyDVZr7PnltQU-7vVWqnaE5d-PWmBrRnjlA&maxResults=20&query=";
	private int pageNumber;
	private String jsonText;
	private JSONObject json, jsonObject, owner;
	private JSONArray jsonArray;
	private String pageToken;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

	@Override
	public List<User> getUser(UserSearch user) throws Exception {
		List<User> users = new ArrayList<>();
		pageNumber = 0;
		pageToken = "";
		do {
			jsonArray = null;
			jsonText = readAll(urlForUser + user.getName() + "&pageToken=" + pageToken);
			json = new JSONObject(jsonText);
			if (json.getJSONArray("items").toList().isEmpty())
				return users;
			jsonArray = new JSONArray(json.get("items").toString());
			pageToken = json.getString("nextPageToken");
			for (Object object : jsonArray) {
				jsonObject = new JSONObject(object.toString());
				users.add(new User(jsonObject.getString("id"), jsonObject.getString("displayName"),
						jsonObject.getString("url"), jsonObject.getString("objectType"), jsonObject.getString("etag")));
			}
		} while (++pageNumber < user.getNumberOfPages() && pageToken != "");
		return users;
	}

	@Override
	public List<Comment> getComment(CommentSearch comment) throws Exception {
		String id;
		JSONObject commentsJson;
		JSONArray commentsArray;
		List<Comment> comments = new ArrayList<>();
		pageNumber = 0;
		pageToken = "";
		do {
			jsonArray = null;
			jsonText = readAll(urlForActivities + comment.getText() + "&pageToken=" + pageToken);
			json = new JSONObject(jsonText);
			if (json.getJSONArray("items").toList().isEmpty())
				return comments;
			jsonArray = new JSONArray(json.get("items").toString());
			pageToken = json.getString("nextPageToken");
			for (Object object : jsonArray) {
				jsonObject = new JSONObject(object.toString());
				id = jsonObject.getString("id");
				jsonText = readAll(
						urlForComments + id + "/comments?key=AIzaSyDVZr7PnltQU-7vVWqnaE5d-PWmBrRnjlA&maxResults=500");
				commentsJson = new JSONObject(jsonText);
				if (commentsJson.getJSONArray("items").toList().isEmpty())
					continue;
				commentsArray = new JSONArray(commentsJson.get("items").toString());
				for (Object objectComment : commentsArray) {
					jsonObject = new JSONObject(objectComment.toString());
					owner = jsonObject.getJSONObject("actor");
					comments.add(new Comment(jsonObject.getString("id"),
							jsonObject.getJSONObject("object").getString("content"), jsonObject.getString("selfLink"),
							jsonObject.getString("etag"), sdf.format(sdf.parse(jsonObject.getString("published"))),
							new User(owner.getString("id"), owner.getString("displayName"), owner.getString("url"))));
				}
			}
		} while (++pageNumber < comment.getNumberOfPages() && pageToken != "");
		return comments;
	}

	@Override
	public List<Activity> getActivity(ActivitySearch activity) throws Exception {
		List<Activity> activities = new ArrayList<>();
		pageNumber = 0;
		pageToken = "";
		do {
			jsonArray = null;
			jsonText = readAll(urlForActivities + activity.getText() + "&pageToken=" + pageToken);
			json = new JSONObject(jsonText);
			if (json.getJSONArray("items").toList().isEmpty())
				return activities;
			jsonArray = new JSONArray(json.get("items").toString());
			pageToken = json.getString("nextPageToken");
			for (Object object : jsonArray) {
				jsonObject = new JSONObject(object.toString());
				owner = jsonObject.getJSONObject("actor");
				activities.add(new Activity(jsonObject.getString("id"), jsonObject.getString("title"),
						jsonObject.getString("url"), jsonObject.getString("kind"), jsonObject.getString("etag"),
						sdf.format(sdf.parse(jsonObject.getString("published"))),
						new User(owner.getString("id"), owner.getString("displayName"), owner.getString("url"))));
			}
		} while (++pageNumber < activity.getNumberOfPages() && pageToken != "");
		return activities;
	}

	/**
	 * Read data from stream
	 * 
	 * @param rd
	 *            - reader holding stream
	 * @return data from stream
	 */
	private String readAll(String url) throws IOException {
		// String responseData = "";
		// ClientResponse response = null;
		// ClientConfig config = new DefaultClientConfig();
		// try {
		// Client client = Client.create(config);
		// client.addFilter(new GZIPContentEncodingFilter(false));
		// WebResource wr = client.resource(url);
		// response = wr.get(ClientResponse.class);
		// if (response.getStatus() != 200)
		// throw new Exception();
		// responseData = response.getEntity(String.class);
		// } catch (Exception e) {
		// System.out.println("The fatal error with Google+ API:" +
		// e.getMessage());
		// }
		// return responseData;
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		client.addFilter(new GZIPContentEncodingFilter(false));
		WebResource wr = client.resource(url);
		ClientResponse response = null;
		response = wr.get(ClientResponse.class);
		return response.getEntity(String.class);
	}

}
