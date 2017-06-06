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
import com.socialdecorator.api.component.enteties.enteties_social.GitRepository;
import com.socialdecorator.api.component.enteties.enteties_social.User;
import com.socialdecorator.api.component.interfaces.Social;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;

/**
 * Access to GitHub API data. With authentication up to 5 000 requests per hour
 * 
 * @author alex_alokhin
 *
 */
@Service
public class GitHubDao implements Social {

	private String jsonText;
	private JSONObject json, owner, repository;
	private JSONArray jsonArray;
	private final String urlForUser = "https://api.github.com/users?per_page=100&q=";
	private final String urlForRepositories = "https://api.github.com/repos/";
	private final String urlForEvents = "https://api.github.com/events?per_page=100";
	private int pageNumber;
	private final String clientId = "73c6c1fff1ad033a9c4c";
	private final String clientSecret = "c85c6cebc5256ad88274467577fa768c187448d8";
	private final String urlForRateLimit = "https://api.github.com/rate_limit?" + "client_id=" + clientId
			+ "&client_secret=" + clientSecret;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	@Override
	public List<User> getUser(UserSearch user) throws Exception {
		List<User> users = new ArrayList<>();
		pageNumber = 0;
		jsonArray = null;
		do {
			if (isFullRateLimit())
				return users;
			jsonText = readAll(urlForUser + user.getName() + "&page=" + (++pageNumber) + "&client_id=" + clientId
					+ "&client_secret=" + clientSecret);
			jsonArray = new JSONArray(jsonText);
			if (jsonArray == null)
				break;
			for (Object object : jsonArray) {
				json = new JSONObject(object.toString());
				users.add(new User(json.getInt("id"), json.getString("login"), json.getString("type"),
						json.getString("html_url"), json.getString("repos_url"), json.getString("followers_url")));
			}
		} while (pageNumber <= user.getNumberOfPages());
		return users;
	}

	@Override
	public List<Comment> getComment(CommentSearch comment) throws Exception {
		List<Comment> comments = new ArrayList<>();
		pageNumber = 0;
		do {
			if (isFullRateLimit())
				return comments;
			jsonText = readAll(urlForRepositories + comment.getUser() + "/" + comment.getRepository()
					+ "/comments?per_page=100&page=" + (++pageNumber) + "&client_id=" + clientId + "&client_secret="
					+ clientSecret);
			jsonArray = new JSONArray(jsonText);
			if (jsonArray == null)
				break;
			for (Object object : jsonArray) {
				json = new JSONObject(object.toString());
				owner = json.getJSONObject("user");
				comments.add(new Comment(json.getInt("id"), json.getString("commit_id"),
						new User(owner.getInt("id"), owner.getString("login"), owner.getString("type"),
								owner.getString("html_url"), owner.getString("repos_url"),
								owner.getString("followers_url")),
						json.getString("body"), json.getString("url"),
						sdf.format(sdf.parse(json.getString("created_at"))),
						sdf.format(sdf.parse(json.getString("updated_at")))));
			}
		} while (pageNumber <= comment.getNumberOfPages());
		return comments;
	}

	// Up to 300 events because of restrictions of API
	@Override
	public List<Activity> getActivity(ActivitySearch activity) throws Exception {
		List<Activity> activities = new ArrayList<>();
		pageNumber = 0;
		do {
			if (isFullRateLimit())
				return activities;
			jsonText = readAll(urlForEvents + "&page=" + (++pageNumber) + "&client_id=" + clientId + "&client_secret="
					+ clientSecret);
			jsonArray = new JSONArray(jsonText);
			if (jsonArray == null)
				break;
			for (Object object : jsonArray) {
				json = new JSONObject(object.toString());
				owner = json.getJSONObject("actor");
				repository = json.getJSONObject("repo");
				activities.add(new Activity(json.getString("id"), json.getString("type"),
						new User(owner.getInt("id"), owner.getString("login"), owner.getString("url")),
						new GitRepository(repository.getInt("id"), repository.getString("name"),
								repository.getString("url")),
						json.getBoolean("public"), sdf.format(sdf.parse(json.getString("created_at")))));
			}
		} while (pageNumber < 3);
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
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		client.addFilter(new GZIPContentEncodingFilter(false));
		WebResource wr = client.resource(url);
		ClientResponse response = null;
		response = wr.get(ClientResponse.class);
		return response.getEntity(String.class);
	}

	private Boolean isFullRateLimit() throws Exception {
		JSONObject json = new JSONObject(readAll(urlForRateLimit));
		Integer coreRemaining = json.getJSONObject("resources").getJSONObject("core").getInt("remaining");
		Integer searchRemaining = json.getJSONObject("resources").getJSONObject("search").getInt("remaining");
		return coreRemaining <= 1 || searchRemaining <= 1;
	}

}
