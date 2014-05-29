package com.paradiseoctopus.happysquirrel.adapters;

import java.util.List;

import org.androidannotations.annotations.EBean;

import android.content.Context;
import android.support.v7.internal.view.menu.MenuView.ItemView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.paradiseoctopus.happysquirrel.helpers.CommentView;
import com.paradiseoctopus.happysquirrel.helpers.CommentView_;
import com.paradiseoctopus.happysquirrel.models.Comment;

@EBean
public class ItemAdapter extends BaseAdapter{

	List<Comment> items;

	Context context;


	public ItemAdapter (Context context) {
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommentView view = null;
		if (convertView == null) {
			try {
				view = CommentView_.build(context);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		} 
		else {
			view = (CommentView) convertView;
		}
		view.bind(getItem(position));
		return view;
	}

	public void setItems (List<Comment> items) {
		this.items 	  = items;
	}

	public List<Comment> getItems () {
		return items;
	}

	@Override
	public int getCount() {
		return items== null? 0 : items.size();
	}

	@Override
	public Comment getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
