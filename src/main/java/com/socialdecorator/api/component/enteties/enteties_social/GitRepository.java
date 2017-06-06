package com.socialdecorator.api.component.enteties.enteties_social;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GitRepository {

	private Integer id;
	private String name;
	private String description;
	private String url;
	private String language;

	public GitRepository(Integer id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

	public GitRepository(Integer id, String name, String description, String url, String language) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.url = url;
		this.language = language;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
