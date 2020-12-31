import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.joda.time.format.DateTimeFormat;

public class LocalDatePrac {

	public static void main(String[] args) throws ParseException {	
		// TODO Auto-generated method stub
		long duration = 5;
		long stopTime = 0;
		long currentTime = 0;
	//	System.out.println(duration);
		String [] serviceProfileFeatures= {"1","2"};
		
		duration = (duration * 60) * 1000;
	//	System.out.println(duration);
		currentTime = System.currentTimeMillis();
		System.out.println(System.currentTimeMillis());
		/*stopTime = currentTime + duration;
		
		System.out.println(stopTime);
		for (String feature : serviceProfileFeatures) {
			if(feature.equalsIgnoreCase("2")) {
				System.out.println("2");
				break;}
			
			}
		//  "cos": "A", "roam": "02"
		
		String vas="\"cos\": \"A\", \"roam\": \"02\"";
		String s="a";
		if(!vas.startsWith(","))
		{
			System.out.println("vas true  05082020022631");

		}*/
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse("2020-08-05"));

		String sa="active_suspend";
		String sq="active_suspend";
		if(Arrays.asList(sa.split(",")).contains(sq))
		System.out.println("saj");
		
		System.out.println(DateTimeFormat.forPattern("ddMMyyyyHHmmss").print(System.currentTimeMillis()));
	}

}
