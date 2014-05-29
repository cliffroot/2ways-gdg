package com.paradiseoctopus.happysquirrel.helpers;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.paradiseoctopus.happysquirrel.MainActivity_;
import com.paradiseoctopus.happysquirrel.R;
import com.paradiseoctopus.happysquirrel.models.Comment;

@EViewGroup(R.layout.view_comment)
public class CommentView extends LinearLayout{

	@ViewById(R.id.comment_photo)
	NetworkImageView photo;

	@ViewById(R.id.comment_user)
	TextView userName;

	@ViewById(R.id.comment_text)
	TextView commentText;

	Context context;

	public CommentView(Context context) {
		super(context);
		this.context = context;
	}


	public void bind (Comment item) {
		ImageLoader mImageLoader = new ImageLoader(((MainActivity_) context).rQueue, ((MainActivity_) context).cache);
		photo.setImageUrl(item.getPerson().getAvatar_url(), mImageLoader);

		userName.setText(item.getPerson().getName());
		commentText.setText(item.getBody());//
	}


}
