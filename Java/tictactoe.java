import java.lang.*;
import java.util.*;
import java.math.*;
public class tictactoe{
	public static void main(String[] args){
		System.out.println(Math.pow(1.0/6.0, 14));
		board b1 = new board(3);
		Player x = new Player("X");
		Player o = new Player("O");
		System.out.println(b1);
		while(!b1.full()){
			if (x.isMyTurn()){
				System.out.print(x + ", ");
				b1.makeMove(x);
			}
			if(o.isMyTurn()){
				System.out.print(o + ", ");
				b1.makeMove(o);
			}
		}
		System.out.println(b1);
	}
}
