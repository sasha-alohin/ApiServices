package com.example.github.component.interfac;

import java.io.IOException;
import java.util.List;

import com.example.github.component.entity.Repository;
import com.example.github.component.entity.User;

public interface GitHubInterface {

	/**
	 * Get user information by name
	 * 
	 * @param user
	 *            - user name
	 * @return user data
	 */
	public User getUser(String user) throws Exception;

	/**
	 * Get repository information of user
	 * 
	 * @param user
	 *            - user name
	 * @param repository
	 *            - repository name
	 * @return repository data
	 */
	public Repository getRepositoryInfo(String user, String repository) throws Exception;

	/**
	 * Get repositories of user
	 * 
	 * @param user
	 *            - user name
	 * @return repositories data
	 */
	public List<Repository> getUserRepositories(String user) throws Exception;
}
