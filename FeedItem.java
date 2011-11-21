package com.bsp.app.fitrss;

public class FeedItem {
	
	private String naslov = "";
	private String autor = "";
	private String sadrzaj = "";
	private String link = "";
	
	public void setNaslov(String _naslov)	{
		this.naslov = _naslov;
	}
	
	public String getNaslov()	{
		return this.naslov;
	}
	
	public void setAutor(String _autor) {
		this.autor = _autor;
	}
	
	public String getAutor()	{
		return this.autor;
	}
	
	public void setSadrzaj(String _sadrzaj) {
		this.sadrzaj += _sadrzaj;
	}
	
	public String getSadrzaj()	{
		return this.sadrzaj;
	}
	
	public void setLink(String _link) {
		this.link += _link;
	}
	
	public String getLink()	{
		return "<a href='" + this.link.trim() + "'>Vise...</a>";
	}
	
	public String toString() {
		return String.format("Naslov: %s, Autor: %s, Sadrzaj: %s, Link: %s", 
				this.naslov, this.autor, this.sadrzaj, this.link); 
	}
	
}
