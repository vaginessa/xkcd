package com.duobility.hackathons.xkcd.views;

import java.util.ArrayList;

import com.androidquery.AQuery;

import com.duobility.hackathons.xkcd.R;
import com.duobility.hackathons.xkcd.activities.XkcdSyncActivity;
import com.duobility.hackathons.xkcd.data.Database;
import com.duobility.hackathons.xkcd.data.Fonts.Roboto;
import com.duobility.hackathons.xkcd.data.XKCDConstants.ActionBarNavigationArray;
import com.duobility.hackathons.xkcd.data.XKCDConstants.BundleKeys;
import com.duobility.hackathons.xkcd.data.XKCDConstants.Comic;
import com.duobility.hackathons.xkcd.data.XKCDConstants.TransitionAnimation;

import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class MainView extends XkcdSyncActivity {
	
	public static String CLASSTAG = MainView.class.getSimpleName();
	private boolean randomListBoolean = false;
	private boolean fromSideView = false;
	
	private ListView comicListView;
	private SpinnerAdapter navigationSpinnerAdapter;
	private OnNavigationListener onNavigationCallBack;
	
	private Bundle extrasMainView;
	private Parcelable listState;
	private ComicAdapter adapter ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
		
		/* Turn on ActionBar navigation */
		navigationSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.navList, android.R.layout.simple_dropdown_item_1line);
		onNavigationCallBack = new OnNavigationListener() {
			
			String[] navigationItemArray = getResources().getStringArray(R.array.navList);
			
			@Override
			public boolean onNavigationItemSelected(int position, long id) {
				switch (position) {
				case ActionBarNavigationArray.RECENT:
					randomListBoolean = false;
					adapter.getAndRefreshList();
					break;
				case ActionBarNavigationArray.RANDOM:
					randomListBoolean = true;
					adapter.getAndRefreshList();
					break;

				default:
					break;
				}
				Log.d(CLASSTAG, "Nav selected: " + navigationItemArray[position]);
				return true;
			}
		};
		
		/* Applying ActionBar navigation*/
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setListNavigationCallbacks(navigationSpinnerAdapter, onNavigationCallBack);
		
		/* Get Extras if any exist */
		extrasMainView = getIntent().getExtras();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		initViews();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		listStateManager();
	}

	private void listStateManager() {
		if(extrasMainView != null) {
			listState = extrasMainView.getParcelable(BundleKeys.LISTSTATE);
			fromSideView = extrasMainView.getBoolean(BundleKeys.FROM_SIDE_ACTIVITY);
			if ((listState != null) && fromSideView) {
				comicListView.onRestoreInstanceState(listState);
			}
		}
	}

	private void initViews() {
		/* Find all Views */
		comicListView = (ListView) findViewById(R.id.comicListView);
		
		/* Bind adapter to ListView */
		adapter = new ComicAdapter();
		comicListView.setAdapter(adapter);
	}
	
	/* All list Methods below */
	private ArrayList<Comic> getList(boolean isListRandom) {
		Database db = new Database(getApplicationContext());
		db.open();
		ArrayList<Comic> list = new ArrayList<Comic>();
		if (isListRandom) {
			list = db.getRandomEntries(); /* GET RANDOM ENTRIES */
		} else {
			list = db.getEntries(); /* GET RECENT ENTRIES */
		}
		db.close();
		return list;
	}
	
	private OnClickListener comicClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			final int position = comicListView.getPositionForView(v);
			if (position != ListView.INVALID_POSITION) {
				// Save ListState
				listState = comicListView.onSaveInstanceState();
				
				// Build Intent and Bundle
				Intent singleViewIntent = new Intent(getApplicationContext(), SingleComicView.class);
				Bundle bundle = new Bundle();
				
				// Store information in bundle then intent
				bundle.putString(BundleKeys.SINGLE, adapter.list.get(position).url);
				bundle.putParcelable(BundleKeys.LISTSTATE, listState);
				singleViewIntent.putExtras(bundle);
				
				// Call intent
				gotoView(singleViewIntent, TransitionAnimation.FROM_RIGHT);
				finish();
			}
		}
	};
	
	private OnClickListener shareComicClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			final int position = comicListView.getPositionForView(v);
			if (position != ListView.INVALID_POSITION) {
				shareComicIntent(
						adapter.list.get(position).url, // Comic Image URL 
						adapter.list.get(position).title // Comic Image Title
						);
			}
		}
	};
	
	private static class ComicViewHolder {
		public ImageView comicImage;
		public TextView titleView;
		public TextView captionView;
		public Button shareButton;
	}
	
	private class ComicAdapter extends BaseAdapter {
		
		ArrayList<Comic> list = initList();
		
		public ArrayList<Comic> initList() {
			return getList(randomListBoolean);
		}
		
		public void getAndRefreshList() {
			list = initList();
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
				convertView = getLayoutInflater().inflate(R.layout.comic_li, parent, false); // COMIC LIST //
				holder.titleView = (TextView) convertView.findViewById(R.id.comicCardTitle);
				holder.comicImage = (ImageView) convertView.findViewById(R.id.comicCardImage);
				holder.captionView = (TextView) convertView.findViewById(R.id.comicCardCaption);
				holder.shareButton = (Button) convertView.findViewById(R.id.comicShareButton);
				
				/* Comic OnClickListener */
				holder.comicImage.setOnClickListener(comicClickListener);
				holder.shareButton.setOnClickListener(shareComicClickListener);
				
				convertView.setTag(holder);
			}
			
			/* Applied to Title */
			holder.titleView.setText(list.get(position).title);
			mFonts.typeFaceConstructor(holder.titleView, Roboto.BOLD, getAssets());
			
			/* Applied to Image */
			aq.id(holder.comicImage).image(
					list.get(position).url, // Image URL 
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
			
			/* Applied to Share Button */
			mFonts.typeFaceConstructor(holder.shareButton, Roboto.BOLD, getAssets());
			
			return convertView;
		}
		
	}
	
}
