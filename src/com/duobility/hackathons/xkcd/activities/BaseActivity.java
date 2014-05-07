package com.duobility.hackathons.xkcd.activities;

import com.androidquery.AQuery;
import com.duobility.hackathons.xkcd.data.Fonts;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity {
	
	protected Fonts mFonts = new Fonts();
	protected AQuery aq;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aq = new AQuery(this);
	}
	
}
