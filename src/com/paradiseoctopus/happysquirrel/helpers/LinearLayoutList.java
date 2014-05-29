package com.paradiseoctopus.happysquirrel.helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.paradiseoctopus.happysquirrel.adapters.ItemAdapter;


public class LinearLayoutList extends LinearLayout {

	private Context context;

	public LinearLayoutList(Context context) {
		super(context);
		this.context = context;
		setOrientation(LinearLayout.VERTICAL);
	}

	public LinearLayoutList(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		setOrientation(LinearLayout.VERTICAL);
	}

	public void setAdapter (ItemAdapter adapter) {
		removeAllViews();
		Double total = 0.0;
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
		dlp.leftMargin = 18;
		dlp.rightMargin = 18;
		dlp.topMargin = 2;
		dlp.bottomMargin = 2;
		for (int i = 0; i < adapter.getCount(); i++) {
			View v = adapter.getView(i, null, this);
			this.addView(v, llp);

			if (i != adapter.getCount() - 1) {
				View divider = new View(context);
				divider.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
				this.addView(divider, dlp);
			}
		}

	}

}
