package com.softbistro.api.github.component.entity;

public class User {

	private Integer id;
	private String login;
	private String name;
	private String location;

	public User() {
	}

	public User(Integer id, String login,String name, String location) {
		this.login = login;
		this.id = id;
		this.name = name;
		this.location = location;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public String getLocation() {
		return location;
	}

	public void setEmail(String location) {
		this.location = location;
	}

}
