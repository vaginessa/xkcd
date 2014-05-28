package com.duobility.hackathons.xkcd.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.duobility.xkcd.R;
import com.duobility.hackathons.xkcd.activities.XkcdSyncActivity;
import com.duobility.hackathons.xkcd.data.Database;
import com.duobility.hackathons.xkcd.data.XKCDConstants.SharedPrefKeys.LastSync;
import com.duobility.hackathons.xkcd.data.XKCDConstants.TransitionAnimation;

public class SplashView extends XkcdSyncActivity {
	
	private TextView xkcdTitleTextView;
	private ProgressDialog firstRequestProgressDialog;
	private Animation fadeinAnimation;
	private Database db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_view);
		
		
		fadeinAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
		
		firstRequestProgressDialog = new ProgressDialog(SplashView.this, ProgressDialog.THEME_HOLO_DARK);
	}

	@Override
	protected void onStart() {
		super.onStart();
		initView();
		/* Check if there are entries in the Databases */
		db = new Database(getApplicationContext());
		db.open();
		if (db.getNumberOfComics() > 0) {
			/* Don't waste time on this screen */
			if ((readFromLastSyncPrefs() + LastSync.DELTA) < now()) {
				writeToLastSyncPrefs(now());
				requestJsonFromServer();
			} else {
				gotoView(new Intent(this, MainView.class), TransitionAnimation.FROM_RIGHT);
				finish();
			}
		} else {
			/* Get information for the first time
			 * Check if you user is connected to the Internet */
			updateInternetConnectionFlags();
			if (mobileConnected || wifiConnected) {
				showProgressDialog();
				requestJsonFromServer();
			} else {
				/* Tell the user they don't have Internet access */
				Toast noInternetToast = new Toast(getApplicationContext());
				noInternetToast.setGravity(Gravity.CENTER, 0, 0);
				noInternetToast.setText(R.string.noInternetAcces);
				noInternetToast.setDuration(Toast.LENGTH_LONG);
				noInternetToast.show();
			}
			/* End of Initial check */
		}
		db.close();
	}
	
	private void showProgressDialog() {
		firstRequestProgressDialog.getWindow().setGravity(Gravity.TOP);
		firstRequestProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		firstRequestProgressDialog.setTitle(getString(R.string.firstTimeIsee));
		firstRequestProgressDialog.setMessage(getString(R.string.gettingFromDB));
		firstRequestProgressDialog.show();
	}
	
	private void initView() {
		xkcdTitleTextView = (TextView) findViewById(R.id.xkcdTitle);
		
		xkcdTitleTextView.startAnimation(fadeinAnimation);
	}
	
}
