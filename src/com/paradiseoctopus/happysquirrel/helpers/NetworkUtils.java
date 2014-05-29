package com.paradiseoctopus.happysquirrel.helpers;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

	public static BitmapLruCache cache = new BitmapLruCache();
	private static boolean reachable = true;

	public static boolean serverIsReachable() {
		return reachable;
	}

	public static void setServerReachable (boolean isReachable) {
		reachable = isReachable;
	}

	public static boolean isNetworkOnline(Activity activity) {
		boolean status=false;
		try{
			ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getNetworkInfo(0);
			if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
				status = true;
			}else {
				netInfo = cm.getNetworkInfo(1);
				if(netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
					status = true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();  
			return false;
		}
		setServerReachable(status);
		return status;

	}  

}
