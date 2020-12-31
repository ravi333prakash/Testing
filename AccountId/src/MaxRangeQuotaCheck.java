import java.util.Date;

import org.joda.time.DateTimeComparator;
import org.joda.time.LocalDateTime;

public class MaxRangeQuotaCheck {

	public static void main(String[] args) {
		
		int maxRange=4;
		Boolean inRange=false;
		LocalDateTime SubmissionDate = LocalDateTime.now();
		LocalDateTime quotaDate = new LocalDateTime(SubmissionDate).withHourOfDay(9);
		//quotaDate=quotaDate.minusDays(1);
		Date quotaDateinDate=quotaDate.toDate();
		//System.out.println("date format = "+SubmissionDate.toString());
		//System.out.println("LocalDateTime = "+quotaDate+" date = "+quotaDateinDate);
		//System.out.println("date = "+quotaDateinDate);
	//	System.out.println(quotaDate.toDate());
		System.out.println(LocalDateTime.now());
		System.out.println(quotaDate.withHourOfDay(20).toDate() + " ");
		System.out.println(DateTimeComparator.getDateOnlyInstance().compare(quotaDate.withHourOfDay(20).toDate(), SubmissionDate));
		//returns 0 if only equal
	//	if(quotaDate.withHourOfDay(20).toDate()==SubmissionDate)
		{
			System.out.println("today Date");
		}
	//	System.out.println(quotaDate+" "+LocalDateTime.now());
		if(quotaDate.equals(LocalDateTime.now()))
		{
			System.out.println("today time ");
		}
		String [] weekDays= {"Mon","Tue","Wed","Thu","Fri","Mon","Tue","Wed","Thu","Fri","Mon","Tue","Wed","Thu","Fri"};
		
//if todays quota available no need to go inside for, directly put quota available 		
		if(DateTimeComparator.getDateOnlyInstance().compare(LocalDateTime.now().minusDays(1).toDate(), LocalDateTime.now().toDate())==-1)
		{
			System.out.println(" yufyu same day");
		}
		
		System.out.println(new LocalDateTime(quotaDateinDate));
		
		
		for (int i=0;i< weekDays.length;i++)
		{
			if("Fri".contains(weekDays[i]))
			{
				for(int j=i;j<=i+maxRange;j++)
				{
					if(quotaDate.toDate().toString().contains(weekDays[j]) && i!=j)
					{
						System.out.println(i+" "+j);
						System.out.println(weekDays[j]);
						System.out.println("In range "+quotaDateinDate);
						inRange=true;
						break;
					}
					
				}
				if(!inRange && !quotaDate.toDate().toString().contains("Sat") && !quotaDate.toDate().toString().contains("Sun")) {
				System.out.println("Out of Range");
				System.out.println("Quota =null ");
				quotaDate=null;
				}
				break;
			}
			
		}
		System.out.println(quotaDate);
		
		

	}

}
