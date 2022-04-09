import static java.lang.System.out;
import java.math.BigInteger;
import java.text.NumberFormat;
public class Fibonacci {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//int v. double(v float v long)
		//ints are whole numbers only, longs are HUGE ints.  
		//Also, shorts and bytes - too small
		//doubles and floats are for decimals.  
		//Never use floats ever for inaccuracy purposes
		NumberFormat comma = NumberFormat.getInstance();
		double d = 10000000.8523;
		float f = (float)d;
		long l = (long)f;
		int i = (int)l;
		out.println(d);
		out.println(f);
		out.println(l);
		out.println(i);
		out.println((int)d);
		//add
		out.println("Manipulation of i");
		i+=1;
		out.println(i);
		out.println(i++);
		out.println(++i);
		//subtract
		i-= 5;
		out.println(i);
		//times 
		i*= 3;
		out.println(i);
		//divide
		i/=53;
		out.println(i);
		//other useful things-remainder
		out.println(i%45);
		
		//
		//if loops do something once
		//if(boolean x){
			//y()
		//}else if(){
		//}else(
		//)
		//
		//while loops do something multiple times
		//while(boolean x){
			//y();
		//}
		//
		//do-while loops do something multiple times, but do them once before checking
		//do{
		//y();
		//}while(boolean x);
		//
		//for loops are compact while loops
		//example
		//for(int i = 0; i < 3; i++){
			//y();
		//}
		//
		//
		//This is the same as
		//int counter = 0;
		//while(counter <3){
		//	y();
		//	counter ++;
		//}
		//
		//
		//
		//Biginteger fibbonacci
		//Biginteger/BigDecimal is for really huge numbers
		
		out.println("Fibbnacci with BigIntegers");
		BigInteger n1 = new BigInteger("1");
		BigInteger n2 = new BigInteger("1");
		for(int i2 = 0; i2 < 300; i2++){
			out.println(comma.format(n1));
			out.println(comma.format(n2));
			n1 = n1.add(n2);
			n2 = n2.add(n1);
		}
		out.println("Fibbnacci with ints");
		int n3 = 1;
		int n4 = 1;
		for(int i2 = 0; i2 < 30; i2++){
			out.println(comma.format(n3));
			out.println(comma.format(n4));
			n3+=n4;
			n4+=n3;
		}
		out.println("See all of the errors!!!");
		out.println(System.currentTimeMillis());
	}
}
