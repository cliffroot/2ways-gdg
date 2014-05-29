package com.paradiseoctopus.happysquirrel.models;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class activePerson {

	int person_id;
	int project_id;
	int sum;
	
	public int getPerson_id() {
		return person_id;
	}
	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}
	public int getProject_id() {
		return project_id;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

}
