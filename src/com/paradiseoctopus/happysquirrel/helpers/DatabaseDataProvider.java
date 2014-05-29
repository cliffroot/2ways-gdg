package com.paradiseoctopus.happysquirrel.helpers;

import org.androidannotations.annotations.EBean;
import org.androidannotations.api.rest.RestErrorHandler;

import com.paradiseoctopus.happysquirrel.models.Comment;
import com.paradiseoctopus.happysquirrel.models.Comments;
import com.paradiseoctopus.happysquirrel.models.Project;
import com.paradiseoctopus.happysquirrel.models.ProjectList;
import com.paradiseoctopus.happysquirrel.models.User;
import com.paradiseoctopus.happysquirrel.models.activePerson;

/*
 * Class which kind of tries to be like Rest Client and do the same things but with the database;
 */
@EBean
public class DatabaseDataProvider implements DataProvider {

	@Override
	public void setRestErrorHandler(RestErrorHandler arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public User registerUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProjectList getAllProjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project newProject(Project project) {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public Project getProject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comments getComments(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project newActivePerson(activePerson person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProjectList getMyProjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void newMessage(Comment message) {
		// TODO Auto-generated method stub

	}

}
