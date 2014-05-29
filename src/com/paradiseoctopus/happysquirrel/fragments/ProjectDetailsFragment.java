package com.paradiseoctopus.happysquirrel.fragments;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.paradiseoctopus.happysquirrel.MainActivity_;
import com.paradiseoctopus.happysquirrel.R;
import com.paradiseoctopus.happysquirrel.adapters.ItemAdapter;
import com.paradiseoctopus.happysquirrel.helpers.DataProvider;
import com.paradiseoctopus.happysquirrel.helpers.DialogHelper;
import com.paradiseoctopus.happysquirrel.helpers.LinearLayoutList;
import com.paradiseoctopus.happysquirrel.models.Comments;
import com.paradiseoctopus.happysquirrel.models.Project;
import com.paradiseoctopus.happysquirrel.models.activePerson;

@EFragment(R.layout.project_details_fragment)
@OptionsMenu(R.menu.project_details_menu)
public class ProjectDetailsFragment extends Fragment {
	
	@ViewById(R.id.project_description)
	TextView projectDescription;
	
	@ViewById(R.id.project_photo)
	NetworkImageView projectPhoto;
	
	@ViewById(R.id.project_title)
	TextView projectTitle;
	
	@ViewById(R.id.project_category)
	TextView projectCategory;
	
	@ViewById(R.id.project_comments)
	LinearLayoutList projectComments;
	
	@ViewById(R.id.sum_label)
	TextView sumLabel;
	
	@ViewById(R.id.sum)
	EditText sum;
	
	@ViewById(R.id.accept)
	Button accept;
	
	@Bean
	static ItemAdapter commentAdapter;
	Comments comments;
	
	int orderPosition = 0;
	
	Project project;
	
	@RestService
	DataProvider restClient;
	
	@ViewById(R.id.project_description)
	TextView description;
	
	@ViewById
	ImageView expandIcon;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		orderPosition = bundle.getInt("project");
		Log.i("PROJECT", "TRYING TO FETCH ");
		fetchProject();
	}
	
	@Background
	void fetchProject() {
		project = restClient.getProject(orderPosition);
		comments = restClient.getComments(orderPosition);
		
		// Log.i("COMMENTS", comments.getComments().get(0).getBody());
		
		Log.i("PROJECT", project.getName());
		Log.i("PROJECT", project.getDescription());
		// Log.i()
		fillForm();
	}
	
	@OptionsItem(R.id.add_message)
	public void addMessage() {
		SharedPreferences s = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
		DialogHelper.showErrorDialog(getActivity(), s.getInt("user_id", -1), project.getId(), this);
	}
	
	@SuppressLint("NewApi")
	@UiThread
	void fillForm() {
		if (project == null) {
			Log.e("ой ой", true + "");
		}
		getActivity().setTitle(project.getName());
		ActionBar actionBar = getActivity().getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		if (comments.getComments().size() != 0) {
			commentAdapter.setItems(comments.getComments());
			projectComments.setAdapter(commentAdapter);
			commentAdapter.notifyDataSetChanged();
		}
		else {
			
		}
		
		ImageLoader mImageLoader = new ImageLoader(((MainActivity_) getActivity()).rQueue, ((MainActivity_) getActivity()).cache);
		// Log.i("AAAAA", (project.getPhotos() == null) + "");
		// Log.i("AAAAA", (project.getPhotos().get(0) == null) + "");
		// Log.i("AAAAA", project.getPhotos().get(0).getAvatar_file_name());
		try {
			projectPhoto.setImageUrl("http://172.31.151.64:3000/images/" + project.getPhotos().get(0).getAvatar_file_name(), mImageLoader);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		projectTitle.setText(project.getName());
		projectDescription.setText(project.getDescription());
		
		projectCategory.setText(project.getCategory_name());
		
		if (project.getGoal() == 0) {
			sumLabel.setVisibility(View.GONE);
			sum.setVisibility(View.GONE);
			accept.setText("Attend");
		}
	}
	
	@Background
	void newActivePerson(activePerson person) {
		restClient.newActivePerson(person);
	}
	
	private boolean clicked;
	
	@Click
	void accept() {
		if (!clicked) {
			activePerson person = new activePerson();
			
			SharedPreferences s = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
			int user_id = s.getInt("user_id", -1);
			
			person.setPerson_id(user_id);
			person.setProject_id(project.getId());
			
			if (project.getGoal() != 0) {
				if (!(sum.getText() + "").equals("")) {
					person.setSum(Integer.valueOf(sum.getText() + ""));
				}
			}
			
			newActivePerson(person);
		}
		else {
			clicked = true;
		}
	}
	
	@Click
	void descLayout() {
		description.setVisibility(description.isShown() ? View.GONE : View.VISIBLE);
		expandIcon.setImageResource(description.isShown() ? R.drawable.ic_action_collapse : R.drawable.ic_action_expand);
	}
}
