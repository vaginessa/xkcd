package com.duobility.hackathons.xkcd.activities;

import java.io.File;

import com.androidquery.AQuery;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.data.Fonts;
import com.duobility.hackathons.xkcd.data.XKCDConstants.SharedPrefKeys.LastSync;
import com.duobility.hackathons.xkcd.data.XKCDConstants.TransitionAnimation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public abstract class BaseActivity extends Activity {
	
	public static String CLASSTAG = BaseActivity.class.getSimpleName();
	
	protected static boolean wifiConnected = false;
	protected static boolean mobileConnected = false;
	
	protected Fonts mFonts = new Fonts();
	protected SharedPreferences sharedPreferences;
	protected Context baseActivityContext;
	protected AQuery aq;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aq = new AQuery(this);
		
		/* Shared Preferences */
		baseActivityContext = getApplicationContext();
		sharedPreferences = baseActivityContext.getSharedPreferences(LastSync.LASTSYNC, Context.MODE_PRIVATE);
	}
	
	protected void updateInternetConnectionFlags() {
		ConnectivityManager connectManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeInfo = connectManager.getActiveNetworkInfo();
		if ((activeInfo != null) && (activeInfo.isConnected())) {
			wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
			mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
		} else {
			wifiConnected = false;
			mobileConnected = false;
		}
	}
	
	protected boolean writeToLastSyncPrefs(int time) {
		Log.d(CLASSTAG, String.valueOf(time));
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(LastSync.LASTSYNC, time);
		editor.commit();
		
		if (readFromLastSyncPrefs() == time) {
			return true;
		} else {
			return false;
		}
	}
	
	protected int readFromLastSyncPrefs() {
		return sharedPreferences.getInt(LastSync.LASTSYNC, LastSync.DEFAULTVALUE);
	}
	
	protected int now() {
		long unixSeconds = System.currentTimeMillis() / 1000L;
		return (int) unixSeconds;
	}
	
	/* Intents */
	protected boolean shareComicIntent(String comicURL, String comicTitle) {
		File imageFile = aq.makeSharedFile(comicURL, comicTitle);
		if ((imageFile != null) && (!comicTitle.equals(""))) {
			Intent shareComicIntent = new Intent(Intent.ACTION_SEND);
			shareComicIntent.setType("image/*");
			shareComicIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile));
			startActivity(Intent.createChooser(shareComicIntent, getResources().getString(R.string.shareIntent)));
			return true;
		} else {
			return false;
		}
	}
	
	protected void gotoView(Intent toViewIntent, int transitionAnimation) {
		startActivity(toViewIntent);
		
		switch (transitionAnimation) {
		case TransitionAnimation.FROM_LEFT:
			activityTransitionAnimation_fromLeft();
			break;
			
		case TransitionAnimation.FROM_RIGHT:
			activityTransitionAnimation_fromRight();
			break;

		default:
			break;
		}
	}
	
	protected void gotoView(Intent toViewIntent) {
		startActivity(toViewIntent);
	}
	
	protected void activityTransitionAnimation_fromRight() {
		overridePendingTransition(R.anim.activity_enter_fromright, R.anim.activity_nomotion);
	}
	
	protected void activityTransitionAnimation_fromLeft() {
		overridePendingTransition(R.anim.activity_enter_fromleft, R.anim.activity_nomotion);
	}
}
