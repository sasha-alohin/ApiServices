package com.socials.api.stackoverflow.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.socials.api.github.component.entity.GitRepository;
import com.socials.api.github.component.entity.User;
import com.socials.api.github.services.GitHubService;
import com.socials.api.stackoverflow.component.entity.Answer;
import com.socials.api.stackoverflow.component.entity.Comment;
import com.socials.api.stackoverflow.component.entity.Question;
import com.socials.api.stackoverflow.services.StackOverflowService;

@RequestMapping(value = "/stackoverflow")
@RestController
public class StackOverflowController {

	@Autowired
	private StackOverflowService stackOverflow;

	/**
	 * Get all answer's information by site name
	 * 
	 * @param site
	 *            - site simple or full name (stackoverflow or
	 *            stackoverflow.com)
	 * @return answers
	 */
	@RequestMapping(value = "/answers/{site}")
	public List<Answer> getAnswers(@PathVariable(value = "site") String site) throws IOException {
		return stackOverflow.getAnswers(site);
	}

	/**
	 * Get all sites names related to StackExchange API that you can use in your
	 * queries
	 * 
	 * @return sites
	 */
	@RequestMapping(value = "/sites")
	public List<String> getSites() throws IOException {
		return stackOverflow.getSites();
	}

	/**
	 * Get all comment's information by site name
	 * 
	 * @param site
	 *            - site simple or full name (stackoverflow or
	 *            stackoverflow.com)
	 * @return comments
	 */
	@RequestMapping(value = "/comments/{site}")
	public List<Comment> getComments(String site) throws IOException {
		return stackOverflow.getComments(site);
	}

	/**
	 * Get all questions information by site name
	 * 
	 * @param site
	 *            - site simple or full name (stackoverflow or
	 *            stackoverflow.com)
	 * @return questions
	 */
	@RequestMapping(value = "/questions/{site}")
	public List<Question> getQuestions(String site) throws IOException {
		return stackOverflow.getQuestions(site);
	}

}
