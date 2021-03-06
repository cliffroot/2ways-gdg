package com.paradiseoctopus.happysquirrel;

import org.androidannotations.annotations.EActivity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.paradiseoctopus.happysquirrel.fragments.SettingsFragment;

@EActivity
public class SettingsActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Display the fragment as the main content.
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
	}
	
}
