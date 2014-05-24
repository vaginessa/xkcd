package com.duobility.hackathons.xkcd.activities;

import com.duobility.hackathons.xkcd.data.XKCDConstants.BundleKeys;
import com.duobility.hackathons.xkcd.data.XKCDConstants.TransitionAnimation;
import com.duobility.hackathons.xkcd.views.MainView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;

public abstract class SideActivity extends BaseActivity {
	
	protected Parcelable listState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			goBack();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		goBack();
		super.onBackPressed();
	}
	
	protected void goBack() {
		Intent goBackIntent = new Intent(getApplicationContext(), MainView.class);
		Bundle extras = new Bundle();
		extras.putParcelable(BundleKeys.LISTSTATE, listState);
		extras.putBoolean(BundleKeys.FROM_SIDE_ACTIVITY, true);
		goBackIntent.putExtras(extras);
		gotoView(goBackIntent, TransitionAnimation.FROM_LEFT);
	}
	
}
