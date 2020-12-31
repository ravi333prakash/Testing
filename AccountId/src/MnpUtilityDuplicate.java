import java.util.Date;

import org.joda.time.DateTimeComparator;
import org.joda.time.LocalDateTime;


public class MnpUtilityDuplicate {

	public static void main(String[] args) {
		
		
		
		
	}
	
	public LocalDateTime findPortInSubmissionWindow( LocalDateTime currentDateTime) {
		
		LocalDateTime portInWindowClosingTime = null;
		LocalDateTime portInWindowOpeningTime = null;
		LocalDateTime portinSubmissionWindow = null;
		LocalDateTime quotaDate = null;
		LocalDateTime firstSubmissionDate=null;
		int portInWindowClosingHour = 20;
		int portInWindoOpeningHour = 9;

	System.out.println(  " |  New Date to be Checked " + currentDateTime);
	
	portInWindowClosingTime = new LocalDateTime().withDayOfMonth(currentDateTime.getDayOfMonth())
			.withHourOfDay(portInWindowClosingHour).withMinuteOfHour(0).withSecondOfMinute(0)
			.withMillisOfSecond(0);

	portInWindowOpeningTime = new LocalDateTime().withDayOfMonth(currentDateTime.plusDays(1).getDayOfMonth())
			.withHourOfDay(portInWindoOpeningHour).withMinuteOfHour(0).withSecondOfMinute(0)
			.withMillisOfSecond(0);
	System.out.println( " | Port In Window Closing Time " + portInWindowClosingTime);
	System.out.println(" | Port In Window Opening Time " + portInWindowOpeningTime);
	
	
	if (6 == currentDateTime.getDayOfWeek() || 7 == currentDateTime.getDayOfWeek()) {
		System.out.println(
				" | port in is not possible at this time. day is saturday/sunday. checking for available time slot");
		System.out.println("portinSubmissionWindow:" + currentDateTime.getDayOfMonth());
		currentDateTime = currentDateTime.plusDays(1);
		System.out.println("incremented since sat/sun " + currentDateTime);
		portinSubmissionWindow=currentDateTime;
	}
	
	else if ((currentDateTime.isAfter(portInWindowClosingTime)
			|| currentDateTime.isEqual(portInWindowClosingTime))
			&& currentDateTime.isBefore(portInWindowOpeningTime)) {
		System.out.println(
				" | port in is not possible at this time.port in submission window is closed. checking for available time slot");
		portinSubmissionWindow = new LocalDateTime().withDayOfMonth(currentDateTime.plusDays(1).getDayOfMonth())
				.withHourOfDay(9).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);//make suppose monday time exceeded, make submission date as tuesday 9 am

		if (6 == portinSubmissionWindow.getDayOfWeek() || 7 == portinSubmissionWindow.getDayOfWeek()) {
			System.out.println(
					 " | port in is not possible at this time. day is saturday/sunday. checking for available time slot");
			if(quotaDate ==null) {
				quotaDate=portinSubmissionWindow;
				}
			portinSubmissionWindow = findPortInSubmissionWindow( quotaDate);
			System.out.println("portinSubmissionWindow:" + portinSubmissionWindow);
		}
	}
	else {
		portinSubmissionWindow = currentDateTime;
		System.out.println("portinSubmissionWindow:" + portinSubmissionWindow);

	}
	
	if(!portinSubmissionWindow.isBefore(firstSubmissionDate)) {
	//	quotaDate = new LocalDateTime(checkPortInQuota( portinSubmissionWindow.toDate(),firstSubmissionDate.toDate()));
		System.out.println("quotaDate returned :" + quotaDate);
			}

	if (DateTimeComparator.getDateOnlyInstance().compare(quotaDate.toDate(), firstSubmissionDate.toDate())== -1) {
		System.out.println( " | quota is not available for the operator ");
	} else {
		System.out.println("mnp_quota_available true");
		if (6 == quotaDate.getDayOfWeek() || 7 == quotaDate.getDayOfWeek()) {
			System.out.println( 
					" | port in is not possible at this time. day is saturday/sunday. checking for available time slot");

			portinSubmissionWindow = findPortInSubmissionWindow( quotaDate);
		} else {
			portinSubmissionWindow = quotaDate;
		}

	}
	return portinSubmissionWindow;
	
	}
	
/*	public Date checkPortInQuota( Date submissionDate,Date firstSubmissionDate) {
		
		
		
	}*/
	}


