package com.duobility.hackathons.xkcd.activities;

import com.androidquery.AQuery;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.data.Fonts;
import com.duobility.hackathons.xkcd.data.XKCDConstants.SharedPrefKeys.LastSync;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
	
	/* Intents */
	protected void gotoView(Intent toViewIntent) {
		startActivity(toViewIntent);
	}
	
	protected void activityTransitionAnimation_fromRight() {
		overridePendingTransition(R.anim.activity_enter_fromright, R.anim.activity_nomotion);
	}
}
