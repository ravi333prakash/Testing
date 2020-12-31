import java.util.Arrays;
import java.util.HashMap;

import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

public class JsonRead {
	static HashMap<String,String> map=new HashMap<String,String>();
	public static void put(String key,String value)
	{
		map.put(key,value);
	}
	public static String get(String key)
	{
		return map.get(key);
	}
	public static void main(String[] args) {
		
		String mandatoryFields="account_id,upfront_payment";//request_id,timestamp,source_node,action
		String mandatoryFieldsPath="Request.order_information.payment.account_id,Request.order_information.dataset.param[0].id";//Request.request_id,Request.timeStamp,Request.sourceNode,Request.action
		String pathCheckEnabled="true";
		put("MiddlewareRequest","{\r\n" + 
				"    \"Request\": {\r\n" + 
				"        \"request_id\": \"11278385107239\",\r\n" + 
				"        \"feature\": \"MakePayment\",\r\n" + 
				"        \"action\": \"MakePayment\",\r\n" + 
				"        \"timeStamp\": \"2019-08-31 10:54:41\",\r\n" + 
				"        \"sourceNode\": \"CRM\",\r\n" + 
				"        \"userid\": \"401\",\r\n" + 
				"        \"username\": \"raychan\",\r\n" + 
				"        \"order_information\": {\r\n" + 
				"            \"order_type\": \"MakePayment\",\r\n" + 
				"            \"dataset\": {\r\n" + 
				"                \"param\": [\r\n" + 
				"                    {\r\n" + 
				"                        \"id\": \"upfront_payment\",\r\n" + 
				"                        \"value\": \"no\"\r\n" + 
				"                    },\r\n" + 
				"                    {\r\n" + 
				"                        \"id\": \"external_gw_enabled\",\r\n" + 
				"                        \"value\": \"true\"\r\n" + 
				"                    },\r\n" + 
				"                    {\r\n" + 
				"                        \"id\": \"waive_off\",\r\n" + 
				"                        \"value\": \"false\"\r\n" + 
				"                    },\r\n" + 
				"                    {\r\n" + 
				"                        \"id\": \"DummyValue\",\r\n" + 
				"                        \"value\": \"JustToHandleTheNullPointer\"\r\n" + 
				"                    },\r\n" + 
				"                    {\r\n" + 
				"                        \"id\": \"entity_id\",\r\n" + 
				"                        \"value\": \"1000\"\r\n" + 
				"                    },\r\n" + 
				"                     {\r\n" + 
				"                        \"id\": \"reason_code\",\r\n" + 
				"                        \"value\": \"20\"\r\n" + 
				"                    },\r\n" + 
				"                    {\r\n" + 
				"                        \"id\": \"amount_paid\",\r\n" + 
				"                        \"value\": \"89.76\"\r\n" + 
				"                    },\r\n" + 
				"                    {\r\n" + 
				"                        \"id\": \"customer_email_id\",\r\n" + 
				"                        \"value\": \"Hello@gmail.com\"\r\n" + 
				"                    },\r\n" + 
				"                    {\r\n" + 
				"                        \"id\": \"invoice_date\",\r\n" + 
				"                        \"value\": \"21-08-1998\"\r\n" + 
				"                    },\r\n" + 
				"                    {\r\n" + 
				"                        \"id\": \"next_attempt_date\",\r\n" + 
				"                        \"value\": \"29-08-1998\"\r\n" + 
				"                    },\r\n" + 
				"                    {\r\n" + 
				"                        \"id\": \"suspension_date\",\r\n" + 
				"                        \"value\": \"31-08-1998\"\r\n" + 
				"                    },\r\n" + 
				"                    {\r\n" + 
				"                        \"id\": \"sms_numbers\",\r\n" + 
				"                        \"value\": \"98123456\"\r\n" + 
				"                    }\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"                ]\r\n" + 
				"            },\r\n" + 
				"            \"payment\": {\r\n" + 
				"                \"account_id\": \"30020279\",\r\n" + 
				"                \"profile_id\": \"10020279\",\r\n" + 
				"                \"currency_code\": \"SGD\",\r\n" + 
				"                \"collection_source_type\": \"NORMAL\",\r\n" + 
				"                \"collection_id\": \"12\",\r\n" + 
				"                \"comment\": \"\",\r\n" + 
				"                \"order_type\": \"billpayment\",\r\n" + 
				"                \"amount\": \"910.74\",\r\n" + 
				"                \"payment_detail\": [\r\n" + 
				"                    {\r\n" + 
				"                        \"payment_mode\": \"1\",\r\n" + 
				"                        \"amount_paid\": \"910.74\",\r\n" + 
				"                        \"mode_detail\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"key\": \"payment_subscriber_id\",\r\n" + 
				"                                \"value\": \"cus_DkMWB1odWfLCo5\"\r\n" + 
				"                            }\r\n" + 
				"                        ]\r\n" + 
				"                    }\r\n" + 
				"                ]\r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"    }\r\n" + 
				"}");
		checkMandatoryParams(mandatoryFields,mandatoryFieldsPath,pathCheckEnabled);
		
	}
	public static boolean checkMandatoryParams(String mandatoryFields, String mandatoryFieldsPath, String pathCheckEnabled) {

		boolean pathCheckRqrd = false;
		String paramValue = null;
		String request = null;
//		NGTableDataDTO db = null;
//		GetDataFromCache cache = null;

		try {
//			cache = new GetDataFromCache();
//			db = cache.getCacheDetailsFromDBMap("ERROR_CODE_MASTER", "Mandatory_Param");
			if (mandatoryFields != null) {

				String params[] = mandatoryFields.split(",");

				System.out.println(new StringBuilder().append("Mandatory Parameters expected : ")
						.append(Arrays.toString(params)).toString());

				if (pathCheckEnabled != null && !pathCheckEnabled.isEmpty()) {
					pathCheckRqrd = Boolean.valueOf(pathCheckEnabled);
				}

				System.out.println("Request Api path check required " + pathCheckRqrd);

				if (pathCheckRqrd) {
					String paramsPath[] = mandatoryFieldsPath.split(",");

					if ((get("MiddlewareRequest"))!=null) {
						request = get("MiddlewareRequest");

						for (int i = 0, j = 0; i < params.length && j < paramsPath.length; i++, j++) {

							try {

								System.out.println("params[i] :" + params[i] + " Value " + get(params[i]) + " paramsPath[j] "
										+ paramsPath[j]);

								if ((get(params[i]))!=null && (paramsPath[j] != null
										&& (paramsPath[j].isEmpty() || paramsPath[j].equalsIgnoreCase("0")
												|| paramsPath[j].equalsIgnoreCase("false")))) {
									System.out.println(new StringBuilder().append("156 Parameter : ")
											.append(params[i]).append(" present ").append(get(params[i])).toString());
								} else if (paramsPath[j].contains("[0]")) {
									System.out.println("159 paramsPath[j] :" + paramsPath[j]);
									String val = null;
									JSONArray arr = null;
									val = paramsPath[j].replaceAll("\\[0]", ".");
									System.out.println("163 paramsPath:" + val);
									arr = JsonPath.read(request, "$." + val);
									System.out.println("165 Array:" + arr);
									System.out.println("166 Array size:" + arr.size());

									for (int p = 0; p < arr.size(); p++) {
										if (arr.get(p) != null && !"".equals(arr.get(p).toString())) {
											System.out.println("170 Value " + arr.get(p).toString());
										} else {
											System.out.println(new StringBuilder()
													.append("173 Parameter : ").append(params[i]).append(" missing")
													.toString());
											//setError(db.getNgTableData().get("ERROR_CODE"));
											//genericDTO.getStrAttributes().put("missing_param", params[i]);
											return false;
										}
									}

								}

								else {

									paramValue = JsonPath.read(request, "$." + paramsPath[j]);

									System.out.println("187 param value " + paramValue);
									if ((paramValue)==null) {
										System.out.println(
												new StringBuilder().append("190 Parameter : ")
														.append(params[i]).append(" missing").toString());
										//setError(db.getNgTableData().get("ERROR_CODE"));
									//	genericDTO.getStrAttributes().put("missing_param", params[i]);
										return false;
									} else {
										System.out.println(new StringBuilder()
												.append("197 Parameter : ").append(params[i]).append(" present ")
												.append(paramValue).toString());
									}
								}
							} catch (Exception e) {
								System.out.println(new StringBuilder().append("202 Parameter : ")
										.append(params[i]).append(" missing").toString());
								//setError(db.getNgTableData().get("ERROR_CODE"));
								//genericDTO.getStrAttributes().put("missing_param", params[i]);
								return false;
							}

						}
					}
				} else {
					for (String param : params) {
						if ((get(param))==null) {
							System.out.println(new StringBuilder().append("214 Parameter : ")
									.append(param).append(" missing").toString());
//							setError(db.getNgTableData().get("ERROR_CODE"));
//							genericDTO.getStrAttributes().put("missing_param", param);
							return false;
						} else {
							System.out.println(new StringBuilder().append("220 Parameter:")
									.append(param).append(" present ").append(get(param)).toString());
						}
					}
				}
			} else {
				System.out.println(new StringBuilder().append("226 No mandatory params defined")
						.toString());
			}
		} catch (Throwable e) {
			System.out.println(new StringBuilder().append(" | Exception in callCP()").toString()
					);
			//setError();
		} finally {
			// responseObject = null;
		}

		return true;
	}

}
