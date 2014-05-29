package com.paradiseoctopus.happysquirrel.helpers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;

import com.paradiseoctopus.happysquirrel.fragments.NewMessageDialog_;


/*
 * Class for showing different dialogs.
 */
public class DialogHelper {

	public static int id;
	public static Integer po;

	public static Fragment fragment;

	public static void showErrorDialog(Activity activity, int int1, Integer ida, Fragment fragment1) {
		id = int1;
		po = ida;
		fragment = fragment1;
		FragmentManager fm = activity.getFragmentManager();
		NewMessageDialog_ newWishDialog = new NewMessageDialog_();

		//give NewWishDialog a WishListFragment
		newWishDialog.show(fm, "fragment_new_wish");

	}
}
