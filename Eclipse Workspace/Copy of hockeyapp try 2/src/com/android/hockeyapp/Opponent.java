package com.android.hockeyapp;

public class Opponent {
	String name;
	String initials;
	int icon;
	String location;
	
	static final Opponent defOpp = new Opponent("Some U", "SU", R.drawable.icon, "??,??");
	
	static final Opponent US = new Opponent("University of Michigan", "UM",R.drawable.uofm_icon,"Ann Arbor, MI");
	
	static final Opponent MSU = new Opponent("Michigan State University", "MSU",R.drawable.msu_icon,"East Lansing, MI");
	static final Opponent OSU = new Opponent("The Ohio State University", "OSU", R.drawable.osu_icon,"Columbus, OH");
	static final Opponent WSU = new Opponent("Western Michigan University", "WMU", R.drawable.wsu_icon,"??, MI");
	static final Opponent NOTREDAME = new Opponent("Notre Dame", "UND", R.drawable.notredame_icon, "??, IN");
	static final Opponent ALASKA = new Opponent("University of Alaska", "UA", R.drawable.afu_icon,"??,AL");
	static final Opponent BOWLINGGREEN = new Opponent("Bowling Green State University", "BGSU", R.drawable.bgsu_icon,"Bowling Green, OH");
	static final Opponent FERRISSTATE = new Opponent("Ferris State University", "FSU", R.drawable.ferris_icon,"??,??");
	static final Opponent MIAMI = new Opponent("MIAMI - OHIO", "UM-OH",R.drawable.miami_icon,"??,OH");
	static final Opponent NMU = new Opponent("Northern Michigan University", "NMU", R.drawable.nmu_icon, "??, MI");
	static final Opponent LSSU = new Opponent("Lake Superior State University", "LSSU", R.drawable.lssu_icon, "??,MI");
	static final int numOpponents = 9;
	
	public Opponent(String n,String in,int i, String l){
		name = n;
		initials = in;
		icon = i;
		location = l;
	}
	
	public String toString(){
		return initials;
	}
	
	static Opponent returnRandomOpponent(){
		return getOpponent((int)(numOpponents*Math.random()));
	}
	
	static Opponent getOpponent(int n){
		Opponent o;
		switch(n){
		case 0:
			return MSU;
		case 1:
			return OSU;
		case 2:
			return WSU;
		case 3:
			return NOTREDAME;
		case 4:
			return ALASKA;
		case 5:
			return BOWLINGGREEN;
		case 6: 
			return FERRISSTATE;
		case 7:
			return MIAMI;
		case 8: 
			return NMU;		
		default:
			return defOpp;
		}
		
		
		
	}
	
}
