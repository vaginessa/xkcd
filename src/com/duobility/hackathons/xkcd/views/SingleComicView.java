package com.duobility.hackathons.xkcd.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.activities.SideActivity;
import com.duobility.hackathons.xkcd.data.XKCDConstants.BundleKeys;
import com.duobility.hackathons.xkcd.data.photoview.PhotoViewAttacher;

public class SingleComicView extends SideActivity {
	
	private String CLASSTAG = SingleComicView.class.getSimpleName();
	private Bundle extras;
	private ImageView imageview;
	private PhotoViewAttacher mAttacher;
	
	private String inputURL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singlecomicview);
		
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
		imageview = (ImageView) findViewById(R.id.singleComicDialogImageView);
		// set some kind of click listener
		aq.id(imageview).image(inputURL);
		mAttacher = new PhotoViewAttacher(imageview);
		mAttacher.update();
	}
	
}
