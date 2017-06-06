package com.socialdecorator.api.component.enteties.enteties_social;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
	private Integer id;
	private String idString;
	private String name;
	private String login;
	private String location;
	private String userType;
	private Integer reputation;
	private String url;
	private Integer age;
	private String repositoryUrl;
	private String followersUrl;
	private Integer followersCount;
	private Integer favouritesCount;
	private String language;
	private String status;
	private String createdAt;
	private String etag;

	public User(String idString, String name, String url) {
		this.idString = idString;
		this.name = name;
		this.url = url;
	}

	public User(Integer id, String login, String url) {
		this.id = id;
		this.login = login;
		this.url = url;
	}

	public User(Integer id, String name, String language, String location, Integer followersCount,
			Integer favouritesCount, String url, String createdAt) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.url = url;
		this.followersCount = followersCount;
		this.favouritesCount = favouritesCount;
		this.language = language;
		this.createdAt = createdAt;
	}

	public User(Integer id, String login, String userType, String url, String repositoryUrl, String followersUrl) {
		this.id = id;
		this.login = login;
		this.userType = userType;
		this.url = url;
		this.repositoryUrl = repositoryUrl;
		this.followersUrl = followersUrl;
	}

	public User(Integer id, String name, String url, String userType, Integer age, String location,
			Integer reputation) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.userType = userType;
		this.age = age;
		this.location = location;
		this.reputation = reputation;

	}

	public User(Integer id, String name, String url, String userType, String location, Integer reputation) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.userType = userType;
		this.location = location;
		this.reputation = reputation;

	}

	public User(Integer id, String name, String url, String userType, Integer age, Integer reputation) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.userType = userType;
		this.age = age;
		this.reputation = reputation;

	}

	public User(Integer id, String name, String url, String userType, Integer reputation) {
		this.id = id;
		this.name = name;
		this.userType = userType;
		this.reputation = reputation;
		this.url = url;
	}

	public User(String idString, String name, String url, String userType, String etag) {
		this.idString = idString;
		this.name = name;
		this.userType = userType;
		this.etag = etag;
		this.url = url;
	}

	public String getIdString() {
		return idString;
	}

	public void setIdString(String idString) {
		this.idString = idString;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public Integer getFollowersCount() {
		return followersCount;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}

	public Integer getFavouritesCount() {
		return favouritesCount;
	}

	public void setFavouritesCount(Integer favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	public String getFollowersUrl() {
		return followersUrl;
	}

	public void setFollowersUrl(String followersUrl) {
		this.followersUrl = followersUrl;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
