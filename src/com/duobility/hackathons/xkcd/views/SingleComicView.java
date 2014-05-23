package com.duobility.hackathons.xkcd.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.activities.SideActivity;
import com.duobility.hackathons.xkcd.data.XKCDConstants.BundleKeys;
import com.duobility.hackathons.xkcd.data.photoview.PhotoViewAttacher;

public class SingleComicView extends SideActivity {
	
	private String CLASSTAG = SingleComicView.class.getSimpleName();
	private Bundle extras;
	private RelativeLayout layout;
	private ImageView imageview;
	private PhotoViewAttacher mAttacher;
	
	private String inputURL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		layout = new RelativeLayout(this);
		layout.setBackgroundResource(R.drawable.viewphoto_background);
		setContentView(layout);
		
		/* Get Bundle Information */
		extras = getIntent().getExtras();
		inputURL = extras.getString(BundleKeys.SINGLE);
		listState = extras.getParcelable(BundleKeys.LISTSTATE);
		Log.d(CLASSTAG, "received String: " + inputURL);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		buildUI();
	}

	private void buildUI() {
		/* Start building UI elements */
		imageview = new ImageView(this);
		// set some kind of click listener
		aq.id(imageview).image(inputURL);
		layout.addView(imageview, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mAttacher = new PhotoViewAttacher(imageview);
		mAttacher.update();
	}
	
}
