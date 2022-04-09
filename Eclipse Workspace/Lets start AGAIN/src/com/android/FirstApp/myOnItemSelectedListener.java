package com.android.FirstApp;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


public class myOnItemSelectedListener implements OnItemSelectedListener {
	String info = "";
	int currentSong = 0;
	firstApp higher;
	public void setInfo(String s){
		info = s;
		
	}
	
	public myOnItemSelectedListener(String s,firstApp h){
		info = s;
		higher = h;
	}
	
	public void onItemSelected(AdapterView<?> parent,
			View view, int pos, long id) {

		String[] bands = parent.getResources().getStringArray(R.array.genres);
		Toast.makeText(parent.getContext(), "The " + info + " is " +
		bands[pos], Toast.LENGTH_LONG).show();
		higher.mp().switchSong(pos);
		
	}

	public void onNothingSelected(AdapterView parent) {
		// Do nothing.
	}
}

