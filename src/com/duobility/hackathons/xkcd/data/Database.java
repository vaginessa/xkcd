package com.duobility.hackathons.xkcd.data;

import java.util.ArrayList;

import com.duobility.hackathons.xkcd.data.XKCDConstants.Comic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database {
	
	public static String CLASSTAG = Database.class.getSimpleName();
	
	/* Constants For DB */
	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_URL = "url";
	public static final String KEY_CAPTION = "caption";
	
	private static final String DATABASE_NAME = "comicDB";
	private static final String DATABASE_TABLE = "comicTable";
	private static final int DATABASE_VERSION = 1;
	
	private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + DATABASE_TABLE;
	private static final String SQL_CLEAR_TABLE = "DELETE FROM " + DATABASE_TABLE;
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
			db.execSQL(SQL_DROP_TABLE);
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
	
	/* String Controls */
	private String replaceQuote(String s) {
		return s.replace("&#39;", "'");
	}
 	
	/* Custom DB interaction methods */	
	public long getNumberOfComics() {
		long number = DatabaseUtils.queryNumEntries(ourDatabase, DATABASE_TABLE);
		return number;
	}
	
	public boolean hasEntriesCheck() {
		long numberOfRows = getNumberOfComics();
		if (numberOfRows > 0) {
			return true; // Has entries
		} else {
			return false; // Is Empty
		}
	}
	
	public void clearDBifHasEntries() {
		if (hasEntriesCheck()) {
			ourDatabase.execSQL(SQL_CLEAR_TABLE);
			ourDatabase.execSQL(SQL_CREATE_TABLE);
		}
		Log.d(CLASSTAG, "DB cleared");
	}
	
	public void putEntry(int id, String title, String url, String caption) {
		ContentValues values = new ContentValues();
		values.put(KEY_ID, id);
		values.put(KEY_TITLE, title);
		values.put(KEY_URL, url);
		values.put(KEY_CAPTION, caption);
		ourDatabase.insert(DATABASE_TABLE, null, values);
		Log.i(CLASSTAG, "[PUT] " + title);
	}
	
	public Comic getComicFromURL(String url) {
		String selection = KEY_URL + "=" + "'" + url + "'";
		Cursor dbc = ourDatabase.query(DATABASE_TABLE, columns, selection, null, null, null, null);
		
		Comic comic = getComicFromDBCursor(dbc);
		
		return comic;
	}
	
	public Comic getRandomEntry() {
		String orderBy = "RAND()";
		String limit = "1";
		Cursor dbc = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, orderBy, limit);
		
		Comic comic = getComicFromDBCursor(dbc);
		
		Log.d(CLASSTAG, "getRandomEntry");
		return comic;
	}
	
	public ArrayList<Comic> getRandomEntries() {
		String orderBy = "RAND()";
		String limit = "30";
		Cursor dbc = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, orderBy, limit);
		
		ArrayList<Comic> comiclist = getComicListFromDBCursor(dbc);
		
		/* Return Randomized List */
		Log.d(CLASSTAG, "getRandomEntries");
		return comiclist;
	}
	
	public ArrayList<Comic> getEntries() {
		String orderBy = KEY_ID + " ASC";
		String limit = "30";
		Cursor dbc = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, orderBy, limit);
		
		ArrayList<Comic> comiclist = getComicListFromDBCursor(dbc);
		
		/* Return constructed list */
		Log.d(CLASSTAG, "getEntries");
		return comiclist;
	}
	
	/* Database Helper Methods */
	private Comic getComicFromDBCursor(Cursor dbc) {
		Comic comic = null;
		int iId = dbc.getColumnIndex(KEY_ID);
		int iTitle = dbc.getColumnIndex(KEY_TITLE);
		int iUrl = dbc.getColumnIndex(KEY_URL);
		int iCaption = dbc.getColumnIndex(KEY_CAPTION);
		
		for (dbc.moveToFirst(); !dbc.isAfterLast(); dbc.moveToNext()) {
			comic = new Comic(
					dbc.getInt(iId),
					replaceQuote( dbc.getString(iTitle) ),
					dbc.getString(iUrl),
					replaceQuote( dbc.getString(iCaption) )
					);
		}
		
		return comic;
	}
	
	private ArrayList<Comic> getComicListFromDBCursor(Cursor dbc) {
		ArrayList<Comic> comiclist = new ArrayList<Comic>();
		int iId = dbc.getColumnIndex(KEY_ID);
		int iTitle = dbc.getColumnIndex(KEY_TITLE);
		int iUrl = dbc.getColumnIndex(KEY_URL);
		int iCaption = dbc.getColumnIndex(KEY_CAPTION);
		
		for (dbc.moveToFirst(); !dbc.isAfterLast(); dbc.moveToNext()) {
			comiclist.add(new Comic(
					dbc.getInt(iId), 
					replaceQuote( dbc.getString(iTitle) ), 
					dbc.getString(iUrl), 
					replaceQuote( dbc.getString(iCaption) ) 
					));
		}
		
		return comiclist;
	}
}
