package com.paradiseoctopus.happysquirrel.models;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;


public class Comments {


	@JsonProperty("comments")
	List<Comment> comments;

	public Comments () {}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}



}
