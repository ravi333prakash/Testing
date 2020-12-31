

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;

public class ParseFetchTalkGroup {
	
	public static void main(String[] args) {
		System.out.println("talk_group ");

		String response="{\r\n" + 
				"	\"BillingSystem\": {\r\n" + 
				"		\"request_id\": \"95666000000001234\",\r\n" + 
				"		\"request_timestamp\": \"23092020191857\",\r\n" + 
				"		\"response_timestamp\": \"23092020191855\",\r\n" + 
				"		\"action\": \"FetchTalkGroup\",\r\n" + 
				"		\"source\": \"CRM\",\r\n" + 
				"		\"username\": \"6dadmin\",\r\n" + 
				"		\"userid\": \"1\",\r\n" + 
				"		\"entity_id\": \"1\",\r\n" + 
				"		\"result_code\": \"0\",\r\n" + 
				"		\"result_desc\": \"Success\",\r\n" + 
				"		\"upfront_payment\": \"no\",\r\n" + 
				"		\"response\": {\r\n" + 
				"			\"dataset\": {\r\n" + 
				"				\"totalrecordcnt\": 4,\r\n" + 
				"				\"recordcnt\": 4,\r\n" + 
				"				\"talk_group\": [\r\n" + 
				"					{\r\n" + 
				"						\"group_seq_id\": \"843\",\r\n" + 
				"						\"talk_group_id\": \"3458\",\r\n" + 
				"						\"fleet_id\": \"12326\",\r\n" + 
				"						\"network_type\": \"1\",\r\n" + 
				"						\"talk_group_name\": \"Dubai Police\",\r\n" + 
				"						\"status\": \"1\",\r\n" + 
				"						\"create_user\": \"zxe\",\r\n" + 
				"						\"create_date\": \"08-01-2020 12:42:48\",\r\n" + 
				"						\"update_date\": \"08-01-2020 12:42:48\",\r\n" + 
				"						\"entity_id\": \"1\",\r\n" + 
				"						\"account_id\": \"3458\",\r\n" + 
				"						\"param_list\": [\r\n" + 
				"							{\r\n" + 
				"								\"id\": \"extra_id1\",\r\n" + 
				"								\"value\": \"extra_val1\"\r\n" + 
				"							},\r\n" + 
				"							{\r\n" + 
				"								\"id\": \"extra_id2\",\r\n" + 
				"								\"value\": \"extra_val2\"\r\n" + 
				"							}\r\n" + 
				"						]\r\n" + 
				"					},\r\n" + 
				"					{\r\n" + 
				"						\"group_seq_id\": \"881\",\r\n" + 
				"						\"talk_group_id\": \"3458\",\r\n" + 
				"						\"fleet_id\": \"12326\",\r\n" + 
				"						\"network_type\": \"1\",\r\n" + 
				"						\"talk_group_name\": \"Dubai Police\",\r\n" + 
				"						\"status\": \"1\",\r\n" + 
				"						\"create_user\": \"zxe\",\r\n" + 
				"						\"create_date\": \"17-01-2020 12:48:54\",\r\n" + 
				"						\"update_date\": \"17-01-2020 12:48:54\",\r\n" + 
				"						\"entity_id\": \"1\",\r\n" + 
				"						\"account_id\": \"3458\",\r\n" + 
				"						\"param_list\": [\r\n" + 
				"							{\r\n" + 
				"								\"id\": \"extra_id1\",\r\n" + 
				"								\"value\": \"extra_val1\"\r\n" + 
				"							},\r\n" + 
				"							{\r\n" + 
				"								\"id\": \"extra_id2\",\r\n" + 
				"								\"value\": \"extra_val2\"\r\n" + 
				"							}\r\n" + 
				"						]\r\n" + 
				"					},\r\n" + 
				"					{\r\n" + 
				"						\"group_seq_id\": \"961\",\r\n" + 
				"						\"talk_group_id\": \"1168\",\r\n" + 
				"						\"fleet_id\": \"12326\",\r\n" + 
				"						\"network_type\": \"1\",\r\n" + 
				"						\"talk_group_name\": \"Dubai Police\",\r\n" + 
				"						\"status\": \"1\",\r\n" + 
				"						\"create_user\": \"zxe\",\r\n" + 
				"						\"create_date\": \"27-01-2020 20:02:13\",\r\n" + 
				"						\"update_date\": \"27-01-2020 20:02:13\",\r\n" + 
				"						\"entity_id\": \"1\",\r\n" + 
				"						\"account_id\": \"1168\",\r\n" + 
				"						\"param_list\": [\r\n" + 
				"							{\r\n" + 
				"								\"id\": \"extra_id1\",\r\n" + 
				"								\"value\": \"extra_val1\"\r\n" + 
				"							},\r\n" + 
				"							{\r\n" + 
				"								\"id\": \"extra_id2\",\r\n" + 
				"								\"value\": \"extra_val2\"\r\n" + 
				"							}\r\n" + 
				"						]\r\n" + 
				"					},\r\n" + 
				"					{\r\n" + 
				"						\"group_seq_id\": \"1102\",\r\n" + 
				"						\"talk_group_id\": \"3362\",\r\n" + 
				"						\"fleet_id\": \"12326\",\r\n" + 
				"						\"network_type\": \"1\",\r\n" + 
				"						\"talk_group_name\": \"3362\",\r\n" + 
				"						\"status\": \"1\",\r\n" + 
				"						\"create_user\": \"zxe\",\r\n" + 
				"						\"create_date\": \"05-02-2020 19:48:42\",\r\n" + 
				"						\"update_date\": \"05-02-2020 19:48:42\",\r\n" + 
				"						\"entity_id\": \"1\",\r\n" + 
				"						\"account_id\": \"1168\",\r\n" + 
				"						\"param_list\": [\r\n" + 
				"							{\r\n" + 
				"								\"id\": \"extra_id1\",\r\n" + 
				"								\"value\": \"extra_val1\"\r\n" + 
				"							},\r\n" + 
				"							{\r\n" + 
				"								\"id\": \"extra_id2\",\r\n" + 
				"								\"value\": \"extra_val2\"\r\n" + 
				"							}\r\n" + 
				"						]\r\n" + 
				"					}\r\n" + 
				"				]\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"}\r\n" + 
				"";
		
