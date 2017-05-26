package com.socials.api.stackoverflow.component.entity;

public class User {
	private Integer userId;
	private String displayName;
	private String userType;
	private Integer reputation;
	private String url;

	public User(Integer userId, String displayName, String userType, Integer reputation, String url) {
		this.userId = userId;
		this.displayName = displayName;
		this.userType = userType;
		this.reputation = reputation;
		this.url = url;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getReputation() {
		return reputation;
	}

	public void setReputation(Integer reputation) {
		this.reputation = reputation;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
