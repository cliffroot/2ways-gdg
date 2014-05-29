package com.paradiseoctopus.happysquirrel.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.paradiseoctopus.happysquirrel.MainActivity_;
import com.paradiseoctopus.happysquirrel.R;
import com.paradiseoctopus.happysquirrel.models.User;

@EFragment(R.layout.profile_fragment)
public class ProfileFragment extends Fragment {
	
	@ViewById(R.id.user_photo)
	NetworkImageView photo;
	
	@ViewById(R.id.user_name)
	TextView userName;
	
	@AfterViews
	void fillProfile() {
		ImageLoader mImageLoader = new ImageLoader(((MainActivity_) getActivity()).rQueue, ((MainActivity_) getActivity()).cache);
		photo.setImageUrl(User.getCurrentUser().getAvatar_url(), mImageLoader);
		userName.setText(User.getCurrentUser().getName());
		
	}
}
