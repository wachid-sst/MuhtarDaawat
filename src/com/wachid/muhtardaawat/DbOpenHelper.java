package com.wachid.muhtardaawat;

 

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper {

 
	public static String DB_PATH;
	public static int DB_VERS;
	public static String DB_NAME;
	public static String DB_TABLE;
	public static String DB_EXT;
	public static String DB_EXT_PATH;
	public SQLiteDatabase mDb;
	public final Context context;
	
	private static String TAG = "DbOpenHelper";
	
	public SQLiteDatabase getDb() {
		return mDb;
	}
	
	

	public DbOpenHelper(Context context, String databaseName) {
		super(context, databaseName, null, 2);
		this.context = context;
		
		String packageName = context.getPackageName();
		DB_PATH = String.format("//data//data//%s//databases//", packageName);
		DB_NAME = databaseName;
	 
		openDataBase();
	}

	
	public void createDataBase() {
		boolean dbExist = checkDataBase();
		if (!dbExist) {
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				Log.e(this.getClass().toString(), "Copying error");
				throw new Error("Error copying database!");
			}
		} else {
			Log.i(this.getClass().toString(), "Database already exists");
		}
	}
	
	private boolean checkDataBase() {
		SQLiteDatabase checkDb = null;
		try {
			String path = DB_PATH + DB_NAME;
			checkDb = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLException e) {
			Log.e(this.getClass().toString(), "Error while checking db");
		}
		 
		if (checkDb != null) {
			checkDb.close();
		}
		return checkDb != null;
	}
 
	private void copyDataBase() throws IOException {
		
		// Font path
        DB_EXT_PATH = "db/";
        DB_EXT = DB_EXT_PATH + DB_NAME;
		 
		InputStream externalDbStream = context.getAssets().open(DB_EXT);

		 
		String outFileName = DB_PATH + DB_NAME;

		 
		OutputStream localDbStream = new FileOutputStream(outFileName);

	 
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = externalDbStream.read(buffer)) > 0) {
			localDbStream.write(buffer, 0, bytesRead);
		}
	 
		localDbStream.close();
		externalDbStream.close();

	}

	public SQLiteDatabase openDataBase() throws SQLException {
		String path = DB_PATH + DB_NAME;
		if (mDb == null) {
			createDataBase();
			mDb = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READWRITE);
		}
		return mDb;
	}
	@Override
	public synchronized void close() {
		if (mDb != null) {
			mDb.close();
		}
		super.close();
	}
	@Override
	public void onCreate(SQLiteDatabase db) {}

	 @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	   Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
	     + newVersion + ", which will destroy all old data");
	   db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
	   createDataBase();
	  }
	 
}
