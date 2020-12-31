import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;




public class SchemaPractise {
	static Map<String, JsonSchema>	map			= new HashMap<String, JsonSchema>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			SchemaPractise sp = new SchemaPractise();
			
			String WFRequest=readUsingScanner("src/Schemas/UpdateOrder/ReqUpdateOrder.txt");
			System.out.println(WFRequest);
	
			WFRequest=WFRequest.replaceAll("\\p{Cc}", "");
			System.out.println(WFRequest);
			sp.validateRequest(WFRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static String readUsingScanner(String fileName)
	{
		StringBuilder sb = new StringBuilder();
		try {
		FileInputStream fis = new FileInputStream(fileName);
		byte[] buffer = new byte[10];
		while (fis.read(buffer) != -1) {
			sb.append(new String(buffer));
			buffer = new byte[10];
		}
		fis.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String content = sb.toString();
		return content;
	}
	public synchronized  JsonSchema loadSchema( String feature) throws Exception {
		JsonSchema schema = null;
		String schemaPath = null;
		File file = null;
		InputStream resource = null;
		try {
			if ( feature!=null) {
				schema = map.get(feature + "_");
				if (schema == null) {
			ObjectMapper mapper = new ObjectMapper();
					JsonSchemaFactory factory = JsonSchemaFactory
							.builder(JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7)).objectMapper(mapper).build();
					schemaPath = "Schemas/UpdateOrder/UpdateOrder.json";
					// ClassLoader classLoader = getClass().getClassLoader();


						System.out.println("Loading schema from class path: " + schemaPath);
						resource = new ClassPathResource(schemaPath).getInputStream();
						
					if (resource!=null) {
						schema = factory.getSchema(resource);
						//schema=factory.getSchema(""); can pass string it will work
						System.out.println(" schema loaded sucessfully :: ");
						if (Objects.nonNull(schema)) {
							map.put(feature + "_" , schema);
						}
					}
				}
			} else {
				System.out.println(" Schema key recieved as null ");
			}
		} catch (FileNotFoundException fe) {
			System.out.println(" Json Schema validation file Not found  for action " + fe.getMessage());
			throw fe;
		} catch (Exception e) {
			System.out.println("Error loading tranformation : " + e.getMessage());
		}
		return schema;
	}

	public boolean validateRequest( String request)   {
		JsonSchema schema = null;
		com.fasterxml.jackson.databind.JsonNode node = null;
		boolean isValidRequest = true;
		StringBuilder missingParams=new StringBuilder();
		try {
			node = new ObjectMapper().readTree(request);
			schema = loadSchema("AddSubscription" );
			if (schema==null) {
				System.out.println(  "| No schema found");
				return false;
			}
		//	final List<WorkFlowErrorBean> errorList = new ArrayList<WorkFlowErrorBean>();
			Set<ValidationMessage> errors = schema.validate(node);
			if (!ObjectUtils.isEmpty(errors)) {
				System.out.println( " | Request  Validation Failed!");
				errors.parallelStream().forEach((ValidationMessage message) -> {
					//WorkFlowErrorBean workflowError = new WorkFlowErrorBean();
					String[] errorfield = message.getMessage().split(":");
			System.out.println("120"+ errorfield[0].substring(errorfield[0].lastIndexOf(".") + 1) + "'" + errorfield[1]+"__"+errorfield[0].substring(2));
			
				});
				for (ValidationMessage message:errors)
				{
					//System.out.println("foreach");
					System.out.println(message.getMessage());
					System.out.println(message.getCode());
					System.out.println(message.getPath());// tells u something is missing in param array, I can use this as error desc in final response
					System.out.println(message.getType());
					//System.out.println(message.getDetails());
					switch (message.getType())
					{
					case "required":
						missingParams.append(message.getMessage()).append("|");
						break;
					case "pattern":
						missingParams.append(message.getPath()).append("|");
						break;
					case "contains":
						if(message.getMessage().contains("order"))
						missingParams.append(JsonPath.read(message.getMessage().substring(message.getMessage().indexOf("{")),"$.properties.id.pattern" ).toString().substring(1,9)).append("|");
						else
							missingParams.append("Please check the element inside this array ").append(message.getPath());
						break;
					case "type":
						missingParams.append(message.getMessage()).append("|");
						break;
						default:
							missingParams.append(message.getMessage()).append("|");
							break;
					}
					
				}
				System.out.println("error_desc "+missingParams.toString().substring(0, missingParams.toString().length()-1));
				/*genericDTO.setErrors(errorList);
				genericDTO.setTransactionStatus(1);
				genericDTO.setResponse(createErrorResponse(genericDTO));*/
				isValidRequest = false;
			}
			else
				System.out.println("valid request");
		} catch (FileNotFoundException fe) {
			System.out.println(" Json Schema validation file Not found " + fe.getMessage());
			isValidRequest = false;
		} catch (Exception e) {
			isValidRequest = false;
			/*genericDTO.setTransactionStatus(1);
			genericDTO.setResponse(createErrorResponse(genericDTO));*/
			System.out.println( " | Exception occured in validateSchema()"+e.getMessage());
		} finally {
			schema = null;
			node = null;
		}
		return isValidRequest;

	}


}
