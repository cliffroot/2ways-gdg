package com.paradiseoctopus.happysquirrel.helpers;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.paradiseoctopus.happysquirrel.MainActivity_;
import com.paradiseoctopus.happysquirrel.R;
import com.paradiseoctopus.happysquirrel.models.Project;

@EViewGroup(R.layout.project_view)
public class ProjectView extends LinearLayout{

	@ViewById(R.id.project_photo)
	NetworkImageView photo;

	@ViewById(R.id.project_name)
	TextView projectName;

	@ViewById(R.id.project_description)
	TextView projectDescription;

	Context context;

	public ProjectView(Context context) {
		super(context);
		this.context = context;
	}


	public void bind (Project item) {
		ImageLoader mImageLoader = new ImageLoader(((MainActivity_) context).rQueue, ((MainActivity_) context).cache);
		try {
			photo.setImageUrl("http://172.31.151.64:3000/images/" + item.getPhotos().get(0).getAvatar_file_name(), mImageLoader);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		projectName.setText(item.getName());
		projectDescription.setText(item.getDescription());//
	}


}
