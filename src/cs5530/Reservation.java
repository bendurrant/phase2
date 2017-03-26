package cs5530;
import java.text.SimpleDateFormat;



public class Reservation {
	TH th;
	
	public java.sql.Date start;
	public java.sql.Date stop;
 public Reservation(TH th,java.sql.Date start,java.sql.Date stop)
 {
	 this.th=th;
	 this.start=start;
		this.stop=stop;
 }
 
 public String ToString()
 {
	//"EEE, MMM d, 'yyyy"                ->>  Wed, July 10, 1996
	 SimpleDateFormat format1 = new SimpleDateFormat("EEE, MMM d, yyyy" );
	 //return "Reservation for TH with id: "+thid+" From: "+format1.format(period.start.getTime()+" To: "+format1.format(period.stop.getTime()));
	 return "Reservation for TH with id: "+th.thid+" From: "+start.toString()+" To: "+stop.toString();
 }
}
