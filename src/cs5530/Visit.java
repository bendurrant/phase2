package cs5530;

public class Visit {
public int thid;
public int pid;

public java.sql.Date start;
public java.sql.Date stop;


public Visit(int thid,java.sql.Date start,java.sql.Date stop,int pid)
{
	this.start=start;
	this.stop=stop;
	this.thid=thid;
	this.pid=pid;
}
	
	public String toString()
	{
		return "TH: "+thid+" From: "+start.toString()+" To: "+stop.toString();
	}
	
}
