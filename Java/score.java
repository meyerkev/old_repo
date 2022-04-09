import java.lang.*;
import java.util.*;
public class score{
	private int outof;
	private int points;
	private double grades;
	private double weight;
	private String type;
	private char grade;
	Scanner input = new Scanner(System.in);
	//constructors
	public score(){
		type = "quiz";
		points = 0;
		getScores(type);
		grades = points/outof;
	}
	public score(int p){
		type = "quiz";
		points = p;
		getScores(type);
		grades = points/outof;
	}
	public score(String t){
		type = t;
		points = 0;
		getScores(type);
		grades = points/outof;
	}
	public score(String t, int p){
		type = t;
		points = p;
		getScores(type);
		grades = points/outof;
	}
	public String toString(){
		return Integer.toString(points);
	}
	public static double average(score... a){
		int totalPoints = 0;
		int totalOutOf = 0;
		double ave;
		boolean more = true;
		for(int i = 0; (i) < a.length; i++){
			totalPoints += a[i].points;
			totalOutOf += a[i].outof;
		}
		ave = totalPoints/totalOutOf;
		return ave;
	}
	private void getScores(String t2){
		if (t2 == "quiz"){
			outof = 10;
			weight = .25;
		}else if(t2 == "midterm"){
			outof = 100;
			weight = .35;
		}else if( t2 == "final"){
			outof = 100;
			weight = .4;
		}else {
			System.exit(0);
		}
	}
}
