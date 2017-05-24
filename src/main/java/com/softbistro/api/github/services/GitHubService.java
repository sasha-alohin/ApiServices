package com.softbistro.api.github.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.api.github.component.entity.Repository;
import com.softbistro.api.github.component.entity.User;
import com.softbistro.api.github.component.service.GitHubDao;

@Service
public class GitHubService {

	@Autowired
	private GitHubDao gitHubDao;

	/**
	 * Get user information by name
	 * 
	 * @param user
	 *            - user name
	 * @return user data
	 */
	public User getUser(String user) throws IOException {
		return gitHubDao.getUser(user);
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
	public Repository getRepositoryInfo(String user, String repository) throws IOException {
		return gitHubDao.getRepositoryInfo(user, repository);
	}

	/**
	 * Get repositories of user
	 * 
	 * @param user
	 *            - user name
	 * @return repositories data
	 */
	public List<Repository> getUserRepositories(String user) throws Exception {
		return gitHubDao.getUserRepositories(user);
	}

}
