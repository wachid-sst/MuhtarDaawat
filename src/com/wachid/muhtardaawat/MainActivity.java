package com.wachid.muhtardaawat;



import android.content.Intent;
import android.database.Cursor;
 
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
 
import android.widget.Toast;
 
public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener,  SearchView.OnCloseListener {
 
 private static final ListView listView = null;
 
 
 private static String TAG = "database";
 

 private SimpleCursorAdapter dataAdapter;
 private static String detail_1;
 private static String detail_2; 
 private static String detail_3; 
 private static String detail_4;
 private static String detail_5;
 private static String detail_6;
 private static String detail_7;
 
 private static final String DB_NAME = "MuhtarDaawat";
 private static final String DB_TABLE = "muhtar";

 public static final String KEY_ID = "_id";
 public static final String KEY_BAB = "bab";
 public static final String KEY_DOA = "doa";
 public static final String KEY_HADIST = "hadist";
 public static final String KEY_RIWAYAT = "riwayat";
 public static final String KEY_HALAMAN = "halaman";
 public static final String KEY_ARTI = "arti";
 public static final String KEY_KETERANGAN = "keterangan";
 
 public static SQLiteDatabase mDb;

 
 
 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.main);
  
  
   
 DbOpenHelper dbOpenHelper = new DbOpenHelper(this, DB_NAME);
 mDb = dbOpenHelper.openDataBase();
 
//Clean all data
// dbHelper.deleteAllCountries();
//Add some data
// dbHelper.insertSomeCountries();
 
//Generate ListView from SQLite Database
  displayListView();
