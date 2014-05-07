package com.duobility.hackathons.xkcd.views;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.activities.XkcdSyncActivity;
import com.duobility.hackathons.xkcd.data.XKCDConstants;

import android.os.Bundle;
import android.widget.ImageView;

public class MainView extends XkcdSyncActivity {
	
	private ImageView comicImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
		
		findViews();
		
		// Temporary method
		aq.id(comicImage).image(XKCDConstants.testImageURLs[0]);
	}

	private void findViews() {
		comicImage = (ImageView) findViewById(R.id.comicImageView);
	}

}
