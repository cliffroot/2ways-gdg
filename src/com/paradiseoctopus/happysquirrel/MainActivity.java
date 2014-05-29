package com.paradiseoctopus.happysquirrel;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.rest.RestService;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import com.paradiseoctopus.happysquirrel.fragments.ProjectCreateFragment_;
import com.paradiseoctopus.happysquirrel.helpers.DataProvider;

/*
 * Some stuff may go in here (database helper e.g)
 */
@EActivity(R.layout.base_activity)
public class MainActivity extends BaseActivity {

	@RestService
	public DataProvider restClient;

	public static Location location;

	public void createProject(View v) {
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

		fragmentTransaction.replace(R.id.content_frame, new ProjectCreateFragment_());
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, false);
		locationManager.getBestProvider(criteria, false);

		location = locationManager.getLastKnownLocation(provider);

		getProjects();
		
	}

	@Background
	void getProjects() {
		// ProjectList pl = restClient.getAllProjects();
		// Log.i("PROJECTS", pl.getItems().get(0).toString());

	}

}
