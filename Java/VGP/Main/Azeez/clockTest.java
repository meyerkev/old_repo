public class clockTest
{
	public static void main(String[] args){
		//shallow vs deep copies - learn
		clock c1 = new clock(3, 45);
		clock c2 = new clock("2:43");
		//deep copy		
		c2.setEqual(c1);
		System.out.println(c2);
		//shallow copy
		c2 = c1;
		c2.setTime(1);
		System.out.println(c1);		
	}
}