//Generate filter querying from SQLite Database
  
 
 }
 
 private void displayListView() {
 
 
  Cursor cursor = fetchAllCountries();
 
//KEY_NOMOR, KEY_DOA, KEY_JUMLAH, KEY_WAKTU, KEY_KEUTAMAAN,  KEY_RIWAYAT, KEY_ARTI 
  // The desired columns to be bound
  String[] columns = new String[] {
    KEY_BAB,
    KEY_DOA,
 
  };
  
 
 
  // the XML defined views which the data will be bound to
  int[] to = new int[] { 
	R.id.text_bab,
	R.id.text_doa,
   
  };
 
  
  
  // create the adapter using the cursor pointing to the desired data 
  //as well as the layout information
  dataAdapter = new SimpleCursorAdapter(
    this, R.layout.doa_list, 
    cursor, 
    columns, 
    to,
    0);
 
  ListView listView = (ListView) findViewById(R.id.listView1);
  // Assign adapter to ListView
  listView.setAdapter(dataAdapter);
//  listView.setTextFilterEnabled(true);
  
  dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
      public Cursor runQuery(CharSequence constraint) {
          return  fetchCountriesByName(constraint.toString());
      }
  });
 
 
  listView.setOnItemClickListener(new OnItemClickListener() {
   @Override
   public void onItemClick(AdapterView<?> listView, View view, 
     int position, long id) {
   // Get the cursor, positioned to the corresponding row in the result set
   Cursor cursor = (Cursor) listView.getItemAtPosition(position);
 //KEY_BAB, KEY_DOA, KEY_HADIS, KEY_RIWAYAT, KEY_HALAMAN,  KEY_ARTI, KEY_KETERANGAN 

   // Get the state's capital from this row in the database.
   detail_1 = cursor.getString(cursor.getColumnIndexOrThrow("bab"));
   detail_2 = cursor.getString(cursor.getColumnIndexOrThrow("doa"));
   detail_3 = cursor.getString(cursor.getColumnIndexOrThrow("hadist"));
   detail_4 = cursor.getString(cursor.getColumnIndexOrThrow("riwayat"));
   detail_5 = cursor.getString(cursor.getColumnIndexOrThrow("halaman"));
   detail_6 = cursor.getString(cursor.getColumnIndexOrThrow("arti"));
   detail_7 = cursor.getString(cursor.getColumnIndexOrThrow("keterangan"));
   Toast.makeText(getApplicationContext(),detail_1, Toast.LENGTH_SHORT).show();
 
   
  
   ActionDetail();
   }
  });
 
 
 
 }
 

 
 @Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
	
		 // Set up ShareActionProvider's default share intent
	    MenuItem shareItem = menu.findItem(R.id.action_share);
	    ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
	    mShareActionProvider.setShareIntent(ActionShare());
		
	    MenuItem searchItem = menu.findItem(R.id.action_search);
	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

		
	    return super.onCreateOptionsMenu(menu);
	}

 /**
	 * For calling Menu
	 * */
 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_search:
			// search action
			return true;
		case R.id.action_about:
			// location found
			ActionAbout();
			return true; 
		case R.id.action_help:
			// location found
			ActionHelp();
			return true; 
		case R.id.action_share:
			// location found
			ActionShare();
			return true; 
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Sharing aplication link
	 * */
	
 
	private Intent ActionShare() {
	    Intent intent = new Intent(Intent.ACTION_SEND);
	  //  intent.setType("image/*");
	    intent.putExtra(Intent.EXTRA_TEXT, "Enjoy Pray for easy with Home Work 13");
	    intent.setType("text/plain");
	    return intent;
	}
	
	/**
	 * Launching new activity About
	 * */
	private void ActionAbout() {
		Intent i = new Intent(this, AboutActivity.class);
		startActivity(i);
	}
	
	private void ActionHelp() {
		Intent i = new Intent(this, HelpActivity.class);
		startActivity(i);
	}
	
	//KEY_NOMOR, KEY_DOA, KEY_JUMLAH, KEY_WAKTU, KEY_KEUTAMAAN,  KEY_RIWAYAT, KEY_ARTI 
	
	private void ActionDetail() {
		Intent i = new Intent(this, DetailActivity.class);
		 i.putExtra("key_1", detail_1);
		 i.putExtra("key_2", detail_2);
		 i.putExtra("key_3", detail_3);
		 i.putExtra("key_4", detail_4);
		 i.putExtra("key_5", detail_5);
		 i.putExtra("key_6", detail_6);
		 i.putExtra("key_7", detail_7);
		startActivity(i);
	}
	

	@Override
	public boolean onQueryTextChange(String newText) {
		
	 
			dataAdapter.getFilter().filter(newText.toString());
        
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String newText) {
		 
		dataAdapter.getFilter().filter(newText.toString());
		// TODO Auto-generated method stub
		return false;
	}

	
	
	@Override
	public boolean onClose() {
		listView.setAdapter(dataAdapter);
		return false;
	}

	 //adapter
	  
	

	 
	 public Cursor fetchCountriesByName(String inputText) throws SQLException {
	  Log.w(TAG, inputText);
	  Cursor mCursor = null;
	  if (inputText == null  ||  inputText.length () == 0)  {
	   mCursor = mDb.query(DB_TABLE, new String[]  {KEY_ID,KEY_BAB,
			     KEY_DOA, KEY_HADIST, KEY_RIWAYAT, KEY_HALAMAN, KEY_ARTI, KEY_KETERANGAN}, 
	     null, null, null, null, null);
	 
	  }
	  else {
	   mCursor = mDb.query(true, DB_TABLE, new String[]  {KEY_ID,KEY_BAB,
			     KEY_DOA, KEY_HADIST, KEY_RIWAYAT, KEY_HALAMAN, KEY_ARTI, KEY_KETERANGAN}, 
	     KEY_DOA + " like '%" + inputText + "%'", null,
	     null, null, null, null);
	  }
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }
	
	 public Cursor fetchAllCountries() {
		 
		  Cursor mCursor = mDb.query(DB_TABLE, new String[] {KEY_ID,KEY_BAB,
				     KEY_DOA, KEY_HADIST, KEY_RIWAYAT, KEY_HALAMAN, KEY_ARTI, KEY_KETERANGAN}, 
		    null, null, null, null, null);
		 
		  if (mCursor != null) {
		   mCursor.moveToFirst();
		  }
		  return mCursor;
		 }
		  
 
 
}