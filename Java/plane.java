import java.lang.*;
import java.util.*;
import java.math.*;
public class plane{
	Scanner input = new Scanner(System.in);
	private String[][] airplane;
	private int rows;
	private int columns;
	private int seats;
	public plane(){
			rows = 7;
			columns = 4;
			seats = rows * columns;
			getAirplane();
	}
	public plane(String[][] a){
		airplane = a;
		rows = a[0].length;
		columns = a.length - 1;
		seats = rows * columns;
	}
	public plane(int r, int c){
		rows = r;
		columns = c;
		seats = rows * columns;
		getAirplane();
	}
	public String toString(){
		String printPlane = "";
		for( int i = 0; i<airplane.length; i++){
			for(int i2 = 0; i2 < airplane[i].length; i2++){
				printPlane += airplane[i][i2];
				if(i2 ==0){
					printPlane+=" ";
				}
			}
			printPlane += "\n";
		}
		printPlane += "There are " + seats + " seats availiable";
		return printPlane;
	}
	public void bookSeat(){
		System.out.println(toString());
		boolean tryAgain = true;
		int A2 = 0;
		int B2 = 0;
		while(tryAgain == true){
			System.out.println("Input availiable Seat ex: 4C");
			String seat = input.next();
			char A1 = seat.charAt(0);
			char B1 = seat.charAt(1);
			if (Character.isDigit(A1)){
				A2 = Integer.parseInt(Character.toString(A1));
				B2 = (Character.toUpperCase(B1) - 'A' + 1);
			}else{
				A2 = Integer.parseInt(Character.toString(B1));
				B2 = (Character.toUpperCase(A1) - 'A' + 1);
			}
			if( A2 <= airplane.length && B2 < airplane[0].length && A2 > 0 && B2 > 0 ){
				if(airplane[A2 - 1][B2] !="X"){
					tryAgain = false;
				}else{
					System.out.println("That seat is already booked.\nPlease book another seat\n" + toString());
				}
			}else{
				System.out.println("Improper Entry. \n" + toString());
			}
		}
		airplane[A2 - 1][B2] = "X";
		seats -= 1;
	}
	public void bookSeat(String seat){
		boolean tryAgain = true;
		int A2 = 0;
		int B2 = 0;
		while(tryAgain == true){
			char A1 = seat.charAt(0);
			char B1 = seat.charAt(1);
			if (Character.isDigit(A1)){
				A2 = Integer.parseInt(Character.toString(A1));
				B2 = (Character.toUpperCase(B1) - 'A' + 1);
			}else{
				A2 = Integer.parseInt(Character.toString(B1));
				B2 = (Character.toUpperCase(A1) - 'A' + 1);
			}
			if(A2 <= airplane.length && B2 < airplane[0].length && A2 > 0 && B2 > 0 ){
				if(airplane[A2 - 1][B2] !="X"){
					tryAgain = false;
				}
			}else{
				System.out.println("Improper Entry. \n" + toString());
			}

		}
		this.airplane[A2 - 1][B2] = "X";
		seats -= 1;
	}
	public void bookSeat( char A1, char B1){
		boolean tryAgain = true;
		int A2 = 0;
		int B2 = 0;
		while(tryAgain == true){
			if (Character.isDigit(A1)){
				A2 = Integer.parseInt(Character.toString(A1));
				B2 = (Character.toUpperCase(B1) - 'A' + 1);
			}else{
				A2 = Integer.parseInt(Character.toString(B1));
				B2 = (Character.toUpperCase(A1) - 'A' + 1);
			}
			if(A2 <= airplane.length && B2 < airplane[0].length && A2 > 0 && B2 > 0 ){
				if(airplane[A2 - 1][B2] !="X"){
					tryAgain = false;
				}
			}else{
				System.out.println("Improper Entry. \n" + toString());
			}
		}
		this.airplane[A2 - 1][B2] = "X";
		seats -= 1;
	}
	private void getAirplane(){
		airplane = new String[rows][columns + 1];
		for( int i = 0; i < airplane.length; i++){
			for (int i2 = 0; i2 < airplane[i].length; i2++){
				if (i2 == 0){
					airplane[i][i2] = Integer.toString(i + 1);

				}else{
					airplane[i][i2] = getLetter(i2);
				}
			}
		}
	}
	public boolean equals(plane other){
		if (airplane.length != other.airplane.length){
			return false;
		}else{
			for( int i = 0; i < airplane.length; i++){
				if (airplane[i].length != other.airplane[i].length){
					for (int i2 = 0; i2 < airplane[i].length; i2 ++){
						if (!airplane[i][i2].equals(other.airplane[i][i2])){
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	public boolean full(){

		if(seats <= 0){
			System.out.println("Full");
			return false;
		}else{
			return true;
		}
	}
	private String getLetter(int let){
		//return "A";
		switch(let){
			case 1:
			return "A";
			case 2:
			return "B";
			case 3:
			return "C";
			case 4:
			return "D";
			case 5:
			return "E";
			case 6:
			return "F";
			case 7:
			return "G";
			case 8:
			return "H";
			case 9:
			return "I";
			case 10:
			return "J";
			case 11:
			return "K";
			case 12:
			return "L";
			case 13:
			return "M";
			case 14:
			return "N";
			case 15:
			return "O";
			case 16:
			return "P";
			case 17:
			return "Q";
			case 18:
			return "R";
			case 19:
			return "S";
			case 20:
			return "T";
			case 21:
			return "U";
			case 22:
			return "V";
			case 23:
			return "W";
			case 24:
			return "X";
			case 25:
			return "Y";
			case 26:
			return "Z";
			default:
			System.out.println("Improper Entry");
			System.exit(0);
			return "A";
		}
	}
}
