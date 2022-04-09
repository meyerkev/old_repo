
public class STATSHW {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//prob1();
		prob2();
	}
	
	static void prob2(){
		//P(x; μ) = (e^-μ) (μ^x) / x! when x = ours, and u = average
		double max = 10;
		double u = max/2;//poisson is .5, so 
		double x = 10;
		
		
		
	}
	
	
	
	static void prob1(){
		double total=0,sqtotal = 0;
//		for (int i = 0; i< 10; i++){
//			System.out.println(i + " " + factorial(i));
//			
//			
//		}
		
		for (int i = 0; i<= 25; i ++){
			double f = i;
			double num = f * C(25,f)*Math.pow(.98, 25-i)*Math.pow(.02,i);
			double numsq=  f*num;
			//System.out.println(f + " " + num + " " + numsq);
			total +=num;
			sqtotal += numsq;
		}
		System.out.println(total);
		System.out.println(sqtotal);
		System.out.println(sqtotal - total*total);
		
		
	}
	
	static double C(double n, double k){
		//int top
		return factorial(n)/(factorial(k)*factorial(n-k));
	}
	
	static double factorial(double n){

		//System.out.println(n);
		return fact_helper(n,1);
	}
	
	static double fact_helper(double n, double base){
		if (n<=1){return base;}
		return fact_helper(n-1,base*n);
	}

}
