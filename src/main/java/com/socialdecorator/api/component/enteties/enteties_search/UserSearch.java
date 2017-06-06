package com.socialdecorator.api.component.enteties.enteties_search;


public class UserSearch {

	private String name;
	private String site;
	private Integer numberOfPages;
	public UserSearch(String site,String name,Integer numberOfPages) {
		this.site = site;
		this.name = name;
		this.numberOfPages = numberOfPages;
	}

	public Integer getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
