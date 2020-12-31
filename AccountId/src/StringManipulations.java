import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.jayway.jsonpath.JsonPath;

public class StringManipulations {

	public static void main(String args[])
	{
		String filter = "[\"base_plan\"]";
		
		Set errors = new HashSet();
		//errors.add(new StringManipulations());
		System.out.println(errors.isEmpty());
		String error ="Request.dataset.param: does not contain an element that passes these validations: {\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\",\"pattern\":\"^order_id$\"},\"value\":{\"type\":\"string\",\"pattern\":\"[^ ,null]\",\"minLength\":1}},\"required\":[\"id\",\"value\"]}\r\n" + 
				"";
		//System.out.println(error.indexOf("{"));
		if(error.contains("pattern"))
		{
		//System.out.println(JsonPath.read(error.substring(error.indexOf("{")),"$.properties.id.pattern" ).toString());
		}
		
		//System.out.println(filter);
		if (filter!=null) {
			if (filter.startsWith("[\"") && filter.endsWith("\"]"))
				filter = filter.substring(2, filter.length() - 2);
			//System.out.println(filter);
		}
		String eventType="hlrRetry";
		if(eventType.toLowerCase().contains("retry"))
			System.out.println("contains");
		else
			System.out.println("doesnt");
	}
	public static boolean isNotNull(Collection<?> field) {

		return field != null && !field.isEmpty();
	}
	public static boolean checkWhiteSpace(String field) {
		if(field!=null && !field.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
