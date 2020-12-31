import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.joda.time.DateTimeComparator;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class newQuota {

	public static void main(String[] args) {
	/*	LocalDateTime currentTime=LocalDateTime.now();
		System.out.println(currentTime.plusDays(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toDate());
		LocalDate ld = new LocalDate();
		System.out.println(ld.plusDays(1));*/
		int maxwaitingdays=3;
		Boolean inRange=false;
		LocalDate firstSubmissionDate = new LocalDate();
		firstSubmissionDate=firstSubmissionDate.plusDays(3);
		LocalDate quotaDate=new LocalDate().plusDays(8);
		System.out.println("firstSubmissionDate "+firstSubmissionDate);
		System.out.println("quotaDate " +quotaDate);
		StringBuilder weekdays= new StringBuilder();
		
		if(firstSubmissionDate.getDayOfWeek()==6)
		{
			firstSubmissionDate=firstSubmissionDate.plusDays(2);
			System.out.println("sat so "+firstSubmissionDate);
			weekdays.append("Dummy").append(",");
		}
		if(firstSubmissionDate.getDayOfWeek()==7)
		{
			firstSubmissionDate=firstSubmissionDate.plusDays(1);
			System.out.println("sun so "+firstSubmissionDate);
			weekdays.append("Dummy").append(",");
		}
		
		
		weekdays.append(firstSubmissionDate).append(",");
		System.out.println(weekdays);
		for(int i =0;i<50;)
		{
			firstSubmissionDate=firstSubmissionDate.plusDays(1);
			if(firstSubmissionDate.getDayOfWeek()!=6 && firstSubmissionDate.getDayOfWeek()!=7 ) {
				i++;
			weekdays.append(firstSubmissionDate).append(",");
			}
			
			
		}
		System.out.println(weekdays);
		
		String[] weekdaysSArr=weekdays.toString().split(",");
		for(int i=0;i<=maxwaitingdays;i++)
		{
			if(quotaDate.toString().equalsIgnoreCase(weekdaysSArr[i]))
			{
				System.out.println("In range ");
				inRange=true;
			}
		}
	if(!inRange && quotaDate.getDayOfWeek()!=6 &&  quotaDate.getDayOfWeek()!=7) {
			System.out.println("Out of Range");
			
			
		}
	
		
		
		System.out.println(LocalDateTime.now());
		
		
		
	}

}
