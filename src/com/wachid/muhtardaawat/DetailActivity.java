package com.wachid.muhtardaawat;


import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
 
 
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
 

public class DetailActivity  extends ActionBarActivity {

	 //KEY_BAB, KEY_DOA, KEY_HADIS, KEY_RIWAYAT, KEY_HALAMAN,  KEY_ARTI, KEY_KETERANGAN 

	//private static String key_nomor; 
	private static String key_bab; 
	private static String key_doa; 
	private static String key_hadist;
	private static String key_riwayat;
	private static String key_halaman;
	private static String key_arti;
	private static String key_keterangan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doa_detail);
		 //KEY_BAB, KEY_DOA, KEY_HADIS, KEY_RIWAYAT, KEY_HALAMAN,  KEY_ARTI, KEY_KETERANGAN 

		Bundle extras = getIntent().getExtras();
	    key_bab = extras.getString("key_1");
	    key_doa = extras.getString("key_2");
	    key_hadist = extras.getString("key_3");
	    key_riwayat = extras.getString("key_4");
	    key_halaman = extras.getString("key_5");
	    key_arti = extras.getString("key_6");
	    key_keterangan = extras.getString("key_7");
	    
	    TextView tv_1 = (TextView) findViewById(R.id.text_bab);
	    TextView tv_2 = (TextView) findViewById(R.id.text_doa);
	    TextView tv_3 = (TextView) findViewById(R.id.text_hadist);
	    TextView tv_4 = (TextView) findViewById(R.id.text_riwayat);
	    TextView tv_5 = (TextView) findViewById(R.id.text_halaman);
	    TextView tv_6 = (TextView) findViewById(R.id.text_arti);
	    TextView tv_7 = (TextView) findViewById(R.id.text_keterangan);
	    
	    tv_1.setText(key_bab);
	    tv_2.setText(key_doa);
	    tv_3.setText(key_hadist);
	    tv_4.setText(key_riwayat);
	    tv_5.setText(key_halaman);
	    tv_6.setText(key_arti);
	    tv_7.setText(key_keterangan);
	    
	    // Font path
        String fontPath = "fonts/UthmanicHafs1 Ver09.otf";
 
       
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
 
        // Applying font
        tv_3.setTypeface(tf);
       
		
	}
	
	 
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {

			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.detail, menu);
		
			 // Set up ShareActionProvider's default share intent
		    MenuItem shareItem = menu.findItem(R.id.action_share);
		    ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
		    mShareActionProvider.setShareIntent(ActionShare());
		 
			
		    return super.onCreateOptionsMenu(menu);
		}

	 private Intent ActionShare() {
		    Intent intent = new Intent(Intent.ACTION_SEND);
		  //  intent.setType("image/*");
		    //KEY_BAB, KEY_DOA, KEY_HADIS, KEY_RIWAYAT, KEY_HALAMAN,  KEY_ARTI, KEY_KETERANGAN 

		    
		    intent.putExtra(Intent.EXTRA_TEXT, key_doa + "\n" + "dibaca " + key_hadist + " Setiap "+ key_riwayat +"\n" +  " Keutamaannya : " + key_arti + " #Muhtar Daawat for Android");
		    intent.setType("text/plain");
		    return intent;
		}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	        finish();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}
