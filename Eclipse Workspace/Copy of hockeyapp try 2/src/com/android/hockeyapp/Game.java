package com.android.hockeyapp;

import android.text.format.Time;

public class Game {
	//What does a game have
	//date
	//time
	//location
	//opponent
	Time t;
	String location;
	Opponent opponent;
	boolean home;
	private Integer ourScore;
	private Integer theirScore;
	enum victoryState{NOTPLAYED,WON,LOST,DREW};
	private victoryState won;
	
	public Game(Time ti, String l, Opponent o, boolean h, Integer ours, Integer theirs){
		t = new Time(ti);
		location = l;
		opponent = o;
		home = h;
		setScore(ours,theirs);
	}
	
	public Game(Time ti, Opponent o, boolean h){
		this(ti,h?Opponent.US.location:o.location,o,h, null, null);
	}
	
	public String toString(){
		return dateString()+ " // " +
		vsString() + " // " +
		resultsString();
	}
	
	public String dateString(){
		return MonthFormat(t.month) + " " + Integer.toString(t.monthDay);
	}
	
	public String vsString(){
		return (home?"vs.":"@") +" " + opponent.initials;
	}
	
	public void setScore(Integer ours, Integer theirs){
		//IF either ours or theirs is null, victory state is set to NOTPLAYED
		ourScore = ours;
		theirScore = theirs;
		if (ours == null || theirs == null){
			won = victoryState.NOTPLAYED;
		}else if(ours>theirs){
			won = victoryState.WON;
		}else if (ours == theirs){
			won = victoryState.DREW;
		}else{
			won = victoryState.LOST;
		}
	}
	
	private String MonthFormat(int n){
		String s = "";
		switch(n){
		case 0:
			s= "Jan";
			break;
		case 1:
			s= "Feb";
			break;
		case 2:
			s= "Mar";
			break;
		case 3:
			s= "Apr";
			break;
		case 4:
			s= "May";
			break;
		case 5:
			s= "Jun";
			break;
		case 6:
			s= "Jul";
			break;
		case 7:
			s= "Aug";
			break;
		case 8:
			s= "Sep";
			break;
		case 9:
			s= "Oct";
			break;
		case 10:
			s= "Nov";
			break;
		case 11:
			s= "Dec";
			break;
		default:
			s= "Bad" + Integer.toString(n);
		}
		return s;
		
	}
	
	public String timeString(){
		return Backend.timeString(t);
		
	}
	
	public String resultsString(){
		String ret = "";
		if(won == victoryState.WON){
			ret = "W " + ourScore + " - " + theirScore;
		}else if(won == victoryState.DREW){
			ret = "D " + ourScore + " - " + theirScore;
		}else if(won == victoryState.LOST){
			ret = "L " + theirScore + " - " + ourScore;
		}else{ //(won == victoryState.NOTPLAYED){
			ret = timeString();
		}
		return ret;
	}
}
