import java.lang.*;
import java.util.*;
import java.math.*;
public class clock
{
	//variables
	private int hour;
	private int minute;
	private int second;
	private boolean AM;
	Scanner input = new Scanner(System.in);

	//constructors
	public clock(){
		hour = 12;
		minute = 0;
		second = 0;
		AM = true;
	}
	public clock(int h, int m){
		hour = h;
		minute = m;
		second = 0;
		AM = true;
		timeOk();
	}
	public clock(int h, int m, String AM){
		hour = h;
		minute = m;
		second = 0;
		AMchecker(AM);
		timeOk();
	}
	public clock(String time){
		StringTokenizer clockToken = new StringTokenizer(time, " :");
		if(clockToken.countTokens()>3){
			System.exit(0);
		}else if(clockToken.countTokens() == 3){
			hour = Integer.parseInt(clockToken.nextToken());
			minute = Integer.parseInt(clockToken.nextToken());
			AMchecker(clockToken.nextToken());
		}else if (clockToken.countTokens() == 2){
			hour = Integer.parseInt(clockToken.nextToken());
			minute = Integer.parseInt(clockToken.nextToken());
			AM = true;
		}else {
			System.exit(0);
		}
		second = 0;
		timeOk();
	}
	public clock(clock otherClock){
		hour = otherClock.hour;
		minute = otherClock.minute;
		AM = otherClock.AM;
		second = otherClock.second;
		timeOk();
	}
	//equals
	public void setEqual(clock otherClock){
		hour = otherClock.hour;
		minute = otherClock.minute;
		AM = otherClock.AM;
		timeOk();
	}
	public boolean equals(clock otherClock){
		return (hour == otherClock.hour && minute == otherClock.minute  && second == otherClock.second && AM ==otherClock.AM);
	}
	public boolean equalsIgnoreAM(clock otherClock){
		return (hour == otherClock.hour && minute == otherClock.minute && second == otherClock.second);
	}
	//set variables
	public void setTime()
	{
		System.out.println("Input the time");
		String time = input.nextLine();
		StringTokenizer clockToken = new StringTokenizer(time, " :,");
		if(clockToken.countTokens()>3){
			System.exit(0);
		}else if(clockToken.countTokens() == 3){
			setHour(Integer.parseInt(clockToken.nextToken()));
			setMinute(Integer.parseInt(clockToken.nextToken()));
			setAM(clockToken.nextToken());
		}else if (clockToken.countTokens() == 2){
			setHour(Integer.parseInt(clockToken.nextToken()));
			setMinute(Integer.parseInt(clockToken.nextToken()));
			AM = true;
		}else {
			System.exit(0);
		}
		second = 0;
		timeOk();
	}
	public void setHour(){
		System.out.println("Input the hour.");
		hour = input.nextInt();
		timeOk();
	}
	public void setHour(int inputHour){
		if(inputHour > 0 && inputHour < 13){
			hour = inputHour;
		}
	}
	public void setMinute(){
		System.out.println("Input the minute.");
		minute = input.nextInt();
		timeOk();
	}
	public void setMinute(int inputMinute){
		if(inputMinute >=0 && inputMinute<560){
			minute = inputMinute;
		}
	}
	public void setAM(){
		AMchecker();
	}
	public void setAM(String am){
		AMchecker(am);
	}
	public void setAM(boolean huh){
		AM = huh;
	}
	//do stuff
	public void advanceTime(){
		second += 1;
		checkTime();
	}
	//write output
	public String toString(){
		return (Integer.toString(hour)) + ":" + (writeMinute(minute)) + " " + writeAM();
	}
	public String toStringIgnoreAM(){
		return (Integer.toString(hour)) + ":" + (writeMinute(minute));
	}
	// check it
	private void checkTime(){
		if (second ==60) {
			second = 0;
			minute ++;
			if(minute == 60){
				hour ++;
				minute = 0;
				if(hour == 12){
					if(AM = true){
						AM = false;
					}else{
						AM = true;
					}
				}
				if(hour == 13){
					hour = 1;
				}
			}
		}
	}
	private void timeOk(){
		if(hour < 1 || hour >12 || minute <0 || minute >= 60){
			System.out.println("Improper Entry.");
			System.exit(0);
		}
	}
	//write output
	private String writeMinute(int minute){
		String minuteString = Integer.toString(minute);
		if(minute <= 9){
			minuteString = 0 + minuteString;
		}
		return minuteString;
	}
	private String writeAM(){
		if (AM == true){
			return "AM";
		}else{
			return "PM";
		}
	}
	//set and check stuff
	private void AMchecker(String AMcheck){
		if (AMcheck.equalsIgnoreCase("AM")){
			AM = true;
		}else if(AMcheck.equalsIgnoreCase("PM")){
			AM = false;
		}else{
			System.out.println("Improper Entry");
			System.exit(0);
		}
	}
	private void AMchecker(){
		int c = 0;
		System.out.println("Input 'AM' for AM, 'PM' for PM");
		String AMcheck = input.next();
		while (c == 0){
			if (AMcheck.equalsIgnoreCase("AM")){
				AM = true;
				c++;
			}else if(AMcheck.equalsIgnoreCase("PM")){
				AM = false;
				c++;
			}else{
				System.out.println("Improper Entry\nInput 'AM' for AM, 'PM' for PM");
			}
		}
	}

}