package com.paradiseoctopus.happysquirrel.fragments;

import java.io.File;
import java.io.FileNotFoundException;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.paradiseoctopus.happysquirrel.MainActivity_;
import com.paradiseoctopus.happysquirrel.R;
import com.paradiseoctopus.happysquirrel.helpers.DataProvider;
import com.paradiseoctopus.happysquirrel.models.Project;

@EFragment(R.layout.project_create_fragment)
public class ProjectCreateFragment extends Fragment {
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;

	private Uri mImageCaptureUri;

	private LayoutInflater li;

	private int currentGoalProgress;

	AlertDialog dialog;

	@ViewById(R.id.create_project_project_name)
	EditText projectName;

	@ViewById(R.id.create_project_project_description)
	EditText projectDescription;

	@RestService
	DataProvider restClient;

	@ViewById(R.id.goalLabel)
	TextView goalLabel;

	@ViewById(R.id.takephoto)
	Button takePhoto;

	@ViewById(R.id.goal)
	SeekBar goal;

	@ViewById(R.id.photo_preview1)
	ImageView photoPreview;

	@SuppressWarnings("deprecation")
	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);

		if (cursor == null) {
			return null;
		}

		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		cursor.moveToFirst();

		return cursor.getString(column_index);
	}

	String path = "";

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		Bitmap bitmap = null;
		path = "";

		if (requestCode == PICK_FROM_FILE) {
			mImageCaptureUri = data.getData();
			path = getRealPathFromURI(mImageCaptureUri); // from Gallery

			if (path == null) {
				path = mImageCaptureUri.getPath(); // from File Manager
			}

			if (path != null) {
				bitmap = BitmapFactory.decodeFile(path);
			}
		}
		else {
			path = mImageCaptureUri.getPath();
			bitmap = BitmapFactory.decodeFile(path);
		}

		photoPreview.setImageBitmap(bitmap);
	}

	public void postImage(int id) {
		if (!path.equals("")) {
			RequestParams params = new RequestParams();
			params.put("photo[project_id]", id + "");
			try {
				params.put("photo[avatar]", new File(path));
			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AsyncHttpClient client = new AsyncHttpClient();
			client.post("http://172.31.151.64:3000/photos/", params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String response) {
					Log.w("async", "success!!!!");
				}
			});
		}
	}

	int id;

	@Background
	void newProject(Project project) {
		Project pr = restClient.newProject(project);
		id = pr.getId();
		postImage(pr.getId());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final ActionBar actionBar = getActivity().getActionBar();

		setRetainInstance(true);
		setHasOptionsMenu(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		// actionBar.setHomeButtonEnabled(true);
		getActivity().setTitle("Edit profile");

		li = inflater;
		actionBarButtons();

		final String[] items = new String[] { "From Camera", "From SD Card" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle("Select Image");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (item == 0) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File file = new File(Environment.getExternalStorageDirectory(), "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
					mImageCaptureUri = Uri.fromFile(file);

					try {
						intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
						intent.putExtra("return-data", true);

						startActivityForResult(intent, PICK_FROM_CAMERA);
					}
					catch (Exception e) {
						e.printStackTrace();
					}

					dialog.cancel();
				}
				else {
					Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(Intent.createChooser(galleryIntent, "Select Source"), PICK_FROM_FILE);
				}
			}
		});

		dialog = builder.create();

		return null;
	}

	Spinner spinner;

	@SuppressLint("NewApi")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		spinner = (Spinner) getView().findViewById(R.id.create_project_category_spinner);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.project_categs, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter2);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			// Respond to the action bar's Up/Home button
			case android.R.id.home:
				getActivity().onBackPressed();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPause() {
		final ActionBar actionBar = getActivity().getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);

		super.onPause();
	}

	@Override
	public void onResume() {
		actionBarButtons();
		super.onResume();
	}

	void actionBarButtons() {
		final ActionBar actionBar = getActivity().getActionBar();
		View actionBarButtons = li.inflate(R.layout.edit_profile_bar, new LinearLayout(getActivity()), false);
		final View cancelActionView = actionBarButtons.findViewById(R.id.action_cancel);
		// actionBar.setHomeButtonEnabled(false);
		cancelActionView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
				// getFragmentManager().beginTransaction().replace(R.id.content_frame, new ProjectAllFragment()).commit();
				getFragmentManager().popBackStack();

			}
		});

		View doneActionView = actionBarButtons.findViewById(R.id.action_done);
		doneActionView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Project project = new Project();
				project.setName(projectName.getText() + "");
				project.setDescription(projectDescription.getText() + "");
				project.setGoal((double) currentGoalProgress);

				project.setCategory_name((String)spinner.getSelectedItem());

				SharedPreferences s = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
				int user_id = s.getInt("user_id", -1);
				project.setPerson_id(user_id);

				try {
					project.setLatitude(String.valueOf(MainActivity_.location.getLatitude()));
					project.setLongitude(String.valueOf(MainActivity_.location.getLongitude()));
				}
				catch (Exception e) {
					e.printStackTrace();

					project.setLatitude("50.448624");
					project.setLongitude("30.523335");
				}

				newProject(project);

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage("Project was successfully created").setTitle("Success") // FIXME: FIXME: FIXME: FIXME: ;
				.setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int pid) {
						dialog.dismiss();
						cancelActionView.performClick();
						// Bundle bundle = new Bundle();
						// bundle.putInt("project", id);
						//
						// ProjectDetailsFragment_ pdf = new ProjectDetailsFragment_();
						// pdf.setArguments(bundle);
						// FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
						// fragmentTransaction.replace(R.id.content_frame, pdf);
						// fragmentTransaction.addToBackStack(null);
						// fragmentTransaction.commit();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
				// cancelActionView.performClick();

			}
		});
		actionBar.setCustomView(actionBarButtons);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);

	}

	@Click
	void takephoto() {
		dialog.show();
	}

	@SeekBarProgressChange
	public void goal(int progress) {
		currentGoalProgress = progress * 50;
		goalLabel.setText("Goal: " + String.valueOf(currentGoalProgress) + " UAH");
	}
}
