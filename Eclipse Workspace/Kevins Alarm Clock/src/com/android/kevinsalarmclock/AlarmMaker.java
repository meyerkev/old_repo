package com.android.kevinsalarmclock;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class AlarmMaker extends Activity {
	private TextView mTimeDisplay;
	private Button mPickTime;
	private Button addToDatabase;

	private int mHour;
	private int mMinute;
	private Dialog t;
	
	private static Information parent;
	
	public static void setParent(Information k){
		parent = k;
	}

	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
	    new TimePickerDialog.OnTimeSetListener() {
	        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	            mHour = hourOfDay;
	            mMinute = minute;
	            updateDisplay();
	        }
	    };
	static final int TIME_DIALOG_ID = 0;
	// capture our View elements
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarmmaker);
		
		WindowManager w = getWindowManager();
        Display d = w.getDefaultDisplay();
        int width = d.getWidth();
        int height = d.getHeight();
		
		mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
		mTimeDisplay.setWidth(width);
		//TODO: Set text to stretch across screen
		
		
		mPickTime = (Button) findViewById(R.id.pickTime);
		addToDatabase = (Button) findViewById(R.id.add);
		
		

		// add a click listener to the button
		mPickTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
				
			}
		});
		
		addToDatabase.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				parent.currentAlarm.setTime(mHour,mMinute);
				//addToDatabase(a);
				parent.setCurrentAlarm(parent.currentAlarm);
				parent.getMyParent().updateText();
				finish();
			}
		});

		// get the current time
		//final Calendar c = Calendar.getInstance();
		mHour = parent.currentAlarm.getTime(true).getHour();
		mMinute = parent.currentAlarm.getTime(true).getMinute();

		// display the current date
		updateDisplay();

	}

	private void updateDisplay() {
		mTimeDisplay.setText(
				new StringBuilder()
				.append(pad(mHour)).append(":")
				.append(pad(mMinute)));
	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case TIME_DIALOG_ID:
	        return new TimePickerDialog(this,
	                mTimeSetListener, mHour, mMinute, false);
	    }

		
	    return null;
	}

	protected Dialog onReturnDialog(int id) {
	    switch (id) {
	    case TIME_DIALOG_ID:
	    	parent.currentAlarm.setTime(mHour,mMinute);
			//addToDatabase(a);
			parent.setCurrentAlarm(parent.currentAlarm);
			finish();
	    	
	    	return null;
	    }

		
	    return null;
	}


}
