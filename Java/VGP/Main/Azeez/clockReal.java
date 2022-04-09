import java.util.*;
public class clockReal
{
	public static void main(String[] args){
		//establish clocks and speeds
		Scanner input = new Scanner(System.in);
		System.out.println("Input Clock 1 (h:mm), then speed");
		String time1;
		time1 = input.nextLine() + " ";
		System.out.println("Input Clock 2 (h:mm), then speed");
		time1 += input.nextLine();
		StringTokenizer time = new StringTokenizer(time1, " :,");
		if (time.countTokens() != 6){
			System.exit(0);
		}
		clock c1 = new clock(Integer.parseInt(time.nextToken()), Integer.parseInt(time.nextToken()));
		int s1 = Integer.parseInt(time.nextToken());
		clock c2 = new clock(Integer.parseInt(time.nextToken()), Integer.parseInt(time.nextToken()));
		int s2 = Integer.parseInt(time.nextToken());
		//set them equal
		int counter = 0;
		if(c1.equals(c2)){
			System.out.println("The clocks are already equal at: " + c1);
		}else if(s1 == s2 && !c1.equals(c2)){
			System.out.println("The clocks will never equal each other.");
		}else{
			do{
				counter ++;
				if(counter % s2 == 0){
					c1.advanceTime();
				}
				if(counter % s1 == 0){
					c2.advanceTime();
				}
				if(counter == s1 * s2){
					counter = 0;
				}
			}while(!c1.equals(c2));
			System.out.println("The clocks will be equal at: " + c1);
		}
	}
}
