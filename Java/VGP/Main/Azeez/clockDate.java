
public class clockDate extends AMclock {
	private Date d;
	public clockDate() {
		super();
		d = new Date();
		// TODO Auto-generated constructor stub
	}

	public clockDate(AMclock otherClock, Date d) {
		super(otherClock);
		this.d = new Date(d);
	}
	
	public clockDate(clockDate otherClock) {
		super(((AMclock)otherClock));
		this.d = new Date(otherClock.d);
	}

	public void advanceTime(){
		minute += 1;
		checkTime();
		if(((AMclock)this).equals(new AMclock("12:00 AM"))){
			d.advanceDate();
		}
	}
	public void advanceDate(){
			d.advanceDate();
	}

}
