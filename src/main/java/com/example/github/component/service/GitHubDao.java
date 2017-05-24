package com.example.github.component.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.github.component.entity.Repository;
import com.example.github.component.entity.User;
import com.example.github.component.interfac.GitHubInterface;

public class GitHubDao implements GitHubInterface {
	private InputStream is;
	private JSONObject json;
	private String jsonText;
	private BufferedReader rd;
	private final String urlForUser = "https://api.github.com/users/";
	private final String urlForRepositoryInfo = "https://api.github.com/repos/";

	/**
	 * Get user information by name
	 * 
	 * @param user
	 *            - user name
	 * @return user data
	 */
	@Override
	public User getUser(String user) throws IOException {
		is = new URL(urlForUser + user).openStream();
		try {
			rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			jsonText = readAll(rd);
			json = new JSONObject(jsonText);
			return new User(json.getInt("id"), json.getString("login"), json.getString("name"),
					json.getString("location"));
		} finally {
			is.close();
		}
	}

	/**
	 * Get repository information of user
	 * 
	 * @param user
	 *            - user name
	 * @param repository
	 *            - repository name
	 * @return repository data
	 */
	@Override
	public Repository getRepositoryInfo(String user, String repository) throws IOException {
		is = new URL(urlForRepositoryInfo + user + "/" + repository).openStream();
		try {
			rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			jsonText = readAll(rd);
			json = new JSONObject(jsonText);
			return new Repository(json.getInt("id"), json.getString("name"), json.getString("description"),
					json.getString("html_url"), json.getString("language"));
		} finally {
			is.close();
		}
	}

	/**
	 * Get repositories of user
	 * 
	 * @param user
	 *            - user name
	 * @return repositories data
	 */
	@Override
	public List<Repository> getUserRepositories(String user) throws Exception {
		is = new URL(urlForUser + user + "/repos").openStream();
		List<Repository> repositories = new ArrayList<>();
		rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		jsonText = readAll(rd);
		JSONArray jsonArray = new JSONArray(jsonText);
		for (Object object : jsonArray) {
			System.out.println(object);
			json = new JSONObject(object.toString());
			System.out.println(json.get("description"));
			repositories.add(new Repository(json.getInt("id"), json.getString("name"),
					Optional.of(json.get("description")).map(Object::toString).orElse(null), json.getString("html_url"),
					Optional.of(json.get("language")).map(Object::toString).orElse(null)));
		}
		return repositories;

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
