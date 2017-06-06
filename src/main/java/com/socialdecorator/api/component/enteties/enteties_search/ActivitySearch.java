package com.socialdecorator.api.component.enteties.enteties_search;

public class ActivitySearch {

	private String site;
	private String user;
	private Integer numberOfPages;
	private String text;
	public ActivitySearch(String user, String site, Integer numberOfPages,String text) {
		this.user = user;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

}
