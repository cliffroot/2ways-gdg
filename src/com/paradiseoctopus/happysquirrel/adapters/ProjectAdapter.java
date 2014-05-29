package com.paradiseoctopus.happysquirrel.adapters;

import java.util.List;

import org.androidannotations.annotations.EBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.paradiseoctopus.happysquirrel.helpers.ProjectView;
import com.paradiseoctopus.happysquirrel.helpers.ProjectView_;
import com.paradiseoctopus.happysquirrel.models.Project;

@EBean
public class ProjectAdapter extends BaseAdapter{

	List<Project> items;

	Context context;


	public ProjectAdapter (Context context) {
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ProjectView view = null;
		if (convertView == null) {
			try {
				view = ProjectView_.build(context);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		} 
		else {
			view = (ProjectView) convertView;
		}
		view.bind(getItem(position));
		return view;
	}

	public void setItems (List<Project> items) {
		this.items 	  = items;
	}

	public List<Project> getItems () {
		return items;
	}

	@Override
	public int getCount() {
		return items== null? 0 : items.size();
	}

	@Override
	public Project getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
