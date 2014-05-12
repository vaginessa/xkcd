package com.duobility.hackathons.xkcd.activities;

import com.duobility.hackathons.xkcd.data.XKCDConstants.BundleKeys;
import com.duobility.hackathons.xkcd.views.MainView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

public abstract class SideActivity extends BaseActivity {
	
	protected Parcelable listState;
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		goBack();
	}
	
	protected void goBack() {
		Intent goBackIntent = new Intent(getApplicationContext(), MainView.class);
		Bundle extras = new Bundle();
		extras.putParcelable(BundleKeys.LISTSTATE, listState);
		goBackIntent.putExtras(extras);
		gotoView(goBackIntent);
	}
	
}
