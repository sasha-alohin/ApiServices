package com.socialdecorator.api.component.enteties.enteties_social;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Activity {
	private Integer id;
	private String eventId;
	private Integer postId;
	private String postType;
	private String date;
	private GitRepository repository;
	private String url;
	private Integer score;
	private String content;
	private String place;
	private String language;
	private User owner;
	private Boolean isPublic;
	private Integer favouriteCount;
	private String replyToUserName;
	private String etag;

	public Activity(String eventId, String content, String url, String postType, String etag, String date, User owner) {
		this.eventId = eventId;
		this.date = date;
		this.url = url;
		this.content = content;
		this.owner = owner;
		this.etag = etag;
		this.postType = postType;
	}

	public Activity(String eventId, String postType, User owner, GitRepository repository, Boolean isPublic,
			String date) {
		this.eventId = eventId;
		this.postType = postType;
		this.date = date;
		this.repository = repository;
		this.owner = owner;
		this.isPublic = isPublic;
	}

	public Activity(Integer postId, String postType, String url, Integer score, User owner) {
		this.postId = postId;
		this.postType = postType;
		this.url = url;
		this.score = score;
		this.owner = owner;
	}

	public Activity(Integer postId, String postType, String url, Integer score) {
		this.postId = postId;
		this.postType = postType;
		this.url = url;
		this.score = score;
	}

	public Activity(Integer id, String content, String language, User owner, String replyToUserName,
			Integer favouriteCount, String date) {
		this.id = id;
		this.date = date;
		this.content = content;
		this.language = language;
		this.owner = owner;
		this.favouriteCount = favouriteCount;
		this.replyToUserName = replyToUserName;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public Integer getFavouriteCount() {
		return favouriteCount;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setFavouriteCount(Integer favouriteCount) {
		this.favouriteCount = favouriteCount;
	}

	public String getReplyToUserName() {
		return replyToUserName;
	}

	public void setReplyToUserName(String replyToUserName) {
		this.replyToUserName = replyToUserName;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public GitRepository getRepository() {
		return repository;
	}

	public void setRepository(GitRepository repository) {
		this.repository = repository;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
