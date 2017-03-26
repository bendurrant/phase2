package cs5530;
import java.util.ArrayList;
public class Session {

	User user;
	ArrayList<Reservation> reservations;
	public Session(User user)
	{
		this.user=user;
		reservations=new ArrayList<Reservation>();
	}
	
	public void addReservation(int thid,Period period)
	{
		//reservations.add(new Reservation(thid,period));
	}
}
