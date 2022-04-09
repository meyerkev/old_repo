package com.android.kevinsalarmclock;

public class SimpleSong {
	String artist;
	String album;
	String title;
	String filePath;
	int track;
	
	public SimpleSong(String artiste, String al, String t, int track, String path){
		setArtist(artiste);
		setAlbum(al);
		setTitle(t);
		setTrack(track);
		setFilePath(path);
	}
	
	public String getArtist(){
		return artist;
	}
	public String getAlbum(){
		return album;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getFilePath(){
		return filePath;
	}
	
	public void setArtist(String s){
		artist = s;
	}
	
	public void setAlbum(String s){
		album = s;
	}
	
	public void setTitle(String s){
		title = s;
	}
	
	public void setFilePath(String s){
		filePath = s;
	}
	
	public void setTrack(int s){
		track = s;
	}
	
	public int getTrack(){
		return track;
	}
	
	public String toString(){
		
		return getArtist() + " " + getAlbum() + " " +  getTrack() + " " + getTitle();
	}
}
