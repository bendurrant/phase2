package cs5530;
import java.sql.Date;
import java.util.Calendar;

public class Period {
	
	public java.sql.Date start;
	public java.sql.Date stop;
	public Period(java.sql.Date start,java.sql.Date stop)
	{
		this.start=start;
		this.stop=stop;
	}
	public Period() {
		// TODO Auto-generated constructor stub
		this.start=null;
		this.stop=null;
	}

	
	public boolean contains(Period p)//returns whether a given period fully contains another 
	{
		boolean startWithinOrEqual=!(p.start.before(start) || p.start.after(stop));//starts are equal or or p is within this  
		boolean stopWithinOrEqual=!(p.stop.before(start) || p.stop.after(stop));
		
		return startWithinOrEqual&&stopWithinOrEqual;
	}
	
	public boolean isValid()//returns whether this period is valid in order
	{
		return start.before(stop);
	}
	
//	public  Date getSqlDate(boolean useStart)
//	 {
//		if(useStart)
//		{
//			return new java.sql.Date(start.getTimeInMillis());
//		}else
//		{
//			return new java.sql.Date(stop.getTimeInMillis());
//		}
//		 
//	 }
}
