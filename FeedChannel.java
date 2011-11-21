package com.bsp.app.fitrss;

import java.util.Vector;

public class FeedChannel {
	private Vector<FeedItem> feeds;
	
	public FeedChannel() {
		feeds = new Vector<FeedItem>();
	}

	public Vector<FeedItem> getFeeds() {
		return feeds;
	}
	
	public FeedItem getFeed(int position)
	{
		return feeds.elementAt(position);	
	}
	
	public void addFeed(FeedItem fi) {
		feeds.addElement(fi);
	}
	
	public String toString() {
		String newLine = System.getProperty("line.separator");
		StringBuffer bafer = new StringBuffer();
		
		bafer.append("--- FEEDS ---").append(newLine);
		for (int i = 0; i < feeds.size(); i++) {
			bafer.append(feeds.elementAt(i)).append(newLine);
		}
		bafer.append("--- KRAJ ---");
		
		return bafer.toString();
	}		
}
