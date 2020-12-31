import java.util.HashMap;
import java.util.Map;

public class CriteriaListPNINV {

	public static void main(String[] args) {
		Boolean s= Boolean.parseBoolean("    true".trim());
		//System.out.println(s);
		String []ss= {"1","2"};
		Map<String, Object> kiMap=null;
		kiMap=new HashMap<String, Object>();
		kiMap.put("ki","true");
		if(Boolean.parseBoolean(kiMap.get("ki").toString()))
				{
			//System.out.println("has");
				}
		String ssa= "100,101,102,104,105,";
		StringBuilder sb= new StringBuilder();
		
		sb.append("PTTID").append(",").append("QOS").append("APNID");
		if(sb.toString().contains("PTTI"))
		System.out.println();
		
		if(!Boolean.parseBoolean("false"))
		{
			System.out.println("hasd");
		}
		/*String []ass=sb.toString().split(",");*/
		/*for(String jad: ass)
		{
			if(!jad.equalsIgnoreCase("9")) {
				
				System.out.println("yes");
			break;
			}
		}*/
		
		
		
		/*String[] a=ssa.split(",");
		for (String h:a)
		{
			if(h.equalsIgnoreCase("105"))
			{
				System.out.println("100");
				break;
				
			}
			else if(h.equalsIgnoreCase("101"))
			{
				System.out.println("101");
				break;
			}
			else
			{
				System.out.println("jsa");
				
			}
		}*/
		
		
		
		

	}

}
