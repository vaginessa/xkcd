package com.duobility.hackathons.xkcd.views;

import android.os.Bundle;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.activities.XkcdSyncActivity;

public class SplashView extends XkcdSyncActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_view);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
}
