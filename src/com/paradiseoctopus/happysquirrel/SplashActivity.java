package com.paradiseoctopus.happysquirrel;

import java.util.UUID;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.paradiseoctopus.happysquirrel.adapters.SplashScreenAdapter;
import com.paradiseoctopus.happysquirrel.helpers.CustomRestErrorHandler;
import com.paradiseoctopus.happysquirrel.helpers.DataProvider;
import com.paradiseoctopus.happysquirrel.helpers.GooglePlusAuthenticator;
import com.paradiseoctopus.happysquirrel.helpers.SharedPreferences_;
import com.paradiseoctopus.happysquirrel.models.User;
import com.viewpagerindicator.LinePageIndicator;

/*
 * Activity which demonstrates how cool the application is. It has a view pager with several screens. On the final screen there should be some login/register options
 */
@EActivity(R.layout.splash_activity)
public class SplashActivity extends FragmentActivity {

	// For some fancy effects
	@ViewById(R.id.pager)
	ViewPager pager;

	// Lines in the bottom of the screen indicating the position of current
	// screen
	@ViewById
	LinePageIndicator circles;

	// Preferences from which we might get user if already logged in
	@Pref
	SharedPreferences_ prefs;

	// Rest Client for registering user on the server
	@RestService
	DataProvider dataProvider;

	// Error handler for rest client if something goes wrong (no internet,
	// server is down)
	@Bean
	CustomRestErrorHandler errorHandler;

	// Class to login with Google Plus
	GooglePlusAuthenticator googleAuthenticator;

	// Adapter which holds the screens
	SplashScreenAdapter myFragmentPagerAdapter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.holoeverywhere.app.Activity#onCreate(android.os.Bundle) in here we decide if to show the motivational screens or just to let user
	 * in the app. we let him in if he's already logged in
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// check if we're online
		checkOnline();

		if (prefs.logged().get()) {
			Intent intent = new Intent(SplashActivity.this, MainActivity_.class);//
			try {
				JSONObject usr = new JSONObject(prefs.user().get());
				User.setCurrentUser(usr.getString("id"), prefs.password().get(), usr.getString("photo"), usr.getString("name"));
				SplashActivity.this.startActivity(intent);
				finish();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else {

			googleAuthenticator = new GooglePlusAuthenticator(this, new GooglePlusAuthenticator.Connection() {
				@Override
				public void onSuccess(JSONObject result) {
					String uuid = UUID.randomUUID().toString();
					try {
						User.setCurrentUser(result.getString("id"), uuid, result.getString("photo"), result.getString("name"));
						registerUser(User.getCurrentUser());
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}

					Intent intent = new Intent(getApplicationContext(), MainActivity_.class);

					try {
						String name = result.getString("name");
						result.getString("email");
						String imageUrl = result.getString("photo");
						User.setCurrentUser("", "", imageUrl, name);
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
					prefs.logged().put(true); // user is logged
					prefs.user().put(result.toString()); // json of user
					prefs.password().put(uuid); // remember password
					SplashActivity.this.startActivity(intent);
					finish();
				}

				@Override
				public void onFail() {
				}
			}, null);
		}
	}

	@Background
	void checkOnline() {
		// dataProvider.checkOnline();
	}

	//public static int uId;

	@Background
	void registerUser(User user) {
		User savedUser = dataProvider.registerUser(user);

		SharedPreferences s = getSharedPreferences("prefs", Context.MODE_PRIVATE);
		s.edit().putInt("user_id", savedUser.getId()).commit();
		//uId = savedUser.getId();
		Log.w("test", "user id " + savedUser.getId());
	}

	@AfterInject
	void afterInject() {
		dataProvider.setRestErrorHandler(errorHandler);
	}

	/*
	 * Set adapter for view pager. May change effects (TransitionEffect) Should also give pager to the indicator
	 */
	@AfterViews
	public void setAdapter() {
		myFragmentPagerAdapter = new SplashScreenAdapter(getSupportFragmentManager(), pager);
		// pager.setTransitionEffect(TransitionEffect.Stack);
		pager.setAdapter(myFragmentPagerAdapter);
		circles.setViewPager(pager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.holoeverywhere.app.Activity#onActivityResult(int, int, android.content.Intent) Some crazy shit needed for google sign in
	 */
	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		if (requestCode == 0) {
			if (responseCode != RESULT_OK) {
				googleAuthenticator.setmSignInClicked(false);
			}
			googleAuthenticator.setmIntentInProgress(false);
			if (!googleAuthenticator.getmGoogleApiClient().isConnecting()) {
				googleAuthenticator.getmGoogleApiClient().connect();
			}
		}
	}

	public GooglePlusAuthenticator getGoogleAuthenticator() {
		return googleAuthenticator;
	}

}