import java.lang.*;
import java.util.*;
import java.math.*;
public class booking{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		plane p1 = new plane(7,4);
		boolean exit = false;
		while(p1.full() && exit == false){
			p1.bookSeat();
			System.out.println("If you wish to book another seat, enter Y.");
			if(!input.next().equalsIgnoreCase("Y")){
				exit = true;
			}
		}
		System.out.println(p1);
	}
}