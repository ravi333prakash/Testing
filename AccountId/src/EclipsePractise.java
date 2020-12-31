import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class EclipsePractise {

	public static void main(String [] args)
	{

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
		
		JsonObject talkgroupmini= null;
		JsonArray talkgroup=new JsonArray();
		JsonObject responseObject=new JsonObject();
		
		JsonObject responseObject1=new JsonObject();
		JsonObject responseObject2=new JsonObject();
		
	if(response!=null && response.contains("talk_group"))
	{
		responseObject=new Gson().fromJson(response,JsonObject.class);
		
	}
	if (responseObject.has("BillingSystem") && responseObject.get("BillingSystem").getAsJsonObject().has("response")) {
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
		
		
		talkgroup.add(talkgroupmini);
		
	}
	
	System.out.println("talk_group "+talkgroup);
	
	
	

	}

	
	}
}
