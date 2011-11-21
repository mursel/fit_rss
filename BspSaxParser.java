package com.bsp.app.fitrss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class BspSaxParser extends DefaultHandler {
	
	private boolean isItem = false;
	private boolean isTitle = false;
	private boolean isDesc = false;
	private boolean isAutor = false;
	private boolean isLink = false;
	
	private static FeedChannel fChannel = new FeedChannel();
	private FeedItem fItem;
	
	public FeedChannel GetFeedChanel()
	{
		return fChannel;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		if (localName.equals("item")) {
			fItem = new FeedItem();
			isItem = true;
		}
		else if (localName.equals("title") && fItem != null) 
			isTitle = true;
		else if (localName.equals("link") && fItem != null)
			isLink = true;
		else if (localName.equals("description") && fItem != null)
			isDesc = true;
		else if(localName.equals("author") && fItem != null)
			isAutor = true;
		
	}
	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if (localName.equals("title")) 
			isTitle = false;
		else if (localName.equals("link"))
			isLink = false;
		else if (localName.equals("description"))
			isDesc = false;
		else if(localName.equals("author"))
			isAutor = false;
		else if(localName.equals("item"))
			isItem = false;
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		String chars = new String(ch, start, length);
				
		if(fItem != null)
		{
			if (isTitle) {
				fItem.setNaslov(chars);
			}
			if (isAutor) {
				fItem.setAutor(chars);
			}
			if (isDesc) {
				fItem.setSadrzaj(chars);
			}
			if (isLink) {
				fItem.setLink(chars);
			}
			if (!isAutor && !isDesc && !isLink && !isTitle && !isItem) {
				fChannel.addFeed(fItem);
			}
		}
		
	}
	
}
