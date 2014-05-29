package com.paradiseoctopus.happysquirrel.fragments;

import java.util.List;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.paradiseoctopus.happysquirrel.R;
import com.paradiseoctopus.happysquirrel.adapters.ProjectAdapter;
import com.paradiseoctopus.happysquirrel.helpers.DataProvider;
import com.paradiseoctopus.happysquirrel.models.Project;
import com.paradiseoctopus.happysquirrel.models.ProjectList;


@EFragment(R.layout.project_my_fragment)
public class ProjectMyFragment extends Fragment{


	@ViewById(R.id.project_field)
	ListView myProjects;

	int userId;

	@RestService
	DataProvider restClient;

	@Bean//
	ProjectAdapter projectAdapter;

	List<Project> projects;


	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		SharedPreferences sp = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
		userId = sp.getInt("user_id", -1);
		fetchFromServer();

	}

	@Background
	void fetchFromServer () {
		ProjectList projectList = restClient.getMyProjects(userId);
		projects = projectList.getItems();
		fillList();
	}

	@ItemClick
	void project_field (int position) {
		Bundle bundle = new Bundle();
		int a = projectAdapter.getItem(position).getId();
		bundle.putInt("project", a);
		ProjectDetailsFragment_ pdf = new ProjectDetailsFragment_();
		pdf.setArguments(bundle);
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.content_frame, pdf);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	@UiThread
	void fillList () {
		projectAdapter.setItems(projects);
		myProjects.setAdapter(projectAdapter);
		projectAdapter.notifyDataSetChanged();
	}

}
