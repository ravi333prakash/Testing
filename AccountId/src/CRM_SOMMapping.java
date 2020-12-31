import java.util.Arrays;
import java.util.List;

public class CRM_SOMMapping {
	
	
	public static void main(String args [])
	{
		String CRMTags="a,b,c"; 
		String SomTags="A,B,C"; 
		List SOMArray=null;
		List CRMArray=null;
		for(int i=0;i<SomTags.split(",").length;i++ )
		{
			SOMArray=Arrays.asList(SomTags.split(","));
			CRMArray=Arrays.asList(CRMTags.split(","));
			
			System.out.println(SOMArray.get(i));
			System.out.println(CRMArray.get(i));
			
			System.out.println("put "+CRMArray.get(i)+":"+SOMArray.get(i));

		}
		
	}
	

}
