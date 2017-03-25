package cs5530;
import java.text.SimpleDateFormat;



public class Reservation {
	int thid;
	Period period;

 public Reservation(int thid,Period period)
 {
	 this.thid=thid;

 }
 
 public String ToString()
 {
	//"EEE, MMM d, 'yyyy"                ->>  Wed, July 10, 1996
	 SimpleDateFormat format1 = new SimpleDateFormat("EEE, MMM d, yyyy" );
	 return "Reserving TH with id: "+thid+" From: "+format1.format(period.start.getTime()+" To: "+format1.format(period.stop.getTime()));
 }
}
