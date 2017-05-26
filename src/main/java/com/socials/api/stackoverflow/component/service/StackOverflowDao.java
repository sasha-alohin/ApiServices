package com.socials.api.stackoverflow.component.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.socials.api.stackoverflow.component.entity.Answer;
import com.socials.api.stackoverflow.component.entity.Comment;
import com.socials.api.stackoverflow.component.entity.Question;
import com.socials.api.stackoverflow.component.entity.User;
import com.socials.api.stackoverflow.component.interfaces.StackOverflowInterface;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;

@Repository
public class StackOverflowDao implements StackOverflowInterface {
	private JSONObject json;
	private String jsonText;
	private JSONObject owner, jsonObject;
	private JSONArray jsonArray;
	private final String urlForAnswers = "https://api.stackexchange.com/2.2/answers?order=desc&sort=activity&site=";
	private final String urlForComments = "https://api.stackexchange.com/2.2/comments?order=desc&sort=creation&site=";
	private final String urlForQuestions = "https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&site=";
	private final String urlForSites = "https://api.stackexchange.com/2.2/sites";

	/**
	 * Get all answer's information by site name
	 * 
	 * @param site
	 *            - site simple or full name (stackoverflow or
	 *            stackoverflow.com)
	 * @return answers
	 */
	@Override
	public List<Answer> getAnswers(String site) throws IOException {
		List<Answer> answers = new ArrayList<>();
		jsonText = readAll(urlForAnswers + site);
		jsonObject = new JSONObject(jsonText);
		jsonArray = new JSONArray(jsonObject.get("items").toString());
		for (Object object : jsonArray) {
			json = new JSONObject(object.toString());
			owner = json.getJSONObject("owner");
			answers.add(new Answer(
					new User(owner.getInt("user_id"), owner.getString("display_name"), owner.getString("user_type"),
							owner.getInt("reputation"), owner.getString("link")),
					json.getInt("answer_id"), json.getInt("question_id"), json.getBoolean("is_accepted"),
					json.getInt("score")));
		}
		return answers;
	}

	/**
	 * Get all sites names related to StackExchange API that you can use in your
	 * queries
	 * 
	 * @return sites
	 */
	@Override
	public List<String> getSites() throws IOException {
		List<String> sites = new ArrayList<>();
		jsonText = readAll(urlForSites);
		jsonArray = new JSONArray(jsonText);
		for (Object object : jsonArray) {
			json = new JSONObject(object.toString());
			sites.add(json.getString("site_url"));
		}
		return sites;
	}

	/**
	 * Get all comment's information by site name
	 * 
	 * @param site
	 *            - site simple or full name (stackoverflow or
	 *            stackoverflow.com)
	 * @return comments
	 */
	@Override
	public List<Comment> getComments(String site) throws IOException {
		JSONObject replyToUser = null;
		List<Comment> comments = new ArrayList<>();
		jsonText = readAll(urlForComments + site);
		jsonObject = new JSONObject(jsonText);
		jsonArray = new JSONArray(jsonObject.get("items").toString());
		for (Object object : jsonArray) {
			json = new JSONObject(object.toString());
			owner = json.getJSONObject("owner");
			if (json.has("reply_to_user")) {
				replyToUser = json.getJSONObject("reply_to_user");
				comments.add(new Comment(json.getInt("comment_id"), json.getInt("post_id"),
						new User(owner.getInt("user_id"), owner.getString("display_name"), owner.getString("user_type"),
								owner.getInt("reputation"), owner.getString("link")),
						new User(replyToUser.getInt("user_id"), replyToUser.getString("display_name"),
								replyToUser.getString("user_type"), replyToUser.getInt("reputation"),
								replyToUser.getString("link")),
						json.getInt("score")));
			} else {

				comments.add(new Comment(json.getInt("comment_id"),
						json.getInt("post_id"), new User(owner.getInt("user_id"), owner.getString("display_name"),
								owner.getString("user_type"), owner.getInt("reputation"), owner.getString("link")),
						json.getInt("score")));
			}
		}
		return comments;
	}

	/**
	 * Get all questions information by site name
	 * 
	 * @param site
	 *            - site simple or full name (stackoverflow or
	 *            stackoverflow.com)
	 * @return questions
	 */
	@Override
	public List<Question> getQuestions(String site) throws IOException {
		List<Question> questions = new ArrayList<>();
		jsonText = readAll(urlForQuestions + site);
		jsonObject = new JSONObject(jsonText);
		jsonArray = new JSONArray(jsonObject.get("items").toString());
		for (Object object : jsonArray) {
			json = new JSONObject(object.toString());
			owner = json.getJSONObject("owner");
			questions.add(new Question(json.getInt("question_id"), json.getString("title"),
					new User(owner.getInt("user_id"), owner.getString("display_name"), owner.getString("user_type"),
							owner.getInt("reputation"), owner.getString("link")),
					json.getString("link"), json.getBoolean("is_answered")));
		}
		return questions;
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

}
