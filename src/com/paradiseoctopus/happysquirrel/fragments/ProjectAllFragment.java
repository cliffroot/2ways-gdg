package com.paradiseoctopus.happysquirrel.fragments;

import org.androidannotations.annotations.EFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paradiseoctopus.happysquirrel.MainActivity_;
import com.paradiseoctopus.happysquirrel.R;
import com.paradiseoctopus.happysquirrel.models.Project;
import com.paradiseoctopus.happysquirrel.models.ProjectList;

@EFragment
public class ProjectAllFragment extends Fragment implements OnMarkerClickListener {

	View view;

	GoogleMap map;

	ProjectList projectList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.project_all_fragment, container, false);
		setRetainInstance(true);

		MapFragment fragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.support_map_fragment);
		map = fragment.getMap();

		CameraPosition position = new CameraPosition.Builder().target(new LatLng(50.452012, 30.527369))
				.zoom(10.5f)
				.bearing(0)
				.tilt(25)
				.build();
		map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
		map.setOnMarkerClickListener(this);

		Thread thread = new Thread (new Runnable () {

			@Override
			public void run() {
				fetchFromServer ();

			}

		});
		thread.start();

		return view;
	}

	void fetchFromServer () {
		projectList = ((MainActivity_)getActivity()).restClient.getAllProjects();
		//Log.i("PROJECTLIST", projectList.getItems().get(0).toString());
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				populateMap (projectList);
			}
		});
	}

	void populateMap (ProjectList projectList) {
		for (Project project: projectList.getItems()) {
			map.addMarker(new MarkerOptions()
			.position(new LatLng(Double.parseDouble(project.getLatitude()), Double.parseDouble(project.getLongitude())))
			.title(project.getName())
			.snippet(project.getDescription()));
		}
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		try {
			MapFragment fragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.support_map_fragment);
			if (fragment != null) {
				getFragmentManager().beginTransaction().remove(fragment).commit();
			}
			else {

			}

		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		for (int i = 0; i < projectList.getItems().size(); i++ ) {
			if (projectList.getItems().get(i).getName().equals(marker.getTitle())) {
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				Fragment f = new ProjectDetailsFragment_();
				Bundle bundle = new Bundle();
				bundle.putInt("project", projectList.getItems().get(i).getId());
				f.setArguments(bundle);
				fragmentTransaction.replace(R.id.content_frame, f);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		}
		return false;
	}

}
