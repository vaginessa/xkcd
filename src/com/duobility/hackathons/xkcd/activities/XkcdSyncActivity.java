package com.duobility.hackathons.xkcd.activities;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.duobility.hackathons.xkcd.data.XKCDConstants;

import android.os.AsyncTask;
import android.util.Log;

public class XkcdSyncActivity extends BaseActivity {
	
	private String CLASSTAG = XkcdSyncActivity.class.getSimpleName();
	
	@Override
	protected void onStart() {
		super.onStart();
		
		/* Get the XKCD archive page */
		new GetXkcdASync().execute(XKCDConstants.Urls.ARCHIVE);
	}
	
	private class GetXkcdASync extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... urls) {
			Document doc = null;
			
			// Get HTML doc
			try {
				doc = Jsoup.connect(urls[0]).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Check if we got it
			if (doc != null) {
				Elements comicList = doc.select("#middleContainer a[href]");
				for (Element comic : comicList) {
					//Comic Link at comic.attr("abs:href")
					//Comic Title at comic.text()
					getImageURLandCaption(comic.attr("abs:href")); // specific comic link required as input
				}
			}
			
			return true;
		}

		private String[] getImageURLandCaption(String comicURL) {
			Document comicPage = null;
			
			// Get comic HTML doc
			try {
				comicPage = Jsoup.connect(comicURL).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Get image URL
			if (comicPage != null) {
				Elements images = comicPage.select("#comic img[src]");
				String[] imageArray = {"",""};
				
				for (Element image : images) {
					imageArray[0] = image.attr("src");
					imageArray[1] = image.attr("title");
				}
				Log.i(CLASSTAG, imageArray[0]);
				return imageArray;
				
			} else {
				String[] imageArray = {"", ""};
				return imageArray;
			}
		}
		
	}
	
}
