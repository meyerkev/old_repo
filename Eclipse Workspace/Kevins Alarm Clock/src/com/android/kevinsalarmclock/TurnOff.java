package com.android.kevinsalarmclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TurnOff extends Activity{
	
	//private static Alarm currentAlarm;
	private static Information parent;
	private static Alarm myAlarm;//solely exists to serve in event that parent is made null
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.turnoff);
		
		//trigger parent's currentAlarm
		if(parent != null){
			parent.currentAlarm.trigger();
		}else{
			myAlarm.trigger();
		}
		
		Button snooze = (Button) findViewById(R.id.snooze_button);
		Button turnOff = (Button) findViewById(R.id.turn_off_button);
		
		snooze.setOnClickListener(new View.OnClickListener() {  
			public void onClick(View view) {
				parent.currentAlarm.snooze();
				exit();

			}
		});
		
		turnOff.setOnClickListener(new View.OnClickListener() {  
			public void onClick(View view) {
				parent.currentAlarm.turnOff();
				exit();

			}
		});
	
	}
	
	void exit(){
		this.finish();
		
	}
	
	public static void setParent(Information k){
		parent = k;
	}
	
	public static void setMyAlarm(Alarm a){
		myAlarm = a;
		
	}
	
}
