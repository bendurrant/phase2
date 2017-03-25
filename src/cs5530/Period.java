package cs5530;
import java.sql.Date;
import java.util.Calendar;

public class Period {
	
	public Calendar start;
	public Calendar stop;
	public Period(Calendar start,Calendar stop)
	{
		this.start=start;
		this.stop=stop;
	}

	public  Date getSqlDate(boolean useStart)
	 {
		if(useStart)
		{
			return new java.sql.Date(start.getTimeInMillis());
		}else
		{
			return new java.sql.Date(stop.getTimeInMillis());
		}
		 
	 }
}
