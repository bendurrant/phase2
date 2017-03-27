package cs5530;



public class Reservation {
	TH th;
	
	public java.sql.Date start;
	public java.sql.Date stop;
	
	
	public int pid;//manually set and got only sometimes used 
	//public Availability conatiner;//the availability this reservation is held in
 public Reservation(TH th,java.sql.Date start,java.sql.Date stop)
 {
	 this.th=th;
	 this.start=start;
		this.stop=stop;
		//this.conatiner=container;
		pid=-1;
 }
 
 public String toString()
 {
		 return "Reservation for TH with id: "+th.thid+" From: "+start.toString()+" To: "+stop.toString();
 }
}
