import java.util.*;

public class clock{
	//variables
	protected int hour;
	protected int minute;
	protected Scanner input = new Scanner(System.in);

	//constructors
	public clock(){
		hour = 12;
		minute = 0;
		timeOk();
	}
	public clock(int h, int m){
		hour = h;
		minute = m;
		timeOk();
	}
	public clock(int h){
		this(h,0);
	}

	public clock(String time){
		StringTokenizer clockToken = new StringTokenizer(time, " :");
		if(clockToken.countTokens()>2){
			System.exit(0);
		}else if (clockToken.countTokens() == 2){
			hour = Integer.parseInt(clockToken.nextToken());
			minute = Integer.parseInt(clockToken.nextToken());
		}else {
			System.exit(0);
		}
		timeOk();
	}
	public clock(clock otherClock){
		hour = otherClock.hour;
		minute = otherClock.minute;
		timeOk();
	}
	//equals
	public void setEqual(clock otherClock){
		hour = otherClock.hour;
		minute = otherClock.minute;
		timeOk();
	}
	public boolean equals(clock otherClock){
		return (hour == otherClock.hour && minute == otherClock.minute);
	}
	//set variables
	public void setTime(String time)
	{
		StringTokenizer clockToken = new StringTokenizer(time, " :,");
		if(clockToken.countTokens()>2){
			System.exit(0);
		}else if (clockToken.countTokens() == 2){
			setHour(Integer.parseInt(clockToken.nextToken()));
			setMinute(Integer.parseInt(clockToken.nextToken()));
		}else {
			System.exit(0);
		}
		timeOk();
	}
	public void setTime()
	{
		System.out.println("Input the time");
		String time = input.nextLine();
		setTime(time);
	}
	public void setTime(int a)
	{
		setTime("12:00");
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
	//do stuff
	public void advanceTime(){
		minute += 1;
		checkTime();
	}
	//write output
	public String toString(){
		return (Integer.toString(hour)) + ":" + (writeMinute(minute));
	}
	// check it
	protected void checkTime(){
		while(minute >= 60){
			hour ++;
			minute -= 60;
			while(hour >= 13){
				hour -= 12;
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

}