package com.duobility.hackathons.xkcd.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.duobility.hackathons.xkcd.data.XKCDConstants;
import com.duobility.hackathons.xkcd.data.XKCDConstants.Comic;
import com.duobility.hackathons.xkcd.views.MainView.ComicAdapter;

public abstract class XkcdSyncActivity extends BaseActivity {
	
	private String CLASSTAG = XkcdSyncActivity.class.getSimpleName();
	
	protected ComicAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		if ((title != "") || (url != "") || (caption != "")) {
			return true;
		} else {
			return false; // Because some critical piece of information is missing
		}
	}
	
}
