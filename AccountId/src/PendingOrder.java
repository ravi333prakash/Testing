import java.util.Arrays;

public class PendingOrder {

	
	public static void main(String[] args) {
		
		
		System.out.println(validatePendingOrder());
		
		//System.out.println(orderFromDependancyDB);
		
		//System.out.println(orderStatus);
		
		//System.out.println(orderFromDependancyDBArr.length);

		/*for (int i = 0; i < orderFromDependancyDBArr.length; i++) 
		{
			System.out.println(orderFromDependancyDBArr[i]);
			System.out.println(orderFromDependancyDBArr.length);
		}
		*/
	String config="CreateOrderTetra,LifeCycleChangeTetra,AddSubscription";
	String processId="LifeCycleChangeTetra";
	if(Arrays.asList(config.split(",")).contains(processId))
	{
		processId=processId.replaceFirst("Tetra", "").trim();
		System.out.println(processId);
	}
		
		

	}
	public static boolean validatePendingOrder()
	{
		String  orderFromDependancyDB= "MNPPortOut|LifeCycleChange";
		String [] orderFromDependancyDBArr=orderFromDependancyDB.trim().split("\\|");
		String  statusFromDependancyDB= "Pending|Pending,InProgress,In-Progress";
		String [] statusFromDependancyDBArr=statusFromDependancyDB.split("\\|");
		//System.out.println(statusFromDependancyDB);
		//System.out.println("*********");
		//System.out.println();
		String orderType="LifeCycleChange";
		//System.out.println(orderType);
		String orderStatus="InProgress".toUpperCase();
		String[]b;
	for (int i = 0; i < orderFromDependancyDBArr.length; i++) 
	{
		//System.out.println("line number 20");
		if (orderFromDependancyDBArr[i].toUpperCase().equalsIgnoreCase(orderType)) 
		{
			//System.out.println("line number 22");
			for (int j = 0; j<statusFromDependancyDBArr.length; j++) 
			{
			//System.out.println("line number 24");
				b=statusFromDependancyDBArr[i].split(",");
				//System.out.println(b.length);
				for(int k =0;k<b.length;k++)
				{
					
					//System.out.println(b[k]);
					if(b[k].equalsIgnoreCase(orderStatus))
					{
						if("200".equalsIgnoreCase("200"))
						{
					System.out.println("orderType "+orderType+ " is pending so break the flow");
					return false;
						}
				 	
					}	
					
				}
				
			}
			

		}
	}
return true;
	}

}
