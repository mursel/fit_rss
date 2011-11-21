package com.bsp.app.fitrss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class FitNewsAdapter 
{	
	private ArrayList<FeedItem> m_feeds = null;
	
	public FitNewsAdapter(Context ctx)
	{
		m_feeds = new ArrayList<FeedItem>();
		pullFeeds();
		GetFitNews();
	}
	
	public FeedItem getFeed(int i)
	{
		return m_feeds.get(i);
	}
	
	public int getFeedSize()
	{
		return m_feeds.size();
	}
	
	public void refresh() {
		pullFeeds();
	}
		
	private synchronized ArrayList<FeedItem> GetFitNews() {
		try {
        
			if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
			{
				return null;
			}
						
			File rootDir = Environment.getExternalStorageDirectory();
			File appDir = new File(rootDir.getAbsolutePath(), "/fitnews/data/");
			File appFile = new File(appDir, "podaci.dat");
						
			FileInputStream fis = new FileInputStream(appFile);
			BspSaxParser bsp = new BspSaxParser();
			Xml.parse(fis, Xml.Encoding.UTF_8, bsp);
			FeedChannel fc = bsp.GetFeedChanel();
			m_feeds.addAll(fc.getFeeds());

		} catch (IOException e) {
			Log.e("FitNewsAdapter:ERR: ", e.getMessage());
		} catch (SAXException e) {
			Log.e("FitNewsAdapter:ERR: ", e.getMessage());
		} 
		
		return m_feeds;
					
	}

	private synchronized void pullFeeds() {
		
		if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
		{
			return;
		}
		
    	URL url;
		try {
			
			m_feeds = new ArrayList<FeedItem>();
			
			url = new URL("http://www.fit.ba/ba/index.php?option=com_rss&feed=RSS2.0&no_html=1");
		
		InputStream stream = url.openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("windows-1250")),1024 * 8);
		String line;
		StringBuffer content = new StringBuffer();				
						
		while ((line=reader.readLine()) != null) {
			if (line.contains("ž") || line.contains("Ž") ||
				line.contains("ð") || line.contains("Ð") ||
				line.contains("è") || line.contains("È") ||
				line.contains("æ") || line.contains("Æ") ||
				line.contains("š") || line.contains("Š"))
			{						
				line.replace("ž", "z");
				line.replace("Ž", "Z");
				line.replace("è", "c");
				line.replace("È", "C");
				line.replace("æ", "c");
				line.replace("Æ", "C");
				line.replace("š", "s");
				line.replace("Š", "S");
				line.replace("ð", "dj");
				line.replace("Ð", "Dj");
			}
			content.append(line);
		}
		
		File rootDir = Environment.getExternalStorageDirectory();
		File appDir = new File(rootDir.getAbsolutePath(), "/fitnews/data/");
		appDir.mkdirs();
		
		File appFile = new File(appDir, "podaci.dat");
				
		FileOutputStream fos = new FileOutputStream(appFile);
		fos.write(content.toString().getBytes());
		fos.close();
		stream.close();
		
		FileInputStream fis = new FileInputStream(appFile);
		BspSaxParser bsp = new BspSaxParser();
		Xml.parse(fis, Xml.Encoding.UTF_8, bsp);
		FeedChannel fc = bsp.GetFeedChanel();
		m_feeds.addAll(fc.getFeeds());
	
		} catch (MalformedURLException e) {
			Log.e("FitRssWidgetConfig:ERR: ", e.getMessage());
		} catch (IOException e) {
			Log.e("FitRssWidgetConfig:ERR: ", e.getMessage());
		} catch (SAXException e) {
			Log.e("FitRssWidgetConfig:ERR: ", e.getMessage());
		}
				
	}
	
}
