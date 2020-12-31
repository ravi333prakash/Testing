import java.util.Collection;

public class CommonUtil {


	public static boolean isNotNullNotEmpty(String field) {

		return validateField(field);
	}

	public static boolean isNotNull(String field) {

		return validateFieldandEmpty(field);
	}

	public static boolean isNotNull(Collection<?> field) {

		return field != null && !field.isEmpty();
	}

	public static boolean isNotNull(Object field) {

		return field != null;
	}

	public static boolean validateField(String field) {

		if (field != null && field.length() != 0 && !field.equalsIgnoreCase("NULL")) {
			return true;
		}

		return false;

	}
	public static boolean checkWhiteSpace(String field) {
		if(field!=null && !field.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean validateFieldandEmpty(String field) {

		if (field != null && !field.equalsIgnoreCase("NULL")) {
			return true;
		}

		return false;
	}

}
