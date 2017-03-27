package cs5530;

public class Availability {
	Period  period;
	int pid;
	public Availability(Period period,int pid)
	{
	this.period=period;
	this.pid=pid;
	}
	
	public String ToString()
	{
		return "Start: "+period.start.toString()+" Stop: "+period.stop.toString();
	}
}
