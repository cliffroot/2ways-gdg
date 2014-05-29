package com.paradiseoctopus.happysquirrel.fragments;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.paradiseoctopus.happysquirrel.R;
import com.paradiseoctopus.happysquirrel.helpers.DataProvider;
import com.paradiseoctopus.happysquirrel.helpers.DialogHelper;
import com.paradiseoctopus.happysquirrel.models.Comment;
import com.paradiseoctopus.happysquirrel.models.Project;
import com.paradiseoctopus.happysquirrel.models.User;

@EFragment(R.layout.message_new_dialogue)
public class NewMessageDialog extends DialogFragment{


	@ViewById(R.id.messageDescription)
	EditText wishDescription;

	@ViewById(R.id.createButton)
	Button newMessage;

	@RestService
	DataProvider restClient;

	Fragment parentFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().setTitle("New message");
		return null; 
	}

	@Click 
	public void createButton () {
		createMessage();
	}

	@Background
	public void createMessage () {
		Comment comment = new Comment();
		comment.setBody(wishDescription.getText()+ "");
		User user = new User();
		user.setId(DialogHelper.id);
		comment.setPerson(user);
		Project project = new Project();
		project.setId(DialogHelper.po);
		comment.setProject(project);
		restClient.newMessage(comment);

		//((ProjectDetailsFragment_)DialogHelper.fragment).commentAdapter.getItems().add(comment);
		((ProjectDetailsFragment_)DialogHelper.fragment).fetchProject();
		//((ProjectDetailsFragment_)DialogHelper.fragment).commentAdapter.notifyDataSetChanged();//
		closeDialog();

	}

	@UiThread
	public void closeDialog () {
		dismiss();
	}
}