import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;

public class BundleParsing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String bundleFromOCSStr="{\"eligibilityInfo\": [{\r\n" + 
				"        \"bundleId\": \"RAVI 111\",\r\n" + 
				"        \"subscription_id\": \"100014229510100705157184150910\",\r\n" + 
				"        \"bundleStatus\": \"A\",\r\n" + 
				"        \"startDate\": \"2019-10-23 14:38:29\",\r\n" + 
				"        \"expiryDate\": \"2020-10-23 17:38:08\",\r\n" + 
				"        \"entitlementSize\": \"0\",\r\n" + 
				"        \"buckets\": [{\r\n" + 
				"                \"bucketId\": \"CMPrepaidCash\",\r\n" + 
				"                \"remaining\": \"923600000000\",\r\n" + 
				"                \"unit\": \"BYTES\",\r\n" + 
				"                \"usage\": \"0\",\r\n" + 
				"                \"used\": \"0\",\r\n" + 
				"                \"size\": \"0\",\r\n" + 
				"                \"totalOffering\": \"0\"\r\n" + 
				"            }, {\r\n" + 
				"                \"bucketId\": \"CMHomeSMSDefaultTariff\",\r\n" + 
				"                \"remaining\": \"-\",\r\n" + 
				"                \"unit\": \"BYTES\",\r\n" + 
				"                \"usage\": \"0\",\r\n" + 
				"                \"used\": \"0\",\r\n" + 
				"                \"size\": \"-\",\r\n" + 
				"                \"totalOffering\": \"-\"\r\n" + 
				"            }, {\r\n" + 
				"                \"bucketId\": \"CMHomeDataDefaultTariff\",\r\n" + 
				"                \"remaining\": \"-\",\r\n" + 
				"                \"unit\": \"GB\",\r\n" + 
				"                \"usage\": \"0\",\r\n" + 
				"                \"used\": \"0\",\r\n" + 
				"                \"size\": \"-\",\r\n" + 
				"                \"totalOffering\": \"-\"\r\n" + 
				"            }, {\r\n" + 
				"                \"bucketId\": \"CMHomeVoiceDefaultTariff\",\r\n" + 
				"                \"remaining\": \"-\",\r\n" + 
				"                \"unit\": \"-\",\r\n" + 
				"                \"usage\": \"0\",\r\n" + 
				"                \"used\": \"0\",\r\n" + 
				"                \"size\": \"-\",\r\n" + 
				"                \"totalOffering\": \"-\"\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    }, {\r\n" + 
				"        \"bundleId\": \"10076\",\r\n" + 
				"        \"subscription_id\": \"100014229510100765157184154410\",\r\n" + 
				"        \"bundleStatus\": \"B\",\r\n" + 
				"        \"startDate\": \"2019-10-23 14:39:04\",\r\n" + 
				"        \"expiryDate\": \"2019-10-24 14:39:04\",\r\n" + 
				"        \"entitlementSize\": \"700\",\r\n" + 
				"        \"buckets\": [{\r\n" + 
				"                \"bucketId\": \"CMHomeSMSTriTextUnlimited\",\r\n" + 
				"                \"remaining\": \"700\",\r\n" + 
				"                \"unit\": \"GB\",\r\n" + 
				"                \"usage\": \"0\",\r\n" + 
				"                \"used\": \"0\",\r\n" + 
				"                \"size\": \"700\",\r\n" + 
				"                \"totalOffering\": \"700\"\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    }, {\r\n" + 
				"        \"bundleId\": \"10076\",\r\n" + 
				"        \"subscription_id\": \"100014229510100765157184165110\",\r\n" + 
				"        \"bundleStatus\": \"A\",\r\n" + 
				"        \"startDate\": \"2019-10-23 14:40:51\",\r\n" + 
				"        \"expiryDate\": \"2019-10-24 14:40:51\",\r\n" + 
				"        \"entitlementSize\": \"700\",\r\n" + 
				"        \"buckets\": [{\r\n" + 
				"                \"bucketId\": \"CMHomeSMSTriTextUnlimited\",\r\n" + 
				"                \"remaining\": \"700\",\r\n" + 
				"                \"unit\": \"GB\",\r\n" + 
				"                \"usage\": \"0\",\r\n" + 
				"                \"used\": \"0\",\r\n" + 
				"                \"size\": \"700\",\r\n" + 
				"                \"totalOffering\": \"700\"\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    }, {\r\n" + 
				"        \"bundleId\": \"10076\",\r\n" + 
				"        \"subscription_id\": \"100014229510100765157184166110\",\r\n" + 
				"        \"bundleStatus\": \"A\",\r\n" + 
				"        \"startDate\": \"2019-10-23 14:41:01\",\r\n" + 
				"        \"expiryDate\": \"2019-10-24 14:41:01\",\r\n" + 
				"        \"entitlementSize\": \"700\",\r\n" + 
				"        \"buckets\": [{\r\n" + 
				"                \"bucketId\": \"CMHomeSMSTriTextUnlimited\",\r\n" + 
				"                \"remaining\": \"700\",\r\n" + 
				"                \"unit\": \"EVENTS\",\r\n" + 
				"                \"usage\": \"0\",\r\n" + 
				"                \"used\": \"0\",\r\n" + 
				"                \"size\": \"700\",\r\n" + 
				"                \"totalOffering\": \"700\"\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    }, {\r\n" + 
				"        \"bundleId\": \"10076\",\r\n" + 
				"        \"subscription_id\": \"100014229510100765157184167810\",\r\n" + 
				"        \"bundleStatus\": \"A\",\r\n" + 
				"        \"startDate\": \"2019-10-23 14:41:18\",\r\n" + 
				"        \"expiryDate\": \"2019-10-24 14:41:18\",\r\n" + 
				"        \"entitlementSize\": \"700\",\r\n" + 
				"        \"buckets\": [{\r\n" + 
				"                \"bucketId\": \"CMHomeSMSTriTextUnlimited\",\r\n" + 
				"                \"remaining\": \"700\",\r\n" + 
				"                \"unit\": \"EVENTS\",\r\n" + 
				"                \"usage\": \"0\",\r\n" + 
				"                \"used\": \"0\",\r\n" + 
				"                \"size\": \"700\",\r\n" + 
				"                \"totalOffering\": \"700\"\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    }, {\r\n" + 
				"        \"bundleId\": \"10076\",\r\n" + 
				"        \"subscription_id\": \"100014229510100765157184168210\",\r\n" + 
				"        \"bundleStatus\": \"A\",\r\n" + 
				"        \"startDate\": \"2019-10-23 14:41:22\",\r\n" + 
				"        \"expiryDate\": \"2019-10-24 14:41:22\",\r\n" + 
				"        \"entitlementSize\": \"700\",\r\n" + 
				"        \"buckets\": [{\r\n" + 
				"                \"bucketId\": \"CMHomeSMSTriTextUnlimited\",\r\n" + 
				"                \"remaining\": \"700\",\r\n" + 
				"                \"unit\": \"EVENTS\",\r\n" + 
				"                \"usage\": \"0\",\r\n" + 
				"                \"used\": \"0\",\r\n" + 
				"                \"size\": \"700\",\r\n" + 
				"                \"totalOffering\": \"700\"\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    }, {\r\n" + 
				"        \"bundleId\": \"RAVI LAST\",\r\n" + 
				"        \"subscription_id\": \"100014229510101145157184154810\",\r\n" + 
				"        \"bundleStatus\": \"Z\",\r\n" + 
				"        \"startDate\": \"2019-10-23 14:39:08\",\r\n" + 
				"        \"expiryDate\": \"2019-11-22 22:39:07\",\r\n" + 
				"        \"entitlementSize\": \"1073741824\",\r\n" + 
				"        \"buckets\": [{\r\n" + 
				"                \"bucketId\": \"CMHomeDataFacebookYoutube1GB\",\r\n" + 
				"                \"remaining\": \"1073741824\",\r\n" + 
				"                \"unit\": \"GB\",\r\n" + 
				"                \"usage\": \"0\",\r\n" + 
				"                \"used\": \"0\",\r\n" + 
				"                \"size\": \"1073741824\",\r\n" + 
				"                \"totalOffering\": \"1073741824\"\r\n" + 
				"            },\r\n" + 
				"			{\r\n" + 
				"                \"bucketId\": \"CMHomeDataFasssscebookYoutube1GB\",\r\n" + 
				"                \"remaining\": \"1073741824\",\r\n" + 
				"                \"unit\": \"Gb\",\r\n" + 
				"                \"usage\": \"0\",\r\n" + 
				"                \"used\": \"0\",\r\n" + 
				"                \"size\": \"1073741824\",\r\n" + 
				"                \"totalOffering\": \"1073741824\"\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    }\r\n" + 
				"]}\r\n" + 
				"";
		String data="BYTES";
		JsonObject responseObject=null;
		responseObject=new Gson().fromJson(bundleFromOCSStr, JsonObject.class);
		JsonArray bundleFromOCS=responseObject.getAsJsonObject().get("eligibilityInfo").getAsJsonArray();
		JsonArray techOfferReqBundle=new JsonArray();
		JsonArray technicalOfferBucket;
		JsonObject techOffBucketJobj;
		
		JsonArray newmainBundleArray=new JsonArray();
		JsonArray newmainBucketArray=null;
		JsonObject techOfferNewBundle=null;
		JsonObject techOfferNewBucket=null;
		
		
		for(int j=0;j<bundleFromOCS.size();j++)
		{
			
			
			newmainBucketArray=new JsonArray();
			techOfferNewBundle=new JsonObject();
			
			if(bundleFromOCS.get(j).getAsJsonObject().has("buckets") && bundleFromOCS.get(j).getAsJsonObject().get("buckets")!=null )
			{
				  technicalOfferBucket=bundleFromOCS.get(j).getAsJsonObject().get("buckets").getAsJsonArray();
				for (int i =0 ;i<technicalOfferBucket.size();i++)
				{
					
					 
					
				if(technicalOfferBucket.get(i).getAsJsonObject().has("unit") && technicalOfferBucket.get(i).getAsJsonObject().get("unit").getAsString().equalsIgnoreCase(data))
				{
					
					 techOffBucketJobj=new JsonObject();
					 
					 techOfferNewBucket=new JsonObject();
					  
					if(technicalOfferBucket.get(i).getAsJsonObject().has("bucketId")) {
						techOffBucketJobj.addProperty("id",technicalOfferBucket.get(i).getAsJsonObject().get("bucketId").getAsString());
						techOfferNewBucket.addProperty("bucketId",technicalOfferBucket.get(i).getAsJsonObject().get("bucketId").getAsString());

					}
					if(technicalOfferBucket.get(i).getAsJsonObject().has("bucketId")) {
						techOffBucketJobj.addProperty("name",technicalOfferBucket.get(i).getAsJsonObject().get("bucketId").getAsString());
						techOfferNewBucket.addProperty("bucketName",technicalOfferBucket.get(i).getAsJsonObject().get("bucketId").getAsString());

					}
					if(technicalOfferBucket.get(i).getAsJsonObject().has("remaining")) {
						techOffBucketJobj.addProperty("benefitValue",technicalOfferBucket.get(i).getAsJsonObject().get("remaining").getAsString());
						techOfferNewBucket.addProperty("benefitValue",technicalOfferBucket.get(i).getAsJsonObject().get("remaining").getAsString());

					}
					if(technicalOfferBucket.get(i).getAsJsonObject().has("unit")) {
						techOffBucketJobj.addProperty("benefitUnit",technicalOfferBucket.get(i).getAsJsonObject().get("unit").getAsString());
						techOfferNewBucket.addProperty("benefitUnit",technicalOfferBucket.get(i).getAsJsonObject().get("unit").getAsString());

					}
					if(bundleFromOCS.get(j).getAsJsonObject().has("bundleId")) {
						techOfferNewBundle.addProperty("bundleId", bundleFromOCS.get(j).getAsJsonObject().get("bundleId").getAsString());
					//	genericDTO.getStrAttributes().put("compBundleId", bundleFromOCS.get(j).getAsJsonObject().get("bundleId").getAsString());
					}
					if(bundleFromOCS.get(j).getAsJsonObject().has("bundleStatus"))
						techOfferNewBundle.addProperty("bundleStatus", bundleFromOCS.get(j).getAsJsonObject().get("bundleStatus").getAsString());
					if(bundleFromOCS.get(j).getAsJsonObject().has("startDate")) 
					{
						techOffBucketJobj.addProperty("startDate",bundleFromOCS.get(j).getAsJsonObject().get("startDate").getAsString());
						techOfferNewBundle.addProperty("startDate", bundleFromOCS.get(j).getAsJsonObject().get("startDate").getAsString());
					}
					if(bundleFromOCS.get(j).getAsJsonObject().has("expiryDate")) 
					{
						techOffBucketJobj.addProperty("expiryDate",bundleFromOCS.get(j).getAsJsonObject().get("expiryDate").getAsString());
						techOfferNewBundle.addProperty("expiryDate", bundleFromOCS.get(j).getAsJsonObject().get("expiryDate").getAsString());
					}
				
					
				
					if(techOfferNewBucket!=null)
					newmainBucketArray.add(techOfferNewBucket);
					if(newmainBucketArray!=null)
					techOfferNewBundle.add("buckets", newmainBucketArray);
					
					
					

					
					
					
					
					techOfferReqBundle.add(techOffBucketJobj);
					
				}
				}
				
				
			
			}
			if(techOfferNewBundle!=null && techOfferNewBundle.has("startDate"))
			newmainBundleArray.add(techOfferNewBundle);
		}
		System.out.println("parsed bundle List- ready for SM -techOfferReqBundle is "+techOfferReqBundle);
		//System.out.println("ready for New Final Response "+newmainBundleArray);
			/*genericDTO.getStrAttributes().put("DataBuckets",techOfferReqBundle.toString());*/
		
		for(int i =0; i<techOfferReqBundle.size();i++)
		{
			if(techOfferReqBundle.get(i).getAsJsonObject().has("id") && !techOfferReqBundle.get(i).getAsJsonObject().get("id").getAsString().equalsIgnoreCase("CMHomeSMSDefaultTariff"))
			{
				techOfferReqBundle.remove(i);
			}
		}
		
		System.out.println("after filtering "+techOfferReqBundle);
		
	

	
	}
}