		String SOMResponse="{\r\n" + 
				"	\"statusCode\": \"SC0000\",\r\n" + 
				"	\"responseDescription\": \"Success\",\r\n" + 
				"	\"reponseData\": {\r\n" + 
				"		\"CLProtoTcsRSAttributesT\": {\r\n" + 
				"			\"normalPriority\": 3.0,\r\n" + 
				"			\"clip\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"globalIndividualCallRights\": 3.0,\r\n" + 
				"			\"globalSdsMessageRights\": 3.0,\r\n" + 
				"			\"globalStatusMessageRights\": 3.0,\r\n" + 
				"			\"e2eeClearance\": \"PROTO_TCS_E2EE_CLEARANCE_T_E2EE_ALLOWED_C\",\r\n" + 
				"			\"preEmptiveServiceRights\": \"PROTO_TCS_PRE_EMPTIVE_SERVICE_RIGHTS_T_NO_RIGHTS_C\",\r\n" + 
				"			\"storageUsageRights\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"forwardedToNumber\": {\r\n" + 
				"				\"subscriberAddress\": {\r\n" + 
				"					\"type\": \"PROTO_TCS_SUBSCRIBER_ADDRESS_TYPE_T_SSI_C\",\r\n" + 
				"					\"mni\": {}\r\n" + 
				"				}\r\n" + 
				"			},\r\n" + 
				"			\"radioSubscriberAddress\": {\r\n" + 
				"				\"type\": \"PROTO_TCS_SUBSCRIBER_ADDRESS_TYPE_T_TSI_C\",\r\n" + 
				"				\"ssi\": \"7000300\",\r\n" + 
				"				\"mni\": {\r\n" + 
				"					\"landCode\": 424.0,\r\n" + 
				"					\"networkCode\": 100.0\r\n" + 
				"				}\r\n" + 
				"			},\r\n" + 
				"			\"radioSubscriberMnemonic\": \"RS 7000300\",\r\n" + 
				"			\"orgBlockId\": [\r\n" + 
				"				2.0,\r\n" + 
				"				65535.0,\r\n" + 
				"				65535.0,\r\n" + 
				"				65535.0,\r\n" + 
				"				65535.0,\r\n" + 
				"				65535.0\r\n" + 
				"			],\r\n" + 
				"			\"orgBlockMnemonic\": \"6D\",\r\n" + 
				"			\"authentication\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"ipAddress\": \"171671841\",\r\n" + 
				"			\"ipAddressWithValue\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"duplexCallAllowed\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"semiduplexCallAllowed\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"tripletUseCounter\": 100.0,\r\n" + 
				"			\"tripletValidityTime\": {\r\n" + 
				"				\"value\": 1.0,\r\n" + 
				"				\"type\": \"PROTO_TCS_TRIPLET_VALIDITY_TIME_TYPE_T_DAYS_C\"\r\n" + 
				"			},\r\n" + 
				"			\"requiredEncryptionMethod\": \"PROTO_TCS_ENCRYPTION_METHOD_T_NO_ENCRYPTION_C\",\r\n" + 
				"			\"subscriberClass\": 16368.0,\r\n" + 
				"			\"maximumNumberOfTimeslots\": 1.0,\r\n" + 
				"			\"subscriberPolling\": \"PROTO_TCS_SUBSCRIBER_POLLING_T_MOBILE_SUBSCRIBER_C\",\r\n" + 
				"			\"subscriberSpeechItemPriority\": 3.0,\r\n" + 
				"			\"individualCallArea\": {\r\n" + 
				"				\"type\": \"PROTO_TCS_SUBSCRIBER_ADDRESS_TYPE_T_TSI_C\",\r\n" + 
				"				\"mni\": {\r\n" + 
				"					\"landCode\": 424.0,\r\n" + 
				"					\"networkCode\": 100.0\r\n" + 
				"				}\r\n" + 
				"			},\r\n" + 
				"			\"visitorOrgBlockId\": [\r\n" + 
				"				65535.0,\r\n" + 
				"				65535.0,\r\n" + 
				"				65535.0,\r\n" + 
				"				65535.0,\r\n" + 
				"				65535.0,\r\n" + 
				"				65535.0\r\n" + 
				"			],\r\n" + 
				"			\"subscriberType\": \"PROTO_TCS_SUBSCRIBER_TYPE_2_T_RADIO_SUBSCRIBER_C\",\r\n" + 
				"			\"ruaRequiredInRegistration\": \"PROTO_TCS_RUA_REQUIRED_IN_REGISTRATION_T_NOT_REQUIRED_C\",\r\n" + 
				"			\"pinForAliasIdentity\": \"1048575\",\r\n" + 
				"			\"migrationAllowed\": \"PROTO_TCS_TRUE\",\r\n" + 
				"			\"tedsFunctionality\": \"PROTO_TCS_TEDS_FUNCTIONALITY_T_NOT_ALLOWED_C\",\r\n" + 
				"			\"tedsReadyTimer\": \"PROTO_TCS_TEDS_READY_TIMER_T_10s_C\",\r\n" + 
				"			\"preEmptiveResourcePriority\": 1.0\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"}\r\n" + 
				"";
		
		JsonObject talkgroupmini= null;
		JsonArray talkgroup=new JsonArray();
		JsonObject responseObject=new JsonObject();
		JsonObject somResponseObject=new JsonObject();
		JsonObject CLProtoTcsRSAttributesT=new JsonObject();
		
		JsonObject responseObject1=new JsonObject();
		JsonObject responseObject2=new JsonObject();
		
