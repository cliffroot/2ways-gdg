package com.paradiseoctopus.happysquirrel.helpers;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import com.paradiseoctopus.happysquirrel.models.Comment;
import com.paradiseoctopus.happysquirrel.models.Comments;
import com.paradiseoctopus.happysquirrel.models.Project;
import com.paradiseoctopus.happysquirrel.models.ProjectList;
import com.paradiseoctopus.happysquirrel.models.User;
import com.paradiseoctopus.happysquirrel.models.activePerson;

/*
 * Rest client. Let Spring for Android and annotation do all the magic for u ;)
 */
//@Rest(rootUrl = "http://31.43.142.152:3000", converters = {MappingJacksonHttpMessageConverter.class})
@Rest(rootUrl = "http://172.31.151.64:3000", converters = { MappingJacksonHttpMessageConverter.class })
public interface DataProvider extends RestClientErrorHandling {

	@Post("/people.json")
	public User registerUser (User user);

	@Get("/projects.json") 
	public ProjectList getAllProjects ();

	@Post("/projects.json")
	public Project newProject(Project project);

	@Post("/active_people.json")
	public Project newActivePerson(activePerson person);

	@Get("/projects/{id}.json") 
	public Project getProject (int id);

	@Get("/projects/comments/{id}.json")
	public Comments getComments (int id);

	@Get("/people/projects/{id}.json")
	public ProjectList getMyProjects(int id);

	@Post("/comments.json")
	public void newMessage(Comment comment);
}
