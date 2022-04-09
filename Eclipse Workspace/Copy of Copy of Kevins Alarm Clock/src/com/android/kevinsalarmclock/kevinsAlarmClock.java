package com.android.kevinsalarmclock;



import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class kevinsAlarmClock extends Activity {



	/** Called when the activity is first created. */

	//Alarm myInfo.getCurrentAlarm() = new Alarm();
	private Information myInfo;
	private CheckBox active;
	private TextView tv;
	private TextView audio;
	private int mMinute, mHour;
	private static boolean hasRun = false;
	
	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
	    new TimePickerDialog.OnTimeSetListener() {
	        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	            mHour = hourOfDay;
	            mMinute = minute;
	            myInfo.currentAlarm.setTime(mHour, mMinute);
	            updateText();
	            //updateDisplay();
	        }
	    };
	static final int TIME_DIALOG_ID = 0;
	/*
	 * Alarm-
	 * hour
	 * minute
	 * active
	 * audio
	 * 
	 * snoozeTime = myInfo.getCurrentAlarm().getSnooze();
	 */



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		if(myInfo == null)
			myInfo = new Information(this);
		else
			myInfo.setParent(this);
		
		Alarm.setParent(myInfo);
		TurnOff.setParent(myInfo);
		MusicDroid.setParent(myInfo);
		SongLists.setParent(myInfo);
		MusicList.setParent(myInfo);
		tv = (TextView)findViewById(R.id.main_text);
		audio = (TextView)findViewById(R.id.audio_text);
		Button addAlarm = (Button) findViewById(R.id.alarm_button);
		Button test = (Button) findViewById(R.id.test_button);
		active = (CheckBox) findViewById(R.id.active_box);

		Button testing = (Button) findViewById(R.id.set_new_alarm_button);
		
		myInfo.getMySharedPreferences();
		mHour = myInfo.currentAlarm.getTime(true).getHour();
		mMinute = myInfo.currentAlarm.getTime(true).getMinute();
		//setCurrentAlarm(new Alarm(t.hour,t.minute, true,""));//"/sdcard/song.mp3"));//my music/Amon Amarth/Twilight of the Thunder God/Free Will Sacrifice.mp3"));
		updateText();
		addAlarm.setOnClickListener(new View.OnClickListener() {  
			public void onClick(View view) {
				showDialog(TIME_DIALOG_ID);
				/*
				Intent myIntent = new Intent(view.getContext(), AlarmMaker.class);
				//myIntent.putExtra(MyListView.higher, firstApp.this);
				startActivity(myIntent);
//*/

			}
		});
		//*/
		test.setOnClickListener(new View.OnClickListener() {  
			public void onClick(View view) {
				trigger();


			}
		});

		final Spinner spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.spinner_choices, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		myOnItemSelectedListener m;
		spinner.setOnItemSelectedListener(m = new myOnItemSelectedListener("numbers", myInfo));
		spinner.setPrompt("Set snooze time in minutes:");
		int spot = 0;
		for(int i = 0; i < spinner.getAdapter().getCount();i++){
			Log.d("Kevin", i + " = " + (spinner.getAdapter().getItem(spot)));
		}
		for(spot = 0; spot < spinner.getAdapter().getCount();spot++){
			if(myInfo.getCurrentAlarm().getSnooze()==Integer.parseInt((String)(spinner.getAdapter().getItem(spot)))){
				Log.d("Kevin", "Spot  = " + spot +"");
				break;
				
			}
			
			
		}
		//AdapterView<ArrayAdapter<CharSequence>> ohDearGod = new AdapterView<ArrayAdapter<CharSequence>>();
		m.onItemSelected(spinner, spinner, spot, 0l);
		active.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton b, boolean toggled){
				myInfo.getCurrentAlarm().setActive(toggled);
				myInfo.setCurrentAlarm(new Alarm(myInfo.getCurrentAlarm()));
			}



		});

		testing.setOnClickListener(new View.OnClickListener() {  
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), MusicDroid.class);
				//myIntent.putExtra(MyListView.higher, firstApp.this);
				startActivity(myIntent);


			}
		});
		
		//TODO: set up editable snooze times - Spinner - eh kinda
	}

	

	void trigger(){
		myInfo.getCurrentAlarm().trigger();
		Intent i = new Intent((Context)this, TurnOff.class);
		startActivity(i);
	}

	void setStuff(Alarm a){
		boolean x = myInfo.getCurrentAlarm() == a;//DON'T DELETE!! LEADS TO STACK OVERFLOW ERRORS - old
		active.setChecked(myInfo.getCurrentAlarm().getActive());
		updateText();
		myInfo.updateSharedPreferences();
		Log.d("Kevin", a.toString());
		if(a.getActive()){
			myInfo.scheduleAlarm();
		}else{
			a.cancelSnooze();
			myInfo.cancelAlarm();
			if(!x)
				a.turnOff();
		}
	}

	public void updateText(){
		tv.setText(((String) tv.getText()).substring(0,((String) tv.getText()).indexOf(":") +1)	
				+ " " + myInfo.getCurrentAlarm().getTime(true).toString());
		audio.setText(((String) tv.getText()).substring(0,((String) tv.getText()).indexOf(":") +1) 
				+ " " + ((myInfo.getCurrentAlarm().getAudio()!="")?myInfo.getCurrentAlarm().getAudio():"default"));
	}
	
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
	    //int i=0,j = 0;
		switch (id) {
	    case TIME_DIALOG_ID:
	        return new TimePickerDialog(this,
	                mTimeSetListener, mHour,mMinute, false);
	    }

		
	    return null;
	}

	protected Dialog onReturnDialog(int id) {
	    switch (id) {
	    case TIME_DIALOG_ID:
	    	myInfo.currentAlarm.setTime(mHour,mMinute);
			//addToDatabase(a);
			updateText();
	    	
	    	return null;
	    }

		
	    return null;
	}
	



}