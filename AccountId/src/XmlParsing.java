import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlParsing {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
		
		String response ="<res:CredentialData>\r\n" + 
				"                  <res:CredentialItem>\r\n" + 
				"                     <CredentialType>PIN</CredentialType>\r\n" + 
				"                     <res:CredentialStatus>Normal</res:CredentialStatus>\r\n" + 
				"                     <res:ExpirationTime>20991231000000</res:ExpirationTime>\r\n" + 
				"                  </res:CredentialItem>\r\n" + 
				"                  <res:CredentialItem>\r\n" + 
				"                     <res:CredentialType>Secret word</res:CredentialType>\r\n" + 
				"                     <res:CredentialStatus>Normal</res:CredentialStatus>\r\n" + 
				"                     <res:ExpirationTime>20991231000000</res:ExpirationTime>\r\n" + 
				"                  </res:CredentialItem>\r\n" + 
				"               </res:CredentialData>";
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document xmlDoc =  db.parse(new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8)));
		if(response.contains("res:CredentialData"))
		{
			System.out.println(xmlDoc.getElementsByTagName("res:CredentialItem").item(0).getChildNodes().item(0));
			int length = xmlDoc.getElementsByTagName("res:CredentialItem").item(0).getChildNodes().getLength();
		     int data = xmlDoc.getElementsByTagName("res:CredentialData").item(0).getChildNodes().getLength();
		     
		      System.out.println("data length:" + data +" item length"+length);
		      for (int j = 0; j < data ; j++) 
		      {
	          
		    	  int l1 = xmlDoc.getElementsByTagName("res:CredentialItem").item(j).getChildNodes().getLength();
		    	  System.out.println("l1 is :" + l1);
		    	  System.out.println("lenght" + l1);
		    	  for (int k = 0; k < l1; k++) 
		    	  {
		    		 // String key = xmlDoc.getElementsByTagName("res:CredentialItem").item(l1).getChildNodes().item(l1).getChildNodes().item(0).getTextContent();
		    		  String key = xmlDoc.getElementsByTagName("res:CredentialItem").item(j).getChildNodes().toString();
		    		 // System.out.println("keys are :" + key);
		    		 
	            
	          	  } 
		      }
		}

	}

}
