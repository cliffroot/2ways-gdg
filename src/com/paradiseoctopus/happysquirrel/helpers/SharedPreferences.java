package com.paradiseoctopus.happysquirrel.helpers;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface SharedPreferences {
	
	String user();
	
	boolean logged();
	
	String password();
	
	@DefaultBoolean(true)
	boolean isFirstStart();
	
}