package com.duobility.hackathons.xkcd.activities;

import com.androidquery.AQuery;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.data.Fonts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity {
	
	public static String CLASSTAG = BaseActivity.class.getSimpleName();
	
	protected static boolean wifiConnected = false;
	protected static boolean mobileConnected = false;
	
	protected Fonts mFonts = new Fonts();
	protected AQuery aq;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aq = new AQuery(this);
	}
	
	/* Intents */
	protected void gotoView(Intent toViewIntent) {
		startActivity(toViewIntent);
	}
	
	protected void activityTransitionAnimation_fromRight() {
		overridePendingTransition(R.anim.activity_enter_fromright, R.anim.activity_nomotion);
	}
}
