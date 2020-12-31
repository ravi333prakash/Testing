import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;

public class ParseTetraFetchOrganization {
	
	public static void main(String[] args) {
		
		String response="{\r\n" + 
				"	\"statusCode\": \"SC0000\",\r\n" + 
				"	\"responseDescription\": \"Success\",\r\n" + 
				"	\"reponseData\": {\r\n" + 
				"		\"suborgAttributesListSize\": \"5\",\r\n" + 
				"		\"CLProtoTcsSuborgAttributesT\": [\r\n" + 
				"			{\r\n" + 
				"				\"orgBlockLevelId\": 1.0,  //org_id\r\n" + 
				"				\"orgBlockMnemonic\": \"Terminals\", //org_name\r\n" + 
				"				\"homeExchangeId\": \"2201185\",\r\n" + 
				"				\"homeExchangeMnemonic\": \"ATC\",\r\n" + 
				"				\"subscriberClass\": 16368.0,\r\n" + 
				"				\"preEmptiveRights\": \"PROTO_TCS_TRUE\",\r\n" + 
				"				\"eeGroupEnergySavingMode\": \"PROTO_TCS_EE_GROUP_ENERGY_SAVING_MODE_T_EG0_C\",\r\n" + 
				"				\"ipAddressRangeList\": {\r\n" + 
				"					\"rangeList\": [\r\n" + 
				"						{\r\n" + 
				"							\"lowerBoundary\": \"174338592\",\r\n" + 
				"							\"upperBoundary\": \"174338814\"\r\n" + 
				"						}\r\n" + 
				"					]\r\n" + 
				"				},\r\n" + 
				"				\"callAuthorizingDispatcher\": {\r\n" + 
				"					\"type\": \"PROTO_TCS_SUBSCRIBER_ADDRESS_TYPE_T_SSI_C\",\r\n" + 
				"					\"mni\": {}\r\n" + 
				"				},\r\n" + 
				"				\"dualWatchAbsencePeriod\": \"PROTO_TCS_EE_GROUP_ENERGY_SAVING_MODE_T_EG0_C\"\r\n" + 
				"			},\r\n" + 
				"			{\r\n" + 
				"				\"orgBlockLevelId\": 2.0,\r\n" + 
				"				\"orgBlockMnemonic\": \"Groups\",\r\n" + 
				"				\"homeExchangeId\": \"2201185\",\r\n" + 
				"				\"homeExchangeMnemonic\": \"ATC\",\r\n" + 
				"				\"subscriberClass\": 16368.0,\r\n" + 
				"				\"preEmptiveRights\": \"PROTO_TCS_TRUE\",\r\n" + 
				"				\"eeGroupEnergySavingMode\": \"PROTO_TCS_EE_GROUP_ENERGY_SAVING_MODE_T_EG0_C\",\r\n" + 
				"				\"ipAddressRangeList\": {\r\n" + 
				"					\"rangeList\": [\r\n" + 
				"						{\r\n" + 
				"							\"lowerBoundary\": \"174338592\"\r\n" + 
				"						}\r\n" + 
				"					]\r\n" + 
				"				},\r\n" + 
				"				\"callAuthorizingDispatcher\": {\r\n" + 
				"					\"type\": \"PROTO_TCS_SUBSCRIBER_ADDRESS_TYPE_T_SSI_C\",\r\n" + 
				"					\"mni\": {}\r\n" + 
				"				},\r\n" + 
				"				\"dualWatchAbsencePeriod\": \"PROTO_TCS_EE_GROUP_ENERGY_SAVING_MODE_T_EG0_C\"\r\n" + 
				"			}\r\n" + 
				"		],\r\n" + 
				"		\"CLProtoTcsOrgBlockAttributesSetMaskT\": {\r\n" + 
				"			\"allAttributesPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"homeExchangeIdPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"homeExchangeMnemonicPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"orgBlockMnemonicPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"apnIndexPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"apnPoolListPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"subscriberClassPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"bitMaskForSubscriberClassPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"preEmptiveRightsPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"eeGroupEnergySavingModePresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"eeGroupStartPointFrameNumberPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"ipAddressRangeListPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"callAuthorizingDispatcherPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"userGroupIdPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"dualWatchAbsencePeriodPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"scchClassPresent\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"topLevelBitMaskForSubscriberClassPresent\": \"PROTO_TCS_TRUE\"\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"}\r\n" + 
				"";
		
		
		JsonObject organisationmini= null;
		JsonArray organisations=new JsonArray();
		JsonObject responseObject=new JsonObject();
		
	if(response!=null && response.contains("CLProtoTcsSuborgAttributesT"))
	{
		responseObject=new Gson().fromJson(response,JsonObject.class);
		
	}
	if (responseObject.has("reponseData") && responseObject.get("reponseData").getAsJsonObject().has("CLProtoTcsSuborgAttributesT")) {
		JsonArray CLProtoTcsSuborgAttributesT=new JsonArray();
		
		CLProtoTcsSuborgAttributesT = responseObject.get("reponseData").getAsJsonObject().get("CLProtoTcsSuborgAttributesT")
				.getAsJsonArray();

	System.out.println("CLProtoTcsSuborgAttributesT "+CLProtoTcsSuborgAttributesT);
	for (JsonElement clproto:CLProtoTcsSuborgAttributesT)
	{
		organisationmini= new JsonObject();
		if(clproto.getAsJsonObject().has("orgBlockLevelId")) {
			organisationmini.addProperty("organisation_id", clproto.getAsJsonObject().get("orgBlockLevelId").toString());
			
		}
		if(clproto.getAsJsonObject().has("orgBlockMnemonic")) {
			organisationmini.add("organisation_name", clproto.getAsJsonObject().get("orgBlockMnemonic"));
		

		}
		organisations.add(organisationmini);
		
	}
	
	System.out.println("organisations "+organisations);
	
	
	
/**
 * ignore below code is for hrirachysom req builder
 */
	//String result="";
		StringBuilder sb=new StringBuilder();
		String organization_id_crm="2-65535-65535-65535-65535-65535";
		sb.append(organization_id_crm);
		int size=organization_id_crm.split("-").length;
		if(organization_id_crm.equals(""))
			size=0;
		System.out.println(size);
		for(int i=0;i<6-size;i++)
		{
			//if(sb.toString().length()>1&& !sb.toString().substring(0, 1).equals(","))
			 sb.append(",655").toString();
			//else
			//	 result=sb.append(",655").toString();

				System.out.println("h");

		}
		if(sb.toString().startsWith(",")) {
			sb.deleteCharAt(0);
			sb.toString();
		}
		System.out.println(sb.toString().replaceAll("-", ","));
	}
	StringBuilder sb1=new StringBuilder();

System.out.println(sb1.toString().replaceAll("-", ",")+"ss");	
	}

}
