import java.util.Calendar;
import java.util.GregorianCalendar;


public class DateAndTime 
{

	public static void main(String[] args) 
	{
		int day, month, year ;
		int second, minute, hour;
		
		try
		{
	
		
			for (int x=0;    x<10;   x++)
			{
				GregorianCalendar date  = new GregorianCalendar ( ) ;
				
				day = date.get(Calendar.DAY_OF_MONTH);	
				month = date.get(Calendar.MONTH);
				year = date.get(Calendar.YEAR);
				
				second = date.get(Calendar.SECOND) ;
				minute = date.get(Calendar.MINUTE) ;
				hour = date.get(Calendar.HOUR_OF_DAY); 
				
				
				System.out.print(day + "/" + month + "/" + year);
				System.out.print("      " + hour + ":" + minute + ":" + second); 
				
				System.out.println() ; 
				
				
				Thread.sleep(1000); 
			}
		} 
		catch  (Exception e)
		{
			System.out.println("Error occured") ; 
		}

	}
}
