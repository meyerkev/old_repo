package com.android.hockeyapp;

import android.app.ListActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemSelectedListener;


public class myOnItemSelectedListener implements OnItemSelectedListener {
	
	private GameListActivity parent;
	
	public myOnItemSelectedListener(GameListActivity p){
		parent = p;
	}
	
	public void onItemSelected(AdapterView<?> parent,
			View view, int pos, long id) {
		int year = 0;
		int month = 0;
		
		switch(pos){
		case 0:
			month = 10; year = 2010;
			break;
		case 1: 
			month = 11; year = 2010;
			break;
		case 2: 
			month = 0; year = 2011;
		}
		this.parent.rebuild(month, year);
		//setListAdapter(new ArrayAdapter<String>(parent., R.layout.hockey_game_item, Backend.returnGameText()));
		
		
		
		
	}

	public void onNothingSelected(AdapterView parent) {
		// Do nothing.
	}
}

