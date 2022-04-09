import java.lang.*;
import java.util.*;
import java.math.*;
public class board{
	Scanner input = new Scanner(System.in);
	private int size;
	private String[][] Board;
	public board(){
		size = 1;
		getBoard();
	}
	public board(int s){
		size = s;
		getBoard();
	}
	public void getBoard(){
		Board = new String[size][size];
		for(int i = 0; i < size; i++){
			for(int i2 = 0; i2 < size; i2++){
				Board[i][i2] = Integer.toString(size * i + (i2 + 1));
			}
		}
	}
	public String toString(){
		String print = "\n\n";
		for( int i = 0; i<Board.length; i++){
			for(int i2 = 0; i2 < Board[i].length; i2++){
				print += Board[i][i2] + " ";
			}
			print+= "\n\n";
		}
		return print;
	}
	public void makeMove(Player a){
		boolean tryAgain = true;
		System.out.println("Input move location");
		int b = input.nextInt();
		int c = 0;
		if (b > Math.pow(size,2)||b < 1){
			System.out.println("Improper Entry\nPlease input proper entry.");
			b = input.nextInt();
		}
		while(tryAgain == true){
			int i = 0;
			while(b > size){
				b -= size;
				i++;
			}
			if (b <= size){
				for(int i2 = 0; i2 < size; i2++){
					b -= 1;
					if (b == 0){
						if (Board[i][i2].compareTo("1") >=0 && Board[i][i2].compareTo("9") <=0){
							Board[i][i2] = a.toSymbol();
								tryAgain = false;
						}else{
							System.out.println("Already move there\nPlease input proper entry.");
							c = input.nextInt();
						}
					}
				}
			}
			b = c;
		}

		System.out.println(toString());
		if (won(a)){
			System.out.println(a + " has won");
			System.exit(0);
		}
		if(full()){
			System.out.println("Cat's game");
			System.exit(0);
		}
	}
	public void makeMove(Player a, int b){
		if (b > Math.pow(size,2)){
			System.exit(0);
		}
		int i = 0;
		while(b > size){
			b -= size;
			i++;
		}
		if (b <= size){
			for(int i2 = 0; i2 < size; i2++){
				b -= 1;
				if (b == 0){
					if(Board[i][i2].compareTo("1") >=0 && Board[i][i2].compareTo("9") <=0){
						Board[i][i2] = a.toSymbol();
					}else{
						System.out.println("Improper Entry");
						System.exit(0);
					}
				}
			}
		}
		System.out.println(toString());
		if (won(a)){
			System.out.println(a + " has won");
			System.exit(0);
		}
		if(full()){
			System.out.println("cat's game");
			System.exit(0);
		}
	}
	public boolean won(Player a){
		boolean w = false;
		int counter = 0;
		for(int i = 0;i < size; i++){
			counter = 0;
			int i2 = -1;
			while (w = true && i2 < size - 1){
				i2++;
				if(Board[i2][i] != a.toSymbol()){
					w = false;
				}else if (counter == 2){
					return true;
				}else{
					counter ++;
				}
			}
		}
		counter = 0;
		for(int i = 0;i < size ; i++){
			int i2 = -1;
			counter = 0;
			while (w = true && i2 < size - 1){
				i2++;
				if(Board[i][i2] != a.toSymbol()){
					w = false;
				}else if (counter == 2){
					return true;
				}else{
					counter ++;
				}
			}
		}
		counter = 0;
		int i = 0;
		int i2 = 0;
		w = true;
		while (0<=i && i < size && 0<= i2 && i2 < size && w == true){
			if(Board[i][i2] != a.toSymbol()){
				w = false;
			}else if (counter == 2){
				return true;
			}else{
				counter ++;
			}
			i ++;
			i2 ++;
		}
		counter = 0;
		i = 0;
		i2 = size- 1;
		w = true;
		while (0<=i && i< size && 0<=i2 &&  i2 < size && w == true){
			if(Board[i][i2] != a.toSymbol()){
				w = false;
			}else if (counter == 2){
				return true;
			}else{
				counter ++;
			}
			i ++;
			i2 --;
		}
		return false;
	}
	public boolean full(){
		boolean w = true;
		for(int i = 0; i < size; i++){
			for(int i2 = 0; i2 < size; i2++){
				if(Board[i][i2].compareTo("1") >=0 && Board[i][i2].compareTo("9") <=0){
					w = false;
				}
			}
		}
		return w;
	}


}
