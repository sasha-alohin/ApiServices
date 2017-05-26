package com.socials.api.stackoverflow.component.entity;

public class Answer {
	private User owner;
	private Integer answerId;
	private Integer questionId;
	private Boolean isAccepted;
	private Integer score;

	public Answer(User owner, Integer answerId, Integer questionId, Boolean isAccepted, Integer score) {
		this.owner = owner;
		this.answerId = answerId;
		this.questionId = questionId;
		this.isAccepted = isAccepted;
		this.score = score;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
