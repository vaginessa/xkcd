package com.duobility.hackathons.xkcd.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import com.duobility.hackathons.xkcd.data.XKCDConstants;
import com.duobility.hackathons.xkcd.data.XKCDConstants.Comic;

import android.os.AsyncTask;
import android.util.Log;

public class XkcdSyncActivity extends BaseActivity {
	
	private String CLASSTAG = XkcdSyncActivity.class.getSimpleName();
	
	@Override
	protected void onStart() {
		super.onStart();
		
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
					JSONObject comicObject = comicArray.getJSONObject(i);
					comicList.add(new Comic(
							comicObject.getInt(XKCDConstants.Json.ID), //Id
							comicObject.getString(XKCDConstants.Json.TITLE), // Title
							comicObject.getString(XKCDConstants.Json.URL), // URL
							comicObject.getString(XKCDConstants.Json.CAPTION)) // Caption
					);
					Log.d(CLASSTAG, comicObject.getString(XKCDConstants.Json.TITLE));
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
		protected void onPostExecute(ArrayList<Comic> comicArrayList) {
			super.onPostExecute(comicArrayList);
		}
		
	}
	
}
