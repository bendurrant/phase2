package cs5530;

import java.util.ArrayList;

public class User {
	public String login;
	public String password;
	public boolean isAdmin;
	
	ArrayList<Reservation> reservations;
	public User(String login, String password, boolean isAdmin)
	{
		//UserType is whether the user is admin or not
		this.login = login;
		this.password = password;
		this.isAdmin = isAdmin;
		reservations=new ArrayList<Reservation>();
	}
	
	public void addReservation(TH th,java.sql.Date start,java.sql.Date stop)
	{
		reservations.add(new Reservation(th,start,stop));
	}
}
