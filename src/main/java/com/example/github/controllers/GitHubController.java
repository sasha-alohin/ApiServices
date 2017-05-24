package com.example.github.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.github.component.entity.Repository;
import com.example.github.component.entity.User;
import com.example.github.services.GitHubService;

@RestController
public class GitHubController {

	@Autowired
	private GitHubService gitHubService;

	/**
	 * Get user information by name
	 * 
	 * @param user
	 *            - user name
	 * @return user data
	 */
	@RequestMapping(value = "/users/{name}", method = RequestMethod.GET)
	public User getUser(@PathVariable("name") String name) throws IOException {
		return gitHubService.getUser(name);
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
	@RequestMapping(value = "/repository/{user}/{repository}", method = RequestMethod.GET)
	public Repository getRepositoryInfo(@PathVariable("user") String user,
			@PathVariable("repository") String repository) throws IOException {
		return gitHubService.getRepositoryInfo(user, repository);
	}

	/**
	 * Get repositories of user
	 * 
	 * @param user
	 *            - user name
	 * @return repositories data
	 */
	@RequestMapping(value = "/repositories/{user}", method = RequestMethod.GET)
	public List<Repository> getUserRepositories(@PathVariable("user") String user) throws Exception {
		return gitHubService.getUserRepositories(user);
	}

}
