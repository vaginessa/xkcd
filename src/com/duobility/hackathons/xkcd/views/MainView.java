package com.duobility.hackathons.xkcd.views;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.ImageOptions;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.activities.BaseActivity;
import com.duobility.hackathons.xkcd.activities.XkcdSyncActivity;
import com.duobility.hackathons.xkcd.data.XKCDConstants;
import com.duobility.hackathons.xkcd.data.XKCDConstants.Comic;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainView extends XkcdSyncActivity {
	
	private static boolean wifiConnected = false;
	private static boolean mobileConnected = false;
	
	private String CLASSTAG = MainView.class.getSimpleName();
	private ListView comicListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		initViews();
		requestJsonFromServer();
	}

	private void initViews() {
		/* Find all Views */
		comicListView = (ListView) findViewById(R.id.comicListView);
		
		/* Bind adapter to ListView */
		adapter = new ComicAdapter();
		comicListView.setAdapter(adapter);
	}
	
	/* Holds all the views for the comicListView */
	public static class ComicViewHolder {
		public ImageView comicImage;
		public TextView titleTextView;
		public TextView captionTextView;
	}
	
	public class ComicAdapter extends BaseAdapter {
		
		ImageOptions options = new ImageOptions();
		ArrayList<Comic> adapterArrayList = new ArrayList<Comic>();
				
		public void refreshList(ArrayList<Comic> inputList) {
			adapterArrayList = inputList;
			notifyDataSetChanged();
		}
		
		/* Override methods required by the BaseAdapter */		
		@Override
		public int getCount() {
			return adapterArrayList.size();
		}

		@Override
		public Comic getItem(int position) {
			return adapterArrayList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ComicViewHolder holder = new ComicViewHolder();
			convertView = null;
			
			options.fileCache = true;
			options.memCache = true;
			options.animation = AQuery.FADE_IN;
			
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.comic_card, parent, false);
				holder.comicImage = (ImageView) findViewById(R.id.comicImageView);
				holder.titleTextView = (TextView) findViewById(R.id.comicTitle);
				holder.captionTextView = (TextView) findViewById(R.id.comicCaption);
				convertView.setTag(holder);
			}
			
			aq.id(holder.comicImage).image(adapterArrayList.get(position).url); // Image applied
			//holder.titleTextView.setText(adapterArrayList.get(position).title); // Title applied
			//holder.captionTextView.setText(adapterArrayList.get(position).caption); // Caption applied
			
			return convertView;
		}
		
	}
	
}
