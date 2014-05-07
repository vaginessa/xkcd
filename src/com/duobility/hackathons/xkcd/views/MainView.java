package com.duobility.hackathons.xkcd.views;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.activities.XkcdSyncActivity;

import android.os.Bundle;
import android.widget.ListView;

public class MainView extends XkcdSyncActivity {
	
	private ListView comicListView; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
		
		findViews();
	}

	private void findViews() {
		comicListView = (ListView) findViewById(R.id.comicListView);
	}

}
