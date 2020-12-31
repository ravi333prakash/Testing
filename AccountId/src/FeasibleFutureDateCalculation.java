import java.util.HashMap;

import org.joda.time.LocalDate;

public class FeasibleFutureDateCalculation {

	public static void main(String[] args) {
		
		String billCycleDay="26"; //what we get from BS fetchaccount details response -"bill_cycle_day"
		int daysCount=7;   //7day policy
		String futureExecutionDate = null;
		LocalDate currentDate = LocalDate.now();

		HashMap<String,String> map= new HashMap<String,String>();
		map.put("SEVEN_DAYS_TERMINATION_REQD","true");
		
		LocalDate billingDate=getBillingDate(billCycleDay);
		if(map.get("SEVEN_DAYS_TERMINATION_REQD")!=null && Boolean.parseBoolean(map.get("SEVEN_DAYS_TERMINATION_REQD"))){
			// 7 days termination policy
			
			System.out.println(" | Calcuating termination date by "
					+ daysCount + " days termination policy");
			if (currentDate.isBefore(billingDate.minusDays(daysCount))) {
				futureExecutionDate = billingDate.minusDays(1).toString();
				// LocalDate.parse(nextBillDateLocal.toString(),
				// DateTimeFormat.forPattern("yyyy-MM-dd"))
				// .toString();
			} else {
				futureExecutionDate = billingDate.plusMonths(1).minusDays(1).toString();
				// futureExecutionDate = LocalDate
				// .parse(nextBillDateLocal.plus(new Period().withMonths(1)).toString(),
				// DateTimeFormat.forPattern("yyyy-MM-dd"))
				// .toString();
			}
		} else {

			// end of bill cycle termination policy - termination should happen always at
			// the end of bill cycle
			System.out.println( " | Calculating termination date by end of bill cycle policy");
			futureExecutionDate = billingDate.minusDays(1).toString();
			// futureExecutionDate = LocalDate
			// .parse(nextBillDateLocal.toString(), DateTimeFormat.forPattern("yyyy-MM-dd"))
			// .toString();
		}
System.out.println("futureExecutionDate "+futureExecutionDate);
	}
	
	private static LocalDate getBillingDate( String billCycleDay) {
		LocalDate billDate = null;
		LocalDate currentDate = null;
		try {
			if (CommonUtil.validateField(billCycleDay) && Integer.parseInt(billCycleDay.trim()) > 0
					&& Integer.parseInt(billCycleDay.trim()) < 32) {
				currentDate = LocalDate.now();

				if (new LocalDate().withDayOfMonth(Integer.parseInt(billCycleDay.trim())).isBefore(currentDate)
						|| new LocalDate().withDayOfMonth(Integer.parseInt(billCycleDay.trim())).isEqual(currentDate)) {
									System.out.println("|"
							+ " | bill generation for this month is already happend. taking billing date as the next months date");
					billDate = new LocalDate().withDayOfMonth(Integer.parseInt(billCycleDay.trim())).plusMonths(1);
					System.out.println("|" + " | bill date " + billDate);

				} else {
					System.out
							.println("|" + " | bill generation is not done for this month.");
					billDate = new LocalDate().withDayOfMonth(Integer.parseInt(billCycleDay.trim()));
					System.out.println("|" + " | bill date " + billDate);
				}
			} else {
				System.out.println("|" + " Invalid bill cycle day " + billCycleDay);
			}

		} catch (Exception e) {
			 e.printStackTrace() ;
		}
		return billDate;
		
	}

}
