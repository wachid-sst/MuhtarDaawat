package com.wachid.muhtardaawat;


import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
 
 
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;

 
 

public class AboutActivity  extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		
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
		   
		    intent.putExtra(Intent.EXTRA_TEXT, " saroh");
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
