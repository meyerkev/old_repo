package com.android.hockeyapp;

import java.util.Vector;

import android.text.format.Time;

import com.android.hockeyapp.Game.victoryState;

public class Backend {
	
	private static Vector<Game> games =new Vector<Game>();
	
	
	//Game constructor - Time ti, String l, Opponent o, boolean h
	//If no l, uses either home or away defaults;
	static void buildGames(){

		Time now = new Time();
		now.setToNow();
		Time t = new Time();
		t.set(0,5,19,1, 10, 2010);
		for (int i = 0; i<35; i++){
			
			//build bullshit time
			t.monthDay+=2;
			t.normalize(false);
			
			Game g = new Game(t,Opponent.returnRandomOpponent(),2*Math.random()<1);
			if(g.t.before(now)){
				g.setScore((int)(4*Math.random()), (int)(4* Math.random()));
			}else{
				g.setScore(null, null);
			}
			games.add(g);
		}
	}
	
	static String[] returnGameText(){
		String[] gameText = new String[25];
		for (int i = 0; i<25; i++){
			gameText[i] = games.elementAt(i).toString();
			
		}
		
		
		return gameText;
	}
	
	static String timeString(Time t){
		int hour = t.hour;
		int minute = t.minute;
		boolean AM = true;
		if(hour == 0){
			hour = 12;
		}else if (hour == 12){
			AM = false;
		}else if (hour>12){
			hour -= 12;
			AM = false;
		}
		return Integer.toString(hour)+":" + pad (minute) +" " + ((AM)?"AM":"PM");
	}
	
	private static String pad(int n){
		String s = "";
		if(n<10)
			s +="0";
		s += Integer.toString(n);
		return s;
	}

	public static Game[] getGames(int month, int year) {
		// TODO Auto-generated method stub
		Game[] g = new Game[0];
		for(int i = 0; i<games.size(); i++){
			if(games.elementAt(i).t.month == month && games.elementAt(i).t.year == year){
				Game[] f = new Game[g.length+1];
				for (int j = 0 ; j<g.length; j++){
					f[j] = g[j];
				}
				f[g.length] = games.elementAt(i);
				g = f;
			}
			
			
		}
		return g;
	}
}
