package com.duobility.hackathons.xkcd.data;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

public class Fonts {
	
	private static final String FRONT = "fonts/";
	private static final String BACK = ".ttf";
	
	public static final class Roboto {
		private static String RobotoGroup = "Roboto-";
		
		public static final String THIN = FRONT + RobotoGroup + "Thin" + BACK;
		public static final String LIGHT = FRONT + RobotoGroup + "Light" + BACK;
		public static final String REGULAR = FRONT + RobotoGroup + "Regular" + BACK;
		public static final String CONDENSED = FRONT + RobotoGroup + "Condensed" + BACK;
		public static final String BOLD_CONDENSED = FRONT + RobotoGroup + "BoldCondensed" + BACK;
		public static final String BOLD = FRONT + RobotoGroup + "Bold" + BACK;
		public static final String BLACK = FRONT + RobotoGroup + "Black" + BACK;
	}
	
	/* Applies Custom TypeFaces */
	public void typeFaceConstructor(TextView textView, String fontPath, AssetManager assets) {
		Typeface customTypeface = Typeface.createFromAsset(assets, fontPath);
		textView.setTypeface(customTypeface);
	}
	
}
