package com.socialdecorator.api.component.enteties.enteties_search;

public class CommentSearch {

	private Integer id;
	private String user;
	private String site;
	private String repository;
	private Integer numberOfPages;
	private String text;

	public CommentSearch(String user, String repository, String site, Integer numberOfPages, String text) {
		this.user = user;
		this.repository = repository;
		this.site = site;
		this.numberOfPages = numberOfPages;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public CommentSearch(String site) {
		this.site = site;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
