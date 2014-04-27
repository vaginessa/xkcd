package com.duobility.hackathons.xkcd.views;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.R.id;
import com.duobility.hackathons.xkcd.R.layout;
import com.duobility.hackathons.xkcd.R.menu;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainView extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
	}

}
