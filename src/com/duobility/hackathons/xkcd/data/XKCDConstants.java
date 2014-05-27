package com.duobility.hackathons.xkcd.data;

public abstract class XKCDConstants {
	
	public class BundleKeys {
		public static final String SINGLE = "singleKey";
		public static final String LISTSTATE = "comicliststate";
		public static final String FROM_SIDE_ACTIVITY = "fromsideactivity";
	}
	
	public class SharedPrefKeys {
		public class LastSync {
			private static final int DELTA_HOURS = 8;
			public static final int DEFAULTVALUE = 3;
			public static final int DELTA = 3600 * DELTA_HOURS;
			public static final String LASTSYNC = "com.duobility.hackatons.xkcd.LASTSYNC";
		}
	}
	
	public class DatabaseConstants {
		public class Limit {
			public static final String SINGLE = "1";
			public static final String LIST = "50";
		}
	}
	
	public class SyncConstants {
		public static final int MAX_NUMBER_OF_OMITTED_COMICS = 4;
	}
	
	public class TransitionAnimation {
		public static final int FROM_TOP = 1;
		public static final int FROM_RIGHT = 2;
		public static final int FROM_BOTTOM = 3;
		public static final int FROM_LEFT = 4;
	}
	
	public class ActionBarNavigationArray {
		public static final int RECENT = 0;
		public static final int RANDOM = 1;
	}
	
	public class Urls {
		public static final String BACKEND = "http://xkcd-backend.herokuapp.com/";
		public static final String RECEIVE_JSON = BACKEND + "hello";
		
	}
	
	public class Json {
		public static final String ARRAY_TAG = "xkcd";
		
		public static final String ID = "id";
		public static final String TITLE = "title";
		public static final String URL = "url";
		public static final String CAPTION = "caption";
	}
	
	public static final String[] testImageURLs = {
									"http://imgs.xkcd.com/comics/old_files.png", //     [0] Long Image
									"http://imgs.xkcd.com/comics/perl_problems.png", // [1] Single Row Wide Image
									"http://imgs.xkcd.com/comics/1999.png", //          [2] Very Wide Image
									"http://imgs.xkcd.com/comics/tape_measure.png" //   [3] High Content Image
									};
	
	/* Comic Object */
	public static class Comic {
		public final int id;
		public final String title;
		public final String url;
		public final String caption;
		
		public Comic(int id, String title, String url, String caption) {
			this.id = id;
			this.title = title;
			this.url = url;
			this.caption = caption;
		}
	}

}
