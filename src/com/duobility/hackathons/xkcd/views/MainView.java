package com.duobility.hackathons.xkcd.views;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.activities.BaseActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class MainView extends BaseActivity {
	
	private ImageView comicImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
		
		findViews();
		
		// Temporary method
		aq.id(comicImage).image("http://imgs.xkcd.com/comics/old_files.png");
	}

	private void findViews() {
		comicImage = (ImageView) findViewById(R.id.comicImageView);
	}

}
