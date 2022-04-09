package com.android.kevinsalarmclock;

import java.io.IOException;

import android.media.MediaPlayer;
import android.text.format.Time;
import android.util.Log;



public class Alarm {
	public class MyTime{
		private int hour;
		private int minute;
		//private boolean AM;//true if AM

		public MyTime(){
			this(0,0);//12:00 AM
		}

		public MyTime(int h, int m){
			int off = setMinute(m);
			setHour(h+off);

			//setAM(b);
		}

		public MyTime(MyTime t){
			this.setHour(t.getHour());
			this.setMinute(t.getMinute());
			//this.setAM(t.getAM());
		}

		public int getHour(){
			return hour;
		}

		public int setHour(int h){
			int ret = 0;
			while(h<0)
				h+=24;
			ret--;
			while(h>23)
				h-=24;
			ret++;
			hour = h;
			return ret;
		}

		public int getMinute(){
			return minute;
		}

		public int setMinute(int m){
			int ret = 0;
			while(m<0)
				m+=60;
			ret--;
			while(m>59)
				m-=60;
			ret++;
			minute = m;
			return ret;
		}


		public void advance(int m){
			//will advance the clock by m minutes
			//MyTime temp = new MyTime(this);
			int off1 = setMinute(this.getMinute()+m);
			setHour(this.getHour()+off1);
		}

		public String toString(){
			String s = "";
			s += pad(getHour()) + ":" + pad(getMinute());
			return s;


		}
		private String pad(int c) {
			if (c >= 10)
				return String.valueOf(c);
			else
				return "0" + String.valueOf(c);
		}

	}



	private MyTime t;
	private MyTime snoozeMyTime;
	private Boolean active;
	private String audio;//assume that this is a file;
	private MediaPlayer mp;
	private static Information parent;
	private static int snooze = 1;//0;
	boolean snoozing = false;

	public Alarm(){

		this(12,0,false,"");//"/sdcard/my music/Amon Amarth/Twilight of the Thunder God/02 Free Will Sacrifice.mp3");

	}

	public Alarm(int h, int m, boolean a, String sound){
		mp = new MediaPlayer();

		//mp = MediaPlayer.create(parent, R.raw.twilight);
		t = new MyTime(h,m);
		snoozeMyTime= new MyTime(t);
		active = a;
		setAudio(sound);
	}
	//trigger();


	public Alarm(MyTime t, boolean a, String sound){
		this(t.getHour(),t.getMinute(),a,sound);
		//trigger();
	}

	public Alarm(Alarm a){
		this(a.getTime(true), a.getActive(), a.getAudio());

	}

	public void setActive(boolean a){
		active = a;
		if(parent!= null){
			parent.updateSharedPreferences();
		}
	}

	public boolean getActive(){
		return active;
	}

	public String getAudio(){
		return audio;
	}

	public void setAudio(String s){
		audio = s;
		//prepareMedia(audio);
		if(parent!= null){
			parent.setCurrentAlarm(parent.currentAlarm);
		}
	}

	public void prepareMedia(String s){
		if(parent==null){
			return;

		}
		if(mp == null){
			mp = new MediaPlayer();	
		}
		if(s==""){
			mp.reset();
			Log.d("Kevin", "Using default audio");
			mp = MediaPlayer.create(parent.getMyParent(), R.raw.twilight);
			return;
		}else{
			//audio = 
			//if(true) return;
			try{
				mp.reset();
				mp.setDataSource(s);
				mp.prepare();
			}catch (IOException e){
				Log.d("Kevin", "Song at "+ s + " failed to initialize");
				mp.reset();
				prepareMedia("");
			}
		}


		//parent.updateSharedPreferences();
	}

	public MyTime getTime(boolean ignoreSnooze){
		if(snoozing && !ignoreSnooze) 
			return snoozeMyTime;

		return t;
	}

	public void setTime(MyTime t){
		t = new MyTime(t);
		//parent.updateSharedPreferences();
	}

	public void setTime(int h, int m){
		t = new MyTime(h,m);
		//parent.updateSharedPreferences();
	}

	public static void setParent(Information k){
		parent = k;

	}

	public void trigger(){
		//if(mp.)
		prepareMedia(audio);
		mp.setLooping(true);
		mp.start();
		//mp.setVolume(10f, 10f);
		//TurnOff.setCurrentAlarm(this);

	}

	public void turnOff(){
		snoozing = false;
		mp.stop();
		//mp.reset();
		try{
			mp.prepare();
		}catch(Exception e){}
		setActive(false);
		if(this == parent.currentAlarm){
			parent.getMyParent().setStuff(parent.currentAlarm);
		}
		//active = false;

	}

	public void snooze(){
		//snoozing = false;
		setSnoozeTime();
		mp.stop();
		//mp.reset();
		try{
			mp.prepare();
		}catch(Exception e){}
		snoozing = true;
		if(parent!=null){
			parent.setCurrentAlarm(parent.currentAlarm);
		}
	}

	public void setSnoozeTime(){
		Time t = new Time();
		t.setToNow();
		t.normalize(false);
		snoozeMyTime.setHour(t.hour);
		snoozeMyTime.setMinute(t.minute);
		snoozeMyTime.advance(snooze);


	}

	public String toString(){
		return "Time = " + t.toString() + ", Active = " + (getActive()?"true":"false")
		+ ", Audio = " + getAudio();

	}

	void setSnooze(int n){
		if(n<1) n = 1;
		snoozeMyTime = new MyTime(this.getTime(false));
		snoozeMyTime.advance(-snooze);
		snooze = n;
		snoozeMyTime.advance(snooze);
		if(snoozing && parent!=null){
			parent.cancelAlarm();
			parent.scheduleAlarm();
		}

	}

	int getSnooze(){
		return snooze;

	}

	void cancelSnooze(){
		snoozing = false;
	}



}
