package com.paradiseoctopus.happysquirrel.models;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProjectList {

	@JsonProperty("projects")
	private List<Project> items;

	public ProjectList () {}

	public List<Project> getItems() {
		return items;
	}

	public void setItems(List<Project> items) {
		this.items = items;
	} 


}