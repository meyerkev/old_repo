package com.android.FirstApp;

import android.content.Context;
import android.media.MediaPlayer;

public class SongPlayer {
	protected MediaPlayer mp;	
	protected Context mContext;
	protected int currentSong = R.raw.pentiums;
	protected boolean isPlaying = false;
	
	public SongPlayer(Context c){
		mContext = c;
		start();
	}
	
	void start(){
		getSong(0);
		mp = MediaPlayer.create(mContext, currentSong);
		mp.setVolume(10, 10);
		//mp.start();
	}
	
	void restart(){
		mp.start();
		isPlaying = true;
		
	}
	
	void stop(){
		mp.pause();
		isPlaying = false;
	}
	
	void switchSong(int n){
		//return;
		//*
		int temp = currentSong;
		getSong(n);
		if(temp!=currentSong){
			mp.stop();
			mp.release();
			mp = MediaPlayer.create(mContext,currentSong);
			if(isPlaying){
				mp.start();
			}
		}
		//*/
		
	}
	
	private void getSong(int n){
		switch (n){
		case 0:
			currentSong = R.raw.pentiums;
			break;
		case 2: 
			currentSong = R.raw.twilight;
			break;
		default:
			currentSong = R.raw.pentiums;
			break;
		}
		//currentSong = R.raw.pentiums;
	}
	
	boolean isPlaying(){
		return isPlaying;
		
	}
}
