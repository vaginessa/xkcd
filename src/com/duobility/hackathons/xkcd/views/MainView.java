package com.duobility.hackathons.xkcd.views;

import java.util.ArrayList;

import com.androidquery.AQuery;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.activities.XkcdSyncActivity;
import com.duobility.hackathons.xkcd.data.Database;
import com.duobility.hackathons.xkcd.data.Fonts.Roboto;
import com.duobility.hackathons.xkcd.data.XKCDConstants.Comic;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainView extends XkcdSyncActivity {
	
	public static String CLASSTAG = MainView.class.getSimpleName();
	private ListView comicListView;
	private ComicAdapter adapter ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		initViews();
	}

	private void initViews() {
		/* Find all Views */
		comicListView = (ListView) findViewById(R.id.comicListView);
		
		/* Bind adapter to ListView */
		adapter = new ComicAdapter();
		comicListView.setAdapter(adapter);
	}
	
	/* All list Methods below */
	private ArrayList<Comic> getList() {
		Database db = new Database(getApplicationContext());
		db.open();
		ArrayList<Comic> list = db.getEntries();
		db.close();
		return list;
	}
	
	private static class ComicViewHolder {
		public ImageView comicImage;
		public TextView titleView;
		public TextView captionView;
	}
	
	private class ComicAdapter extends BaseAdapter {
		
		ArrayList<Comic> list = initList();
		
		public ArrayList<Comic> initList() {
			return getList();
		}
		
		public void refreshList() {
			notifyDataSetChanged();
		}
		
		/* Override methods required by the BaseAdapter */		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Comic getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ComicViewHolder holder = new ComicViewHolder();
			convertView = null;
			
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.comic_card, parent, false);
				holder.titleView = (TextView) convertView.findViewById(R.id.comicCardTitle);
				holder.comicImage = (ImageView) convertView.findViewById(R.id.comicCardImage);
				holder.captionView = (TextView) convertView.findViewById(R.id.comicCardCaption);
				convertView.setTag(holder);
			}
			
			/* Applied to Title */
			holder.titleView.setText(list.get(position).title);
			mFonts.typeFaceConstructor(holder.titleView, Roboto.BOLD, getAssets());
			
			/* Applied to Image */
			//aq.id(holder.comicImage).image(list.get(position).url, options);
			aq.id(holder.comicImage).image(
					list.get(position).url, // Image Url 
					true, // memCache
					true, // fileCache
					0, // target width
					0, // fall back id
					null, // preset
					AQuery.FADE_IN, // animation ID
					AQuery.RATIO_PRESERVE // aspect ratio
					);
			
			/* Applied to Caption */
			holder.captionView.setText(list.get(position).caption);
			mFonts.typeFaceConstructor(holder.captionView, Roboto.LIGHT, getAssets());
			
			return convertView;
		}
		
	}
	
}
