package com.socials.api.stackoverflow.component.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
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

@Repository
public class StackOverflowDao implements StackOverflowInterface {
	private InputStream is;
	private JSONObject json;
	private String jsonText;
	private BufferedReader rd;
	private JSONObject owner;
	private JSONArray jsonArray;

	private final String urlForAnswers = "https://api.stackexchange.com/2.2/answers?order=desc&sort=activity&site=";
	private final String urlForComments = "https://api.stackexchange.com/2.2/comments?order=desc&sort=activity&site=";
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
		is = new URL(urlForAnswers + site).openStream();
		List<Answer> answers = new ArrayList<>();
		rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		jsonText = readAll(rd);
		jsonArray = new JSONArray(jsonText);
		for (Object object : jsonArray) {
			json = new JSONObject(object.toString());
			owner = json.getJSONObject("owner");
			answers.add(new Answer(
					new User(owner.getInt("user_id"), owner.getString("display_name"), owner.getString("user_type"),
							owner.getInt("reputation"), owner.getString("url")),
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
		is = new URL(urlForSites).openStream();
		List<String> sites = new ArrayList<>();
		rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		jsonText = readAll(rd);
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
		JSONObject replyToUser;
		is = new URL(urlForComments + site).openStream();
		List<Comment> comments = new ArrayList<>();
		rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		jsonText = readAll(rd);
		jsonArray = new JSONArray(jsonText);
		for (Object object : jsonArray) {
			json = new JSONObject(object.toString());
			owner = json.getJSONObject("owner");
			replyToUser = json.getJSONObject("reply_to_user");
			comments.add(new Comment(json.getInt("comment_id"), json.getInt("post_id"),
					new User(owner.getInt("user_id"), owner.getString("display_name"), owner.getString("user_type"),
							owner.getInt("reputation"), owner.getString("url")),
					new User(replyToUser.getInt("user_id"), replyToUser.getString("display_name"),
							replyToUser.getString("user_type"), replyToUser.getInt("reputation"),
							replyToUser.getString("url")),
					json.getInt("score")));
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
		is = new URL(urlForQuestions + site).openStream();
		List<Question> questions = new ArrayList<>();
		rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		jsonText = readAll(rd);
		jsonArray = new JSONArray(jsonText);
		for (Object object : jsonArray) {
			json = new JSONObject(object.toString());
			owner = json.getJSONObject("owner");
			questions.add(new Question(json.getInt("question_id"), json.getString("title"),
					new User(owner.getInt("user_id"), owner.getString("display_name"), owner.getString("user_type"),
							owner.getInt("reputation"), owner.getString("url")),
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
	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

}
