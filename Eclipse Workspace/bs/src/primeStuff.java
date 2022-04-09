import java.lang.*;
import java.util.*;
public class primeStuff {



	public static void main(String[] args) {
		//System.out.println(sumPrimeDivisors(1+firstGreater(227000)));
		int total = 0;
		int[] nums = {3,4,9,14,15,19,28,37,47,50,54,56,59,61,70,73,78,81,92,95,97,99};
		for(int i = 1; i <nums.length; i++){
			for (int j = 0; j<Math.pow(2,i); j++){
				String s = Integer.toBinaryString(j);
				while(s.length() < i){
					s = "0" + s;
				}
				//System.out.println(s);
				int[] numbers = new int[Integer.bitCount(j)] ;
				int loc = 0;
				for(int k = 0; k<s.length(); k++){
					if(s.charAt(k)=='1'){
						numbers[loc] = nums[k];
						loc++;
						
					}
				}
				if(sum(numbers)==nums[i]){
					total++;
					System.out.println(total + " " + nums[i] + " "+ sum(numbers));
				}
			}
		}
		System.out.println(total);

	}

	public static int firstGreater(int min){
		int n = 0;
		do{
			n++;
		}
		while(fib(n)<min || !isPrime(fib(n)));
		return fib(n);
	}

	public static int fib(int n){
		return fibHelper(1,1,n);
	}
	public static int fibHelper(int first, int second, int n){
		if(n<=0){
			return first;	
		}
		first = first + second; 
		return fibHelper(second,first,n-1);
	}

	public static boolean isPrime(long n) {
		boolean prime = true;
		for (long i = 3; i <= Math.sqrt(n); i += 2)
			if (n % i == 0) {
				prime = false;
				break;
			}
		if (( n%2 !=0 && prime && n > 2) || n == 2) {
			return true;
		} else {
			return false;
		}
	}

	public static int sumPrimeDivisors(int n){
		int total = 0;
		for (int i = 2; i<Math.sqrt(n); i++){
			if(n%i ==0 && isPrime(i)){
				total += i;
			}
		}
		return total;

	}

	public static int sum(int[] n){
		int total = 0;
		for (int i = 0; i<n.length; i++){
			total += n[i];
		}
		return total;
	}


}
