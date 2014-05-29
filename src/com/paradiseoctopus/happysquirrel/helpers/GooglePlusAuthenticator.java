package com.paradiseoctopus.happysquirrel.helpers;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

public class GooglePlusAuthenticator implements ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<LoadPeopleResult> {

	private GoogleApiClient mGoogleApiClient;
	private ConnectionResult mConnectionResult;
	private boolean mIntentInProgress;

	private boolean mSignInClicked;
	private Context context;

	private Connection connection;
	private Connection peopleConnection;

	public interface Connection  {
		public void onSuccess (JSONObject result);
		public void onFail ();
	}

	public GooglePlusAuthenticator (Context context, Connection connection, Connection peopleConnection) {
		this.context = context;
		this.connection = connection;
		this.peopleConnection = peopleConnection;
		mGoogleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API, null)
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();
		mGoogleApiClient.connect();

	}


	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), (Activity)context, 0).show();
			connection.onFail();
			return;
		}

		if (!mIntentInProgress) {
			mConnectionResult = result;
			if (mSignInClicked) {
				resolveSignInError();
			}
		}
	}

	public void signInWithGplus() {	
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		//Toast.makeText(context, "User is connected!", Toast.LENGTH_LONG).show();
		if (connection != null) {
			connection.onSuccess(getProfileInformation());
		}
		getPeople();
	}

	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult((Activity)context, 0);
			} catch (SendIntentException e) {
				e.printStackTrace();
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	public void getPeople () {
		if (!mGoogleApiClient.isConnected()) {
			mGoogleApiClient.connect();
		}
		PendingResult<LoadPeopleResult> prlpr = Plus.PeopleApi.loadVisible(mGoogleApiClient, People.OrderBy.BEST, null);
		prlpr.setResultCallback(this);
	}	

	public JSONObject getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {

				Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
				String personName = currentPerson.getDisplayName();
				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
				String id = currentPerson.getId(); // why not
				JSONObject user = new JSONObject();
				user.put("name", personName);
				user.put("email", email);
				user.put("id", id);
				user.put("photo", currentPerson.getImage().getUrl().replace("sz=50", "sz=200"));

				Log.i("Google", currentPerson.getImage().getUrl());

				return user;

			} else {
				Toast.makeText(context, "Person information is null :(", Toast.LENGTH_LONG).show();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void revokeGplusAccess() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			Log.i("LOGIN", "TRY ACCESS REVOKED");
			Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
				@Override
				public void onResult(Status arg0) {
					Log.i("LOGIN", "ACCESS REVOKED");
					mGoogleApiClient.connect();
				}

			});
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}


	public GoogleApiClient getmGoogleApiClient() {
		return mGoogleApiClient;
	}


	public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
		this.mGoogleApiClient = mGoogleApiClient;
	}


	public boolean ismIntentInProgress() {
		return mIntentInProgress;
	}


	public void setmIntentInProgress(boolean mIntentInProgress) {
		this.mIntentInProgress = mIntentInProgress;
	}


	public boolean ismSignInClicked() {
		return mSignInClicked;
	}


	public void setmSignInClicked(boolean mSignInClicked) {
		this.mSignInClicked = mSignInClicked;
	}


	@Override
	public void onResult(LoadPeopleResult loadPeopleResult) {
		if (!loadPeopleResult.getStatus().isSuccess()) {
			Log.e("Weee", loadPeopleResult.getStatus().toString());
			return;
		}
		if (peopleConnection != null) {
			PersonBuffer people = loadPeopleResult.getPersonBuffer();
			Log.d("Weee", "" + people.getCount());
			//ArrayList<User> friends = new ArrayList<User>();
			//			for(Person p : people) {
			//				//User user = new User();
			//				user.setName(p.getDisplayName());
			//				user.setImageUrl(p.hasImage()?p.getImage().getUrl():"");
			//				user.setEmail(p.getId()); //FIXME: get e-mail if possible?
			//				user.setPerson(p);//FIXME: too much data
			//				if (p.hasImage()) {
			//					friends.add(user);
			//				}
			//				Log.d("Weee", p.getDisplayName()); // For example.
			//			}
			//User.getCurrentUser().setFriends(friends);
			peopleConnection.onSuccess(null);
			//people.close();
		}

	}



}