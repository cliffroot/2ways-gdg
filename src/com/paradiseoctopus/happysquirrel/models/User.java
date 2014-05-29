package com.paradiseoctopus.happysquirrel.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {


	int id;

	String socialId;
	String token;
	String email;
	String avatar_url;
	String name;

	static User user;

	public static User getCurrentUser () {
		return user;
	}

	public static void setCurrentUser (String id, String token, String avatar_url, String name) {
		user = new User();
		user.socialId = id;
		user.token = token;
		user.avatar_url = avatar_url;
		user.name = name;
	}

	public User () { 

	}


	public void setId(int id) {
		this.id = id;
	}

	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

}
