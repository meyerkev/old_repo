import java.lang.*;
import java.util.*;
public class clockTest
{
	public static void main(String[] args){
		clock c1 = new clock(3, 45, "PM");
		clock c2 = new clock("2:43 am");
		c2.setEqual(c1);
		System.out.println(c2);
		c2 = c1;
		c2.setTime();
		System.out.println(c1);
	}
}