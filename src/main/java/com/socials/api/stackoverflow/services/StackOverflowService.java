package com.socials.api.stackoverflow.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socials.api.stackoverflow.component.entity.Answer;
import com.socials.api.stackoverflow.component.entity.Comment;
import com.socials.api.stackoverflow.component.entity.Question;
import com.socials.api.stackoverflow.component.service.StackOverflowDao;

@Service
public class StackOverflowService {

	@Autowired
	private StackOverflowDao stackOverflow;

	/**
	 * Get all answer's information by site name
	 * 
	 * @param site
	 *            - site simple or full name (stackoverflow or
	 *            stackoverflow.com)
	 * @return answers
	 */
	public List<Answer> getAnswers(String site) throws IOException {
		return stackOverflow.getAnswers(site);
	}

	/**
	 * Get all sites names related to StackExchange API that you can use in your
	 * queries
	 * 
	 * @return sites
	 */
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
	public List<Question> getQuestions(String site) throws IOException {
		return stackOverflow.getQuestions(site);
	}

}
