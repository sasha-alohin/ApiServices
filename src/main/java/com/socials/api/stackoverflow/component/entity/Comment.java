package com.socials.api.stackoverflow.component.entity;

public class Comment {
	private Integer commentId;
	private Integer postId;
	private User owner;
	private User replyToUser;
	private Integer score;

	public Comment(Integer commentId, Integer postId, User owner, User replyToUser, Integer score) {
		this.commentId = commentId;
		this.postId = postId;
		this.owner = owner;
		this.replyToUser = replyToUser;
		this.score = score;
	}

	public Comment(Integer commentId, Integer postId, User owner, Integer score) {
		this.commentId = commentId;
		this.postId = postId;
		this.owner = owner;
		this.score = score;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getReplyToUser() {
		return replyToUser;
	}

	public void setReplyToUser(User replyToUser) {
		this.replyToUser = replyToUser;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
