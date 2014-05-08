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

public class MainView extends BaseActivity {
	
	private String CLASSTAG = MainView.class.getSimpleName();
	private ComicAdapter adapter;
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
	
	protected void requestJsonFromServer() {
		/* Ask Back end for JSon list */
		aq.ajax(XKCDConstants.Urls.RECEIVE_JSON, String.class, new AjaxCallback<String>(){
			@Override
			public void callback(String url, String response, AjaxStatus status) {
				super.callback(url, response, status);
				
				if (receivedJSon(response)) {
					new ProcessReceivedJSon().execute(response);
				}
			}
		});
	}
	
	private boolean receivedJSon(String response) {
		if (response != "") {
			return true; // There is content
		} else {
			return false; // There was no content
		}
	}
	
	/* Background task for getting the 
	 * XKCD comics and putting them in
	 * an arrayList */
	private class ProcessReceivedJSon extends AsyncTask<String, Integer, ArrayList<Comic>> {

		@Override
		protected ArrayList<Comic> doInBackground(String... payload) {
			ArrayList<Comic> comicList = new ArrayList<Comic>();
			JSONObject xkcdObject = null;
			
			try {
				xkcdObject = new JSONObject(payload[0]);
				JSONArray comicArray = xkcdObject.getJSONArray(XKCDConstants.Json.ARRAY_TAG);
				
				/* Get all comics from JSon array */
				for (int i = 0; i < comicArray.length(); i++) {
					boolean addToComicList = false;
					JSONObject comicObject = comicArray.getJSONObject(i);
					
					int id = comicObject.getInt(XKCDConstants.Json.ID);
					String title = comicObject.getString(XKCDConstants.Json.TITLE);
					String url = comicObject.getString(XKCDConstants.Json.URL);
					String caption = comicObject.getString(XKCDConstants.Json.CAPTION);
					
					addToComicList = checkToAddComicToComicList(
							id, // ID
							title, // Title 
							url, // URL 
							caption // Caption
							);
					
					if (addToComicList) {
						comicList.add(new Comic(id, title, url, caption));
						Log.d(CLASSTAG, "[ADDED] " + title);
					} else {
						Log.d(CLASSTAG, "[OMIT] " + title);
					}
					
				} // End of for loop
				
				/* Return the Comic ArrayList */
				return comicList;
				
			} catch (JSONException e) {
				/* Return a null so we know that it failed */
				Log.e(CLASSTAG, e.toString());
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(ArrayList<Comic> comicArrayListResult) {
			super.onPostExecute(comicArrayListResult);
			adapter.refreshList(comicArrayListResult);
		}
		
	}

	private boolean checkToAddComicToComicList(int id, String title, String url, String caption) {
		if ((title == "") || (url == "") || (caption == "")) {
			return false; // Because some critical piece of information is missing
		} else {
			return true;
		}
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
		
		public void initList() {
			requestJsonFromServer();
		}
		
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
			holder.titleTextView.setText(adapterArrayList.get(position).title); // Title applied
			holder.captionTextView.setText(adapterArrayList.get(position).caption); // Caption applied
			
			return convertView;
		}
		
	}
	
}
