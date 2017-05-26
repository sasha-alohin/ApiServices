package com.socials.api.stackoverflow.component.interfaces;

import java.io.IOException;
import java.util.List;

import com.socials.api.stackoverflow.component.entity.Answer;
import com.socials.api.stackoverflow.component.entity.Comment;
import com.socials.api.stackoverflow.component.entity.Question;

public interface StackOverflowInterface {

	/**
	 * Get all sites names related to StackExchange API that you can use in your
	 * queries
	 * 
	 * @return sites
	 */
	public List<String> getSites() throws IOException;

	/**
	 * Get all comment's information by site name
	 * 
	 * @param site
	 *            - site simple or full name (stackoverflow or
	 *            stackoverflow.com)
	 * @return comments
	 */
	public List<Comment> getComments(String site) throws IOException;

	/**
	 * Get all questions information by site name
	 * 
	 * @param site
	 *            - site simple or full name (stackoverflow or
	 *            stackoverflow.com)
	 * @return questions
	 */
	public List<Question> getQuestions(String site) throws IOException;

	/**
	 * Get all answer's information by site name
	 * 
	 * @param site
	 *            - site simple or full name (stackoverflow or
	 *            stackoverflow.com)
	 * @return answers
	 */
	public List<Answer> getAnswers(String site) throws IOException;

}
