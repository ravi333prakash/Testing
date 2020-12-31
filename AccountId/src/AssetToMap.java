import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;

public class AssetToMap {

	public static void main(String[] args) throws ParseException {
		
		
/*		StringBuilder inventoryAssets = new StringBuilder();
		Set<String> hash_Set = new HashSet<String>();
		inventoryAssets.append("1263847628321").append(",");
		inventoryAssets.append("232873").append(",");
		inventoryAssets.append("32837").append(",");
		inventoryAssets.append("4233").append(",");
       //System.out.println(inventoryAssets);
       String [] assetsIntoMap=inventoryAssets.toString().split(",");
       for(String s: assetsIntoMap)
       {
    	   hash_Set.add(s);
    
       }
       System.out.println(hash_Set);
       
     String s=  hash_Set.toString().substring(1,hash_Set.toString() .length()-1).replaceAll(" ","");
     System.out.println(s);
     
     String[] as = s.split(",");
		for (int i = 0; i < as.length; i++) {
     System.out.println(as[i]);
     if(as[1].trim().equalsIgnoreCase("2"))
    	 System.out.println("true");
	}

	}
    for(int i=0; i<4;) {
    	
    	if(i!=4)
    	{
    		i++;
    		System.out.println("incre");
    	}
    	else
    	{
    		System.out.println("dec");
    	}
    	
    }
    StringBuilder sb= new StringBuilder();
	
	
		sb.append("{\"account\": {");
		sb.append("\"show_profile\":\"true\",");
		sb.append("\"account_id\":").append("\"").append("119028").append("\"}");
		sb.append("}");
		System.out.println("sb "+sb.toString());*/

SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
Calendar c = Calendar.getInstance();
System.out.println(c);
c.setTime(sdf2.parse("2020-07-31"));
c.add(Calendar.DAY_OF_MONTH, 1);
String newDate = sdf2.format(c.getTime());
/*System.out.println("newDate "+newDate);

String s="completed";
List <String> al=Arrays.asList(s.split(","));
System.out.println("ArrayList "+al);
String mnp_owner_name="mathew nibin";
String profileName="NIBIN MATHEW";
String Reversname="MATHEW NIBIN";
if (mnp_owner_name != null && mnp_owner_name.equalsIgnoreCase(profileName)
|| mnp_owner_name != null
		&& mnp_owner_name.equalsIgnoreCase(Reversname)) {


System.out.println("Main loop is executed");
}
String [] sa= {"0","1","2"};
List l=Arrays.asList("".split(","));
System.out.println(l);
if(l!=null && !l.get(0).toString().isEmpty())
{
	System.out.println("sa not null");
}else
{
	System.out.println("sa is null");
}*/
System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse("06082020025345"));
	}
}




