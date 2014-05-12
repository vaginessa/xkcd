package com.duobility.hackathons.xkcd.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.activities.BaseActivity;
import com.duobility.hackathons.xkcd.data.XKCDConstants.BundleKeys;

public class SingleComicView extends BaseActivity {
	
	private String CLASSTAG = SingleComicView.class.getSimpleName();
	private RelativeLayout layout;
	private ImageView imageview;
	
	private String inputURL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		layout = new RelativeLayout(this);
		layout.setBackgroundResource(R.drawable.viewphoto_background);
		setContentView(layout);
		
		/* Get Bundle Information */
		inputURL = getIntent().getStringExtra(BundleKeys.SINGLE);
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
	}
	
}
