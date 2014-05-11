package com.duobility.hackathons.xkcd.data;

public abstract class XKCDConstants {
	
	public class BundleKeys {
		public static final String SINGLE = "singleKey";
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
