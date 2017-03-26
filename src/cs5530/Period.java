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
