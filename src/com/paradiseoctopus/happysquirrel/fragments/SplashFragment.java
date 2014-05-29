package com.paradiseoctopus.happysquirrel.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;
import com.paradiseoctopus.happysquirrel.R;
import com.paradiseoctopus.happysquirrel.SplashActivity;

@EFragment(R.layout.splash_fragment)
public class SplashFragment extends Fragment {
	
	@SuppressLint("InlinedApi")
	int[] COLORS = new int[] { android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light };
	
	// final int[] ANIMALS= new int[] {R.drawable.seal, R.drawable.penguin, R.drawable.giraffe};
	
	private int index;
	
	@ViewById
	LinearLayout background;
	
	@ViewById
	ImageView logo;
	
	@ViewById
	SignInButton signIn;
	
	@ViewById
	TextView motivational;
	
	public static SplashFragment newInstance(int index) {
		
		SplashFragment_ splashFragment = new SplashFragment_();
		Bundle bundle = new Bundle();
		bundle.putInt("pageNumber", index);
		splashFragment.setArguments(bundle);
		return splashFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		index = getArguments().getInt("pageNumber");
	}
	
	@Click
	void signIn() {
		SplashActivity splash = (SplashActivity) getActivity();
		splash.getGoogleAuthenticator().signInWithGplus();
		// splash.getGoogleAuthenticator().revokeGplusAccess();
	}
	
	@AfterViews
	public void setPage() {
		background.setBackgroundResource(COLORS[index]);
		signIn.setVisibility(index == 2 ? View.VISIBLE : View.INVISIBLE);
	}
}
