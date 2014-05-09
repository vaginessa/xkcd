package com.duobility.hackathons.xkcd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {
	
	/* Constants For DB */
	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_URL = "url";
	public static final String KEY_CAPTION = "caption";
	
	private static final String DATABASE_NAME = "comicDB";
	private static final String DATABASE_TABLE = "comicTable";
	private static final int DATABASE_VERSION = 1;
	
	private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + DATABASE_TABLE;
	private static final String SQL_CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE + " (" +
														KEY_ID + " INTEGER NOT NULL, " + 
														KEY_TITLE + " TEXT NOT NULL, " + 
														KEY_URL + " TEXT NOT NULL, " +
														KEY_CAPTION + " TEXT NOT NULL);";
	private static final String[] columns = new String[] {KEY_ID, KEY_TITLE, KEY_URL, KEY_CAPTION};
	
	private DBHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	/* Required class and methods to run the database
	 * DO NOT MODIFY */
	private static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			/* This creates the Database */
			db.execSQL(SQL_CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		} /* onUpgrade is required but not implemented or defined */
		
	}
	
	public Database(Context context) {
		ourContext = context;
	}
	
	/* Required methods to start and stop the DB */
	public Database open() throws SQLException {
		ourHelper = new DBHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		ourHelper.close();
	}
 	
	/* Custom DB interaction methods */
	public void putEntry(int id, String title, String url, String caption) {
		ContentValues values = new ContentValues();
		values.put(KEY_ID, id);
		values.put(KEY_TITLE, title);
		values.put(KEY_URL, url);
		values.put(KEY_CAPTION, caption);
		ourDatabase.insert(DATABASE_TABLE, null, values);
	}
}