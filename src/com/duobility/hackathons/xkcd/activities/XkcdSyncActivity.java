package com.duobility.hackathons.xkcd.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import com.duobility.hackathons.xkcd.data.Database;
import com.duobility.hackathons.xkcd.data.XKCDConstants;
import com.duobility.hackathons.xkcd.data.XKCDConstants.Comic;
import com.duobility.hackathons.xkcd.data.XKCDConstants.TransitionAnimation;
import com.duobility.hackathons.xkcd.views.MainView;

public abstract class XkcdSyncActivity extends BaseActivity {
	
	public static String CLASSTAG = XkcdSyncActivity.class.getSimpleName();
	
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
	private class ProcessReceivedJSon extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... payload) {
			JSONObject xkcdObject = null;
			
			try {
				xkcdObject = new JSONObject(payload[0]);
				JSONArray comicArray = xkcdObject.getJSONArray(XKCDConstants.Json.ARRAY_TAG);
				
				/* Open Database connection */
				Database db = new Database(getApplicationContext());
				db.open();
				Comic newestComicOnDB = db.getNewestComic();
				
				/* Get all comics from JSon array */
				if (newestComicOnDB.title == comicArray.getJSONObject(0).getString(XKCDConstants.Json.TITLE)) {
					// DB is up to date therefore do nothing
				} else {
					processComicJsonArray(comicArray, db);
				}
				
				db.close(); // Close the Database connection
				
				/* Transition to next activity */
				gotoView(new Intent(XkcdSyncActivity.this, MainView.class), TransitionAnimation.FROM_RIGHT);
				finish();
				
				return true;
				
			} catch (JSONException e) {
				/* Return a null so we know that it failed */
				Log.e(CLASSTAG, e.toString());
				return false;
			}
		}

		private void processComicJsonArray(JSONArray comicArray, Database db) throws JSONException {
			for (int i = 0; i < comicArray.length(); i++) {
				boolean addToComicList = false;
				JSONObject comicObject = comicArray.getJSONObject(i);
				
				int id = comicObject.getInt(XKCDConstants.Json.ID);
				String title = comicObject.getString(XKCDConstants.Json.TITLE);
				String url = comicObject.getString(XKCDConstants.Json.URL);
				String caption = comicObject.getString(XKCDConstants.Json.CAPTION);
				
				/* Change boolean to true if it is okay to add */
				addToComicList = checkToAddComicToComicList(id, title, url, caption);
				
				if (addToComicList && !db.doesComicExists(url)) {
					db.putEntry(id, title, url, caption);
					Log.d(CLASSTAG, "[ADDED] " + title);
				} else {
					Log.d(CLASSTAG, "[OMIT] " + title);
				}
				
			} // End of for loop
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
