package com.duobility.hackathons.xkcd.views;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.activities.XkcdSyncActivity;

public class SplashView extends XkcdSyncActivity {
	
	private TextView xkcdTitleTextView;
	private Animation fadeinAnimation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_view);
		
		fadeinAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
		
		initView();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		requestJsonFromServer();
	}
	
	private void initView() {
		xkcdTitleTextView = (TextView) findViewById(R.id.xkcdTitle);
		
		xkcdTitleTextView.startAnimation(fadeinAnimation);
	}
	
}
