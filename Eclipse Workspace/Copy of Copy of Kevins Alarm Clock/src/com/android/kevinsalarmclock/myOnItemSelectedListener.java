package com.android.kevinsalarmclock;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;


public class myOnItemSelectedListener implements OnItemSelectedListener {
	String info = "";
	Information higher;
	public void setInfo(String s){
		info = s;
		
	}
	
	public myOnItemSelectedListener(String s,Information h){
		info = s;
		higher = h;
	}
	
	public void onItemSelected(AdapterView<?> parent,
			View view, int pos, long id) {

		String[] times = parent.getResources().getStringArray(R.array.spinner_choices);
		//Toast.makeText(parent.getContext(), "The " + info + " is " +
		//times[pos], Toast.LENGTH_LONG).show();
		Log.d("Kevin", pos+ "");
		higher.getCurrentAlarm().setSnooze(Integer.parseInt(times[pos]));
		higher.setCurrentAlarm(higher.currentAlarm);
		//higher.mp().switchSong(pos);
		
	}

	public void onNothingSelected(AdapterView parent) {
		// Do nothing.
	}
}

