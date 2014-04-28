package com.duobility.hackathons.xkcd.data;

public abstract class XKCDConstants {
	
	public class Urls {
		public static final String HOME = "http://xkcd.com/";
		public static final String RANDOM = "http://dynamic.xkcd.com/random/comic/";
		public static final String ARCHIVE = "http://xkcd.com/archive/";
		
		public class Images {
			
			/* Get image URLs */
			public String getImageURL() {
				String imageURL = "";
				return imageURL;
			}
		}
	}
	
	public static final String[] testImageURLs = {
									"http://imgs.xkcd.com/comics/old_files.png", //     [0] Long Image
									"http://imgs.xkcd.com/comics/perl_problems.png", // [1] Single Row Wide Image
									"http://imgs.xkcd.com/comics/1999.png", //          [2] Very Wide Image
									"http://imgs.xkcd.com/comics/tape_measure.png" //   [3] High Content Image
									};
}
