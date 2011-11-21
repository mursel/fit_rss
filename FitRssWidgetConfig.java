package com.bsp.app.fitrss;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class FitRssWidgetConfig extends Activity {
		
	int appWID = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// u slucaju da se prijevremeno pritisne na BACK dugme
		// izadji iz metode
		setResult(RESULT_CANCELED);
		setContentView(R.layout.fitrssconfig);
		
		if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			Toast.makeText(this, "Greska: Eksterna memorija (sdcard) nije dostupna!", Toast.LENGTH_SHORT);
			return;
		}
										
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			appWID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		
		if (appWID == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch (item.getItemId()) {
		case R.id.osvjeziItem:
			osvjeziFeeds();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void osvjeziFeeds() {
		Context ctx = FitRssWidgetConfig.this;
		
		@SuppressWarnings("unused")
		FitNewsAdapter fna = new FitNewsAdapter(ctx);
		
		AppWidgetManager appwm = AppWidgetManager.getInstance(ctx);
		FitRssProvider.updateWidgets(ctx, appwm, appWID, 1);
		
		Intent rez = new Intent();
		rez.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWID);
		setResult(RESULT_OK, rez);
		finish();					
		
	}

	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.ctxmenu, menu);
		return true;
	};
}
