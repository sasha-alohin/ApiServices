package com.socialdecorator.api.component.enteties.enteties_social;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment {

	private Integer commentId;
	private String commitId;
	private String idString;
	private Integer postId;
	private User owner;
	private String content;
	private String url;
	private User replyToUser;
	private Integer score;
	private String createdDate;
	private String updatedDate;
	private Integer favouriteCount;
	private String replyToUserName;
	private String language;
	private String etag;
	private String date;

	public Comment(String idString, String content, String url, String etag, String date, User owner) {
		this.idString = idString;
		this.owner = owner;
		this.content = content;
		this.url = url;
		this.date = date;
		this.etag = etag;
	}

	public Comment(Integer commentId, Integer postId, User owner, User replyTouser, Integer score) {
		this.commentId = commentId;
		this.postId = postId;
		this.owner = owner;
		this.replyToUser = replyTouser;
		this.score = score;
	}

	public Comment(Integer commentId, String content, String language, User owner, String replyToUserName,
			Integer favouriteCount, String createdAt) {
		this.commentId = commentId;
		this.createdDate = createdAt;
		this.content = content;
		this.language = language;
		this.owner = owner;
		this.favouriteCount = favouriteCount;
		this.replyToUserName = replyToUserName;
	}

	public Comment(Integer commentId, Integer postId, User owner, Integer score) {
		this.commentId = commentId;
		this.postId = postId;
		this.owner = owner;
		this.score = score;
	}

	public Comment(Integer commentId, String commitId, User owner, String content, String url, String createdAt,
			String updatedAt) {
		this.commentId = commentId;
		this.commitId = commitId;
		this.owner = owner;
		this.content = content;
		this.url = url;
		this.createdDate = createdAt;
		this.updatedDate = updatedAt;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getIdString() {
		return idString;
	}

	public void setIdString(String idString) {
		this.idString = idString;
	}

	public Integer getFavouriteCount() {
		return favouriteCount;
	}

	public void setFavouriteCount(Integer favouriteCount) {
		this.favouriteCount = favouriteCount;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getReplyToUserName() {
		return replyToUserName;
	}

	public void setReplyToUserName(String replyToUserName) {
		this.replyToUserName = replyToUserName;
	}

	public String getCommitId() {
		return commitId;
	}

	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
