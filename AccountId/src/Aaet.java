import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Aaet {

	
	public static void main(String args[])
	{
		String reqXml="<Request> <requestId><![CDATA[{0}]]></requestId><requestType><![CDATA[EMAIL]]></requestType><langId><![CDATA[1]]></langId><senderSystem><![CDATA[{2}]]></senderSystem><timeStamp><![CDATA[{3}]]></timeStamp><responseURL><![CDATA[http://192.168.0.50:2232/servlet/RequestServlet]]></responseURL><tagList><tagData><name><![CDATA[EMAIL_ID]]></name><value><![CDATA[{6},{12}]]></value></tagData><tagData><name><![CDATA[EMAIL_TEXT]]></name><value></value></tagData><tagData><name><![CDATA[EMAIL_FILENAME]]></name><value><![CDATA[]]></value></tagData><tagData><name><![CDATA[EMAIL_FILEPATH]]></name><value><![CDATA[/opt/saturn/backup/]]></value></tagData><tagData><name><![CDATA[CC_MAIL_IDs]]></name><value><![CDATA[vijayalakshmi.kn@6dtech.co.in,ananth.br@6dtech.co.in,srinidhi.srinivasrao@6dtech.co.in]]></value></tagData><tagData><name><![CDATA[BCC_MAIL_IDs]]></name><value><![CDATA[]]></value></tagData><tagData><name><![CDATA[MESSAGE_ID]]></name><value><![CDATA[{5}]]></value></tagData><tagData><name><![CDATA[ACCOUNT_ID]]></name><value><![CDATA[{7}]]></value></tagData><tagData><name><![CDATA[ADJUSTMENT_ID]]></name><value><![CDATA[{8}]]></value></tagData><tagData><name><![CDATA[INVOICE_ID]]></name><value><![CDATA[{9}]]></value></tagData><tagData><name><![CDATA[ADJUSTED_AMOUNT]]></name><value><![CDATA[{10}]]></value></tagData><tagData><name><![CDATA[BUSINESSHRIND]]></name><value><![CDATA[0]]></value></tagData><tagData><name><![CDATA[DATE]]></name><value><![CDATA[{11}]]></value></tagData><tagData><name><![CDATA[FTP_ID]]></name><value><![CDATA[1]]></value></tagData><tagData><name><![CDATA[EMAIL_SUBJECT]]></name><value><![CDATA[SIX DEE]]></value></tagData><tagData><name><![CDATA[SERVER_TYPE]]></name><value><![CDATA[sftp]]></value></tagData><tagData><name><![CDATA[IMAGE_ID]]></name><value><![CDATA[1]]></value></tagData><tagData><name><![CDATA[MVNO_ID]]></name><value><![CDATA[1]]></value></tagData><tagData><name><![CDATA[Priority]]></name><value><![CDATA[2]]></value></tagData></tagList></Request>";
		String keys="request_id,request_type,source_node,NE_TIMESTAMP,service_id,message_id,customer_email_id,grp_account_id,adjustment_id,invoice_ids,adjustment_amount,request_timestamp,rejection";
		String val = null;
		String[] headerKeys = keys.split(",");
		
		for (int i = 0; i < headerKeys.length; i++) {

			if (nullCheck(val = get(headerKeys[i]))) {
				reqXml = reqXml.replace(new StringBuilder().append("{").append(i).append("}").toString(), val);
			} else {
				reqXml = reqXml.replace(new StringBuilder().append("{").append(i).append("}").toString(), "");
			}

		}
	//	System.out.println(reqXml);
		String collection_date="11082020000000";
		
//System.out.println(collection_date.substring(0,10)+".");

/*Map<String,String>map=new HashMap();
map.put("hi", collection_date.substring(0,10));
System.out.println(map.get("hi")+" Col "+collection_date );*/
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
		 if(collection_date!=null) {
			 Date date3;
			try {
				date3 = sdf.parse(collection_date);
				String date4=sd.format(date3);
				System.out.println(date4);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			  
			
			JsonObject pstnCallRightsList = new JsonObject();
			JsonObject connGroupRightsObj=new JsonObject();
			JsonArray connGroupRightsList = new JsonArray();
			
			connGroupRightsObj.addProperty("connGroupId", "pstnconfig");
			connGroupRightsObj.addProperty("connGroupMnemonic", "pstnconfig");
			connGroupRightsObj.addProperty("rightsMask", 3);
			connGroupRightsList.add(connGroupRightsObj);
			pstnCallRightsList.add("connGroupRightsList", connGroupRightsList);
			StringBuilder sb= new StringBuilder();
			sb.append("\"pstnCallRightsList\":").append(pstnCallRightsList).append(",");
		//	System.out.println( sb.toString());
			
			StringBuilder sb1= new StringBuilder();
			sb1.append("\"profile\": {\r\n" + 
					"                \"show_balance\": \"true\",\r\n" + 
					"                \"profile_id\": \"").append(get("profile_id")).append("\"\r\n" + 
							"            }");
		//	System.out.println(sb1);
			
			 }

	
	String optionLst="[{\"seq_id\":\"101\"},{\"seq_id\":\"102\"}]";
//System.out.println(optionLst.replace("\\[",""));
String response="{\r\n" + 
		"    \"BillingSystem\": {\r\n" + 
		"        \"request_id\": \"130892314\",\r\n" + 
		"        \"request_timestamp\": \"23052020165927\",\r\n" + 
		"        \"response_timestamp\": \"18092020125748\",\r\n" + 
		"        \"action\": \"ViewSubscription\",\r\n" + 
		"        \"source\": \"CRM\",\r\n" + 
		"        \"username\": \"6dadmin\",\r\n" + 
		"        \"userid\": \"1\",\r\n" + 
		"        \"result_code\": \"0\",\r\n" + 
		"        \"result_desc\": \"Success\",\r\n" + 
		"        \"upfront_payment\": \"no\",\r\n" + 
		"        \"response\": {\r\n" + 
		"            \"dataset\": {\r\n" + 
		"                \"recordset\": [\r\n" + 
		"                    {\r\n" + 
		"                        \"subscriptions\": [\r\n" + 
		"                            {\r\n" + 
		"                                \"subscription_id\": \"8370\",\r\n" + 
		"                                \"service_seq_id\": \"7349\",\r\n" + 
		"                                \"service_id\": \"999887776\",\r\n" + 
		"                                \"product_id\": \"56\",\r\n" + 
		"                                \"product_name\": \"HSI500_SYMMETRIC_DEDICATED\",\r\n" + 
		"                                \"plan_id\": \"11\",\r\n" + 
		"                                \"plan_name\": \"HSI500_SYMMETRIC_DEDICATED\",\r\n" + 
		"                                \"plan_desc\": \"500 X 500 DEDICATED\",\r\n" + 
		"                                \"is_base_plan\": \"1\",\r\n" + 
		"                                \"is_prorata\": \"0\",\r\n" + 
		"                                \"activation_date\": \"07-01-2020 20:22:21\",\r\n" + 
		"                                \"expiry_date\": \"21-01-2021 00:00:00\",\r\n" + 
		"                                \"external_plan_id\": \"Italk500\",\r\n" + 
		"                                \"status\": \"1\",\r\n" + 
		"                                \"deactivation_date\": \"\",\r\n" + 
		"                                \"create_date\": \"11-09-2020 00:18:07\",\r\n" + 
		"                                \"bundle_id\": \"\",\r\n" + 
		"                                \"offer_service_id\": \"\",\r\n" + 
		"                                \"receipt_no\": \"\",\r\n" + 
		"                                \"deposit_amount\": \"0.00\",\r\n" + 
		"                                \"quantity_based\": \"N\",\r\n" + 
		"                                \"external_event\": \"00000000\",\r\n" + 
		"                                \"group_id\": \"\",\r\n" + 
		"                                \"short_plan_desc\": \"\",\r\n" + 
		"                                \"subscription_charges\": [\r\n" + 
		"                                    {\r\n" + 
		"                                        \"charge_id\": \"9570\",\r\n" + 
		"                                        \"subscription_id\": \"8370\",\r\n" + 
		"                                        \"charge_type\": \"2\",\r\n" + 
		"                                        \"charge_code\": \"1\",\r\n" + 
		"                                        \"charge_name\": \"Plan Rental\",\r\n" + 
		"                                        \"charge_desc\": \"Plan Rental\",\r\n" + 
		"                                        \"charge_name_ol\": \"\",\r\n" + 
		"                                        \"charge_desc_ol\": \"\",\r\n" + 
		"                                        \"start_date\": \"07-10-2020 12:50:04\",\r\n" + 
		"                                        \"end_date\": \"31-10-2040 12:50:04\",\r\n" + 
		"                                        \"charge_amount\": \"50000.000000\",\r\n" + 
		"                                        \"charge_tax\": \"4000.000000\",\r\n" + 
		"                                        \"total_charge\": \"54000.000000\",\r\n" + 
		"                                        \"charge_duration\": \"\",\r\n" + 
		"                                        \"charge_validity\": \"180\",\r\n" + 
		"                                        \"charge_frequency\": \"2\",\r\n" + 
		"                                        \"charge_factor\": \"1\",\r\n" + 
		"                                        \"charge_fee\": \"54000.000000\",\r\n" + 
		"                                        \"charge_category\": \"1\",\r\n" + 
		"                                        \"is_prorata\": \"1\",\r\n" + 
		"                                        \"prorata_flags\": \"1001\",\r\n" + 
		"                                        \"epc_enabled\": \"\",\r\n" + 
		"                                        \"bundle_id\": \"-1\",\r\n" + 
		"                                        \"quantity\": \"1\",\r\n" + 
		"                                        \"quantity_based\": \"N\"\r\n" + 
		"                                    }\r\n" + 
		"                                ],\r\n" + 
		"                                \"version_id\": \"\",\r\n" + 
		"                                \"plan_name_ol\": \"\",\r\n" + 
		"                                \"plan_desc_ol\": \"\",\r\n" + 
		"                                \"linked_plan_id\": \"\",\r\n" + 
		"                                \"max_talk_group_size\": \"\",\r\n" + 
		"                                \"pushcom_plan_id\": \"\",\r\n" + 
		"                                \"size\": 1,\r\n" + 
		"                                \"quantity\": 1,\r\n" + 
		"                                \"pop_a_end\": \"\",\r\n" + 
		"                                \"b_end\": \"\",\r\n" + 
		"                                \"order_id\": \"\",\r\n" + 
		"                                \"order_type\": \"\",\r\n" + 
		"                                \"rl_reference_no\": \"\",\r\n" + 
		"                                \"end_user_tag\": \"\",\r\n" + 
		"                                \"end_user_type\": \"\",\r\n" + 
		"                                \"cug_id\": \"\",\r\n" + 
		"                                \"pabx_number\": \"\",\r\n" + 
		"                                \"pstn_number\": \"234\"\r\n" + 
		"                            },\r\n" + 
		"                            {\r\n" + 
		"                                \"subscription_id\": \"8371\",\r\n" + 
		"                                \"service_seq_id\": \"7349\",\r\n" + 
		"                                \"service_id\": \"999887776\",\r\n" + 
		"                                \"product_id\": \"57\",\r\n" + 
		"                                \"product_name\": \"SYMMETRIC_DEDICATED\",\r\n" + 
		"                                \"plan_id\": \"11\",\r\n" + 
		"                                \"plan_name\": \"SYMMETRIC_DEDICATED\",\r\n" + 
		"                                \"plan_desc\": \"500 X 500 DEDICATED\",\r\n" + 
		"                                \"is_base_plan\": \"1\",\r\n" + 
		"                                \"is_prorata\": \"0\",\r\n" + 
		"                                \"activation_date\": \"07-01-2020 20:22:21\",\r\n" + 
		"                                \"expiry_date\": \"21-01-2021 00:00:00\",\r\n" + 
		"                                \"external_plan_id\": \"Italk500\",\r\n" + 
		"                                \"status\": \"1\",\r\n" + 
		"                                \"deactivation_date\": \"\",\r\n" + 
		"                                \"create_date\": \"11-09-2020 00:18:07\",\r\n" + 
		"                                \"bundle_id\": \"\",\r\n" + 
		"                                \"offer_service_id\": \"\",\r\n" + 
		"                                \"receipt_no\": \"\",\r\n" + 
		"                                \"deposit_amount\": \"0.00\",\r\n" + 
		"                                \"quantity_based\": \"N\",\r\n" + 
		"                                \"external_event\": \"00000000\",\r\n" + 
		"                                \"group_id\": \"\",\r\n" + 
		"                                \"short_plan_desc\": \"\",\r\n" + 
		"                                \"subscription_charges\": [\r\n" + 
		"                                    {\r\n" + 
		"                                        \"charge_id\": \"9571\",\r\n" + 
		"                                        \"subscription_id\": \"8371\",\r\n" + 
		"                                        \"charge_type\": \"2\",\r\n" + 
		"                                        \"charge_code\": \"1\",\r\n" + 
		"                                        \"charge_name\": \"Plan Rental\",\r\n" + 
		"                                        \"charge_desc\": \"Plan Rental\",\r\n" + 
		"                                        \"charge_name_ol\": \"\",\r\n" + 
		"                                        \"charge_desc_ol\": \"\",\r\n" + 
		"                                        \"start_date\": \"07-10-2020 12:50:04\",\r\n" + 
		"                                        \"end_date\": \"31-10-2040 12:50:04\",\r\n" + 
		"                                        \"charge_amount\": \"50000.000000\",\r\n" + 
		"                                        \"charge_tax\": \"4000.000000\",\r\n" + 
		"                                        \"total_charge\": \"54000.000000\",\r\n" + 
		"                                        \"charge_duration\": \"\",\r\n" + 
		"                                        \"charge_validity\": \"180\",\r\n" + 
		"                                        \"charge_frequency\": \"2\",\r\n" + 
		"                                        \"charge_factor\": \"1\",\r\n" + 
		"                                        \"charge_fee\": \"54000.000000\",\r\n" + 
		"                                        \"charge_category\": \"1\",\r\n" + 
		"                                        \"is_prorata\": \"1\",\r\n" + 
		"                                        \"prorata_flags\": \"1001\",\r\n" + 
		"                                        \"epc_enabled\": \"\",\r\n" + 
		"                                        \"bundle_id\": \"-1\",\r\n" + 
		"                                        \"quantity\": \"1\",\r\n" + 
		"                                        \"quantity_based\": \"N\"\r\n" + 
		"                                    }\r\n" + 
		"                                ],\r\n" + 
		"                                \"version_id\": \"\",\r\n" + 
		"                                \"plan_name_ol\": \"\",\r\n" + 
		"                                \"plan_desc_ol\": \"\",\r\n" + 
		"                                \"linked_plan_id\": \"\",\r\n" + 
		"                                \"max_talk_group_size\": \"\",\r\n" + 
		"                                \"pushcom_plan_id\": \"\",\r\n" + 
		"                                \"size\": 1,\r\n" + 
		"                                \"quantity\": 1,\r\n" + 
		"                                \"pop_a_end\": \"\",\r\n" + 
		"                                \"b_end\": \"\",\r\n" + 
		"                                \"order_id\": \"\",\r\n" + 
		"                                \"order_type\": \"\",\r\n" + 
		"                                \"rl_reference_no\": \"\",\r\n" + 
		"                                \"end_user_tag\": \"\",\r\n" + 
		"                                \"end_user_type\": \"\",\r\n" + 
		"                                \"cug_id\": \"\",\r\n" + 
		"                                \"pabx_number\": \"1234\",\r\n" + 
		"                                \"pstn_number\": \"\"\r\n" + 
		"                            }\r\n" + 
		"                        ]\r\n" + 
		"                    }\r\n" + 
		"                ],\r\n" + 
		"                \"totalrecordcnt\": 1,\r\n" + 
		"                \"recordcnt\": 1\r\n" + 
		"            }\r\n" + 
		"        }\r\n" + 
		"    }\r\n" + 
		"}";
//Aaet.getPabx(response);

	}
	public static String get(String s) {
		if(s.equals("rejection"))
			return "ravi";
		return "123";
	}
	public static boolean nullCheck(String key) {

		if (key != null && !key.isEmpty()) {
			return true;
		}
		return false;
		
		
	}
	
	public static void getPabx(String response)
	{

		JsonArray subscriptions =null;
		JsonObject responseObject= new Gson().fromJson(response,JsonObject.class );
		if(response.contains("BillingSystem")&& response.contains("response")&& response.contains("dataset")&&
				response.contains("recordset")&&response.contains("subscriptions")&&
				responseObject !=null && responseObject.has("BillingSystem"))
		{

			subscriptions=responseObject.getAsJsonObject().get("BillingSystem").getAsJsonObject().get("response").getAsJsonObject().get("dataset").
			getAsJsonObject().get("recordset").getAsJsonArray().get(0).getAsJsonObject().get("subscriptions").getAsJsonArray();
			for (JsonElement subscription :subscriptions)
			{

				if (subscription.getAsJsonObject().has("pabx_number") && 
						subscription.getAsJsonObject().get("pabx_number").toString()!=null
						&&!subscription.getAsJsonObject().get("pabx_number").toString().trim().equals("\"\""))
						{
							//genericDTO.getStrAttributes().put("pabx_number",subscription.getAsJsonObject().get("pabx_number").toString());
							System.out.println("pabx_number is "+subscription.getAsJsonObject().get("pabx_number").getAsString());

							
						}
				if (subscription.getAsJsonObject().has("pstn_number") && 
						subscription.getAsJsonObject().get("pstn_number").toString()!=null
						&&!subscription.getAsJsonObject().get("pstn_number").toString().trim().equals("\"\""))
						{
					//genericDTO.getStrAttributes().put("pstn_number",subscription.getAsJsonObject().get("pstn_number").toString());
							System.out.println("pstn_number is "+subscription.getAsJsonObject().get("pstn_number"));

							
						}
			}
		}
	}
	
	
}

