import java.lang.*;
import java.math.*;
import java.util.*;
import java.lang.Double.*;
import java.lang.Integer.*;
public class Shooter{
	public String name;
	public boolean alive = true;
	public double probability;
	public int turnGo;
	public int counter = 0;
	private static int turn = 0;
	public Shooter(String name, double prob, int playerTurn){
		this.name = name;
		this.probability = prob;
		this.turnGo = playerTurn;
		this.alive = true;
	}
	public String toString(){
		String output = name + " has a 1 in " + intValues(1/probability) + " chance of hitting and will be " + intFormat(turnGo) +".";
		return output;
	}
	public void getString(int count){
		double c = count;
		double counter3 = this.counter;
		double total = (100 * counter3/c);
		System.out.printf(name + " will win %.2f percent.\n", total);
	}
	public static void reset(){
		turn = 0;
	}
	public static void skip(){
		turn++;
	}
	public static int getTurn(){
		turn++;
		if (turn == 4){
			turn = 1;
		}
		return turn;
	}
	public static boolean shootAtTarget(double prob){
		return(Math.random() < prob);
	}
	public static String areAlive(Shooter hey){
		if(hey.alive = true){
			return hey.name + " true";
		}else{
			return hey.name + " false";
		}
	}
	private String intValues(double arg){
		return (Double.toString(arg).substring(0, Double.toString(arg).indexOf(".")));
	}
	private String intFormat(int val){
		if(val == 1){
			return "first";
		}
		if(val == 2){
			return"second";
		}
		if (val == 3){
			return "third";
		}
		System.out.println("Fatal Error");
		System.exit(0);
		return"Error";
	}
}