package com.android.kevinsalarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.text.format.Time;
import android.util.Log;

public class Information{


	private static SharedPreferences myPrefs; //needs to store what??
	private static String MY_HOUR = "hour";
	private static String MY_MINUTE = "minute";
	private static String MY_ACTIVE = "active";
	private static String MY_AUDIO = "audio";
	private static String MY_SNOOZE = "snooze";
	Alarm currentAlarm = new Alarm();
	private static PendingIntent pending;
	private kevinsAlarmClock parent;
	//private PreferenceActivity p = new PreferenceActivity();

	private static AlarmManager startAlarm;

	public Information(kevinsAlarmClock k){
		if(k!=null)parent = k;
		myPrefs = getMyParent().getSharedPreferences("myPrefs", 0x00000000);
		startAlarm = (AlarmManager) parent.getSystemService(Context.ALARM_SERVICE);
	}

	public void setParent(kevinsAlarmClock k){
		parent = k;
	}
	
	public long convertTime(Alarm.MyTime mine){
		//we need to get the current time
		Time t = new Time();
		t.setToNow();
		t.normalize(false);
		//then get the next time after at which the given time occurs
		Time nextTime = new Time(t);
		nextTime.hour = mine.getHour();
		nextTime.minute =  mine.getMinute();
		nextTime.second = 0;
		nextTime.normalize(false);
		while (!nextTime.after(t)){
			nextTime.monthDay += 1;
			nextTime.normalize(false);

		}
		return nextTime.toMillis(false);

	}

	void setCurrentAlarm(Alarm a){
		currentAlarm = a;
		if(parent!=null){
			parent.setStuff(currentAlarm);

		}
	}

	void getMySharedPreferences(){
		Time t = new Time();
		t.setToNow();
		t.normalize(false);

		int h = myPrefs.getInt(MY_HOUR, t.hour);
		int m = myPrefs.getInt(MY_MINUTE, t.minute + 1);
		boolean a = (myPrefs.getInt(MY_ACTIVE, 1))==1;
		String s = "";
		try{
			s = (myPrefs.getString(MY_AUDIO, "blank"));
		}catch(ClassCastException e){
			Log.d("Kevin", "Getting audio string failed.  Dunno Why " + MY_AUDIO);

		}
		if (s == "blank"){ s = "";};
		int snooze = myPrefs.getInt(MY_SNOOZE, 1);
		Alarm mine = new Alarm(h,m,a,s);
		mine.setSnooze(snooze);
		setCurrentAlarm(mine);
	}

	void updateSharedPreferences(){
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		prefsEditor.putInt(MY_HOUR, currentAlarm.getTime(true).getHour());
		prefsEditor.putInt(MY_MINUTE, currentAlarm.getTime(true).getMinute());
		prefsEditor.putInt(MY_ACTIVE, currentAlarm.getActive()?1:0);
		prefsEditor.putString(MY_AUDIO, (currentAlarm.getAudio()=="")?"blank":currentAlarm.getAudio());
		prefsEditor.putInt(MY_SNOOZE , currentAlarm.getSnooze());
		prefsEditor.commit();
		Log.d("Kevin","Set preferences to: " + currentAlarm.getTime(true).getHour() +
				" " + currentAlarm.getTime(true).getMinute() + " " + 
				Integer.toString(currentAlarm.getActive()?1:0) + " " +
				((currentAlarm.getAudio()=="")?"blank":currentAlarm.getAudio()) + 
				" " + currentAlarm.getSnooze());

	}

	Alarm getCurrentAlarm(){
		return currentAlarm;

	}

	public AlarmManager getStartAlarm(){
		return startAlarm;
	}

	void scheduleAlarm(){

		startAlarm.set(AlarmManager.RTC_WAKEUP, convertTime(currentAlarm.getTime(false)), 
				pending = PendingIntent.getActivity((Context)parent, 0,new Intent((Context)parent, TurnOff.class), PendingIntent.FLAG_UPDATE_CURRENT));
	}

	void cancelAlarm(){
		if(pending != null)
			startAlarm.cancel(pending);

	}

	kevinsAlarmClock getMyParent(){
		return parent;
	}

	void updateText(){
		if(parent!=null){
			parent.updateText();
		}
	}


}
