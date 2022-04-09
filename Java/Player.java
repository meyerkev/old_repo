import java.lang.*;
import java.util.*;
import java.math.*;
public class Player{
	Scanner input = new Scanner(System.in);
	private String name;
	private int turn;
	private String symbol;
	private static int counter;
	private static int Iturn;
	private static int total;
	public Player(){
		name = "";
		symbol = "X";
		getInfo();
	}
	public Player(String n){
		name = "";
		symbol = n;
		getInfo();
	}
	public Player(String n, String s){
		name = n;
		symbol = s;
		getInfo();
	}
	public boolean isMyTurn(){
		Iturn ++;
		return (turn == (Iturn- 1)%2);
	}
	public void getInfo(){
		if(name == ""){
			System.out.println("Player " + (counter + 1) + ": Input name");
			name = input.next();
		}
		turn = counter;
		counter ++;
		if (symbol == ""){
			System.out.println("Input symbol");
			symbol = input.next();
		}
		total ++;
	}
	public String toString(){
		return (name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase() );
	}
	public String toSymbol(){
			return (symbol.toUpperCase());
	}
}