/*	if(response!=null && response.contains("talk_group"))
	{
		responseObject=new Gson().fromJson(response,JsonObject.class);
		
	}*/
	if(SOMResponse!=null && SOMResponse.contains("CLProtoTcsRSAttributesT"))
	{
		System.out.println("2");
		somResponseObject=new Gson().fromJson(SOMResponse,JsonObject.class);
		
	}
	if(somResponseObject.has("reponseData")  && somResponseObject.get("reponseData").getAsJsonObject().has("CLProtoTcsRSAttributesT")) {
		System.out.println("1");

		CLProtoTcsRSAttributesT=somResponseObject.get("reponseData").getAsJsonObject().get("CLProtoTcsRSAttributesT").getAsJsonObject();
		String config ="normalPriority,clip,orgBlockId,forwardedToNumber";
		for(String somElement: config.split(",")) {
		if(CLProtoTcsRSAttributesT!=null && CLProtoTcsRSAttributesT.has(somElement) &&! CLProtoTcsRSAttributesT.getAsJsonObject().get(somElement).toString().contains("[")&& ! CLProtoTcsRSAttributesT.getAsJsonObject().get(somElement).toString().contains("{"))
		{
			String SomValues=CLProtoTcsRSAttributesT.getAsJsonObject().get(somElement).getAsString().toString();
			System.out.println(somElement+":"+SomValues);
			

		}
		else
			if(CLProtoTcsRSAttributesT!=null && CLProtoTcsRSAttributesT.has(somElement) && CLProtoTcsRSAttributesT.getAsJsonObject().get(somElement).toString().contains("["))
		{
		System.out.println(CLProtoTcsRSAttributesT.getAsJsonObject().get(somElement).toString().replace(",", "-").replace("[", "").replace("]", ""));
		}
		}
		
	}
/*	if (responseObject.has("BillingSystem") && responseObject.get("BillingSystem").getAsJsonObject().has("response")) {
		JsonArray talk_group=new JsonArray();
		
		responseObject1 = responseObject.get("BillingSystem").getAsJsonObject().get("response").getAsJsonObject();
		responseObject2=new Gson().fromJson(responseObject1,JsonObject.class);
		if(responseObject2.has("dataset") && responseObject2.get("dataset").getAsJsonObject().has("talk_group"))
		{
			talk_group = responseObject2.get("dataset").getAsJsonObject().get("talk_group").getAsJsonArray();
		  }
	for (JsonElement clproto:talk_group)
	{
		talkgroupmini= new JsonObject();
		
		if(clproto.getAsJsonObject().has("group_seq_id")) {
			talkgroupmini.add("group_seq_id", clproto.getAsJsonObject().get("group_seq_id"));
			
		}
		if(clproto.getAsJsonObject().has("talk_group_id")) {
			talkgroupmini.add("talk_group_id", clproto.getAsJsonObject().get("talk_group_id"));
			
		}
		if(clproto.getAsJsonObject().has("fleet_id")) {
			talkgroupmini.add("fleet_id", clproto.getAsJsonObject().get("fleet_id"));
			
		}
		if(clproto.getAsJsonObject().has("network_type")) {
			talkgroupmini.add("network_type", clproto.getAsJsonObject().get("network_type"));
			
		}
		if(clproto.getAsJsonObject().has("talk_group_name")) {
			talkgroupmini.add("talk_group_name", clproto.getAsJsonObject().get("talk_group_name"));
		

		}
		if(clproto.getAsJsonObject().has("status")) {
			talkgroupmini.add("status", clproto.getAsJsonObject().get("status"));
		

		}
		if(clproto.getAsJsonObject().has("create_user")) {
			talkgroupmini.add("create_user", clproto.getAsJsonObject().get("create_user"));
		

		}
		if(clproto.getAsJsonObject().has("create_date")) {
			talkgroupmini.add("create_date", clproto.getAsJsonObject().get("create_date"));
		

		}
		if(clproto.getAsJsonObject().has("update_date")) {
			talkgroupmini.add("update_date", clproto.getAsJsonObject().get("update_date"));
		

		}
		if(clproto.getAsJsonObject().has("entity_id")) {
			talkgroupmini.add("entity_id", clproto.getAsJsonObject().get("entity_id"));
		

		}
		if(clproto.getAsJsonObject().has("account_id")) {
			talkgroupmini.add("account_id", clproto.getAsJsonObject().get("account_id"));
		

		}
		/////////////
		
		if(CLProtoTcsSuborgAttributesT!=null && )
		{
			
		}
		
		talkgroup.add(talkgroupmini);
		
	}
	
	System.out.println("talk_group "+talkgroup);
	
	
	

	}*/

	}

}
