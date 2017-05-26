package com.socials.api.stackoverflow.component.entity;

public class Question {

	private Integer questionId;
	private String title;
	private User owner;
	private String link;
	private Boolean isAnswered;

	public Question(Integer questionId, String title, User owner, String link, Boolean isAnswered) {
		this.questionId = questionId;
		this.title = title;
		this.owner = owner;
		this.link = link;
		this.isAnswered = isAnswered;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer question_id) {
		this.questionId = question_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Boolean getIsAnswered() {
		return isAnswered;
	}

	public void setIsAnswered(Boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

}
