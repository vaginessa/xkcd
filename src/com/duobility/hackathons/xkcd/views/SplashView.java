package com.duobility.hackathons.xkcd.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.activities.XkcdSyncActivity;
import com.duobility.hackathons.xkcd.data.Database;
import com.duobility.hackathons.xkcd.data.XKCDConstants.SharedPrefKeys.LastSync;

public class SplashView extends XkcdSyncActivity {
	
	private TextView xkcdTitleTextView;
	private Animation fadeinAnimation;
	private Database db;
	
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
		/* Check if there are entries in the Databases */
		db = new Database(getApplicationContext());
		db.open();
		if (db.getNumberOfComics() > 0) {
			/* Don't waste time on this screen */
			if ((readFromLastSyncPrefs() + LastSync.DELTA) < now()) {
				writeToLastSyncPrefs(now());
				requestJsonFromServer();
			} else {
				gotoMainView();
			}
		} else {
			/* Get information for the first time */
			requestJsonFromServer();
		}
		db.close();
	}

	private void gotoMainView() {
		gotoView(new Intent(this, MainView.class));
		activityTransitionAnimation_fromRight();
		finish();
	}
	
	private void initView() {
		xkcdTitleTextView = (TextView) findViewById(R.id.xkcdTitle);
		
		xkcdTitleTextView.startAnimation(fadeinAnimation);
	}
	
}
