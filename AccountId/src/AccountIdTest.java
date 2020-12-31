import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import com.google.gson.JsonArray;

public class AccountIdTest {
	
	public static String s;
	public static void main(String[] args) {
		
	String subs_change_date="22052020000000";
	System.out.println(LocalDate.parse(subs_change_date,DateTimeFormat.forPattern("ddMMyyyyHHmmss")).plusDays(1).toDate());
	System.out.println(LocalDate.now());
	
	JsonArray jar=new JsonArray();
	System.out.println(jar);
if(jar!=null && jar.isJsonObject())
		System.out.println("yes");
String feature="feature";
String key ="key";
System.out.println( "schemas/" + feature + "/" + key + ".json");
	
		
		

	}

}
