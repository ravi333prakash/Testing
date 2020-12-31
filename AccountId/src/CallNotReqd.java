import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CallNotReqd {

	public static void main(String[] args) {
		String fromRequest="jiinvoice_PTRestrore_restore".toUpperCase();
		//System.out.println(fromRequest);
		String callNotReqdType= "ji".toUpperCase()+"invoice_PTRestrore_Restore,invoice_PTRestrore_Active".trim().toUpperCase();
		//System.out.println(callNotReqdType);
		Set<String>callNotReqdTypeSet= new HashSet<>(Arrays.asList(callNotReqdType.split(",")));
		if(callNotReqdTypeSet.contains(fromRequest))
		{
	//	System.out.println("s");
		}
	//	System.out.println(System.currentTimeMillis());
		/*Set<String> hash_Set = new HashSet<String>(); 
        hash_Set.add("Geeks"); 
        hash_Set.add("For"); 
        hash_Set.add("Geeks"); 
        hash_Set.add("Example"); 
        hash_Set.add("Set"); 
        System.out.print("Set output without the duplicates"); 
  
        System.out.println(hash_Set); 
       System.out.println( hash_Set.toString());*/
		HashMap kiMap=new HashMap();
		kiMap.put("pttCallReqd", "true");
		//kiMap.remove("pttCallReqd");
		/*System.out.println(kiMap.containsKey("pttCallReqd"));
		System.out.println(kiMap.get("pttCallReqd"));*/
		

		//if(kiMap.get("pttCallReqd")== null || !Boolean.parseBoolean(kiMap.get("pttCallReqd").toString()))
		//System.out.println("sj");
		/*if (strAttrMap.containsKey("userId")) {
			_logger.info("userid:"+userId);
			strAttrMap.remove("userid");
		}
		genericDTO.getStrAttributes().putAll(strAttrMap);
		genericDTO.getAttributes().putAll(attrMap);
		_logger.info(genericDTO.getTransactionId() + " | feature after merging genericDTO | "
				+ genericDTO.getStrAttributes().get(GenericConstants.key_feature));

	}*/
	/*
	 if(!userId.equalsIgnoreCase("SOFTACT_UPD")) {
	  genericDTO.getStrAttributes().put(GenericConstants.key_userid,userId); }
		*/
		StringBuilder sb= new StringBuilder();
		sb.append("\"resultParam\": {");
		sb.append("\"resultCode\" : \"SC0000\",");
		sb.append("\"resultDescription\": \"Fetch Plan Success\"");
		sb.append("},");
		sb.append("\"subscription_parameters\":");
		System.out.println(sb);
		
		/*System.out.println(kiMap.get("error_code"));
		if(kiMap.get("error_code")!=null && "sah".equalsIgnoreCase(kiMap.get("error_code").toString()) && kiMap.get("pttCallReqd")==null || !Boolean.valueOf(kiMap.get("pttCallReqd").toString()))
			System.out.println("sakn");*/
		
		
		String invoice_ids="1";
		String credit_memo="1,2";
		int inv1=0;
		int cre1=0;
		for(String  inv:invoice_ids.split(",") )
		{
			//inv1++;
			//System.out.println("inv "+inv1);
			for(String cre: credit_memo.split(","))
			{
				cre1++;
				System.out.println("cre "+cre1);
			}
		}
	}
}



