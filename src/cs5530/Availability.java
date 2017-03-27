package cs5530;

public class Availability {
	Period  period;
	int pid;
	int price;
	
	
	public Availability(Period period,int pid,int price)
	{
	this.period=period;
	this.pid=pid;
	this.price = price;
	}
	
	public String ToString()
	{
		return "Start: "+period.start.toString()+" Stop: "+period.stop.toString()+" Estimated price per night per person: "+price;
	}
}
