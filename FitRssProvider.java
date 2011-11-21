package com.bsp.app.fitrss;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class FitRssProvider extends AppWidgetProvider {
	
	private static FitNewsAdapter fna = null;
	public static int feedIndex = 0;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		
		AppWidgetManager awm = AppWidgetManager.getInstance(context);
		ComponentName cname = new ComponentName(context, FitRssProvider.class);
		int[] ids = awm.getAppWidgetIds(cname);
		
		if (intent.getAction().equals("OSVJEZI")) {
			fna.refresh();	
			feedIndex = 0;		
		}
		
		if (intent.getAction().equals("PREV")) {
			if (feedIndex == 0) {
				return;
			}
			feedIndex = feedIndex - 1;
		}
		
		if (intent.getAction().equals("NEXT")) {
			if (feedIndex == fna.getFeedSize()) {
				return;
			}
			feedIndex = feedIndex + 1;
		}
		
		for (int i = 0; i < ids.length; i++) {
			int appwID = ids[i];
			updateWidgets(context, awm, appwID, 0);			
		}			
		
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
				
		for (int i = 0; i < appWidgetIds.length; i++) {
			int appwID = appWidgetIds[i];
			updateWidgets(context, appWidgetManager, appwID, 0);			
		}

	}

	public static void updateWidgets(Context context,
			AppWidgetManager appWidgetManager, int i, int feedIndx) {

		fna = new FitNewsAdapter(context);
		
		Intent intent1 = new Intent(context, FitRssProvider.class);
		intent1.setAction("OSVJEZI");
		
		Intent intent2 = new Intent(context, FitRssProvider.class);
		intent2.setAction("PREV");
		
		Intent intent3 = new Intent(context, FitRssProvider.class);
		intent3.setAction("NEXT");
		
		PendingIntent pi1 = PendingIntent.getBroadcast(context, 0, intent1, 0);
		PendingIntent pi2 = PendingIntent.getBroadcast(context, 0, intent2, 0);
		PendingIntent pi3 = PendingIntent.getBroadcast(context, 0, intent3, 0);
		
		RemoteViews m_views = new RemoteViews(context.getPackageName(), R.layout.main);
		
		m_views.setOnClickPendingIntent(R.id.imageButton2, pi1);
		m_views.setOnClickPendingIntent(R.id.imageButton1, pi2);
		m_views.setOnClickPendingIntent(R.id.imageButton3, pi3);		
				
		m_views.setTextViewText(R.id.textView1, fna.getFeed(feedIndex).getNaslov());
		m_views.setTextViewText(R.id.textView2, fna.getFeed(feedIndex).getSadrzaj());			
		
		appWidgetManager.updateAppWidget(i, m_views);		
	}
}
