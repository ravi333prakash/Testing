import org.joda.time.LocalDateTime;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
//import java.time.LocalDateTime;
import java.util.Date;
public class QuotaCheck {
	
	
	public static void main(String[] args) {
		

	    
		
		
	}
	
	public static LocalDate addDaysSkippingWeekends(LocalDate date, int days) {
	    LocalDate result = date;
	    int addedDays = 0;
	    while (addedDays < days) {
	        result = result.plusDays(1);
	        if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
	            ++addedDays;
	        }
	    }
	    return result;
	}


		
	}


