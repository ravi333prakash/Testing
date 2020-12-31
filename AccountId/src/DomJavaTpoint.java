import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomJavaTpoint {
public static void main(String[] args) {
	try {
	String response="<?xml version=\"1.0\"?>  \r\n" + 
			"<class>  \r\n" + 
			"    <student>  \r\n" + 
			"        <id>101</id>  \r\n" + 
			"        <firstname>Naman</firstname>  \r\n" + 
			"        <lastname>Kumar</lastname>  \r\n" + 
			"        <subject>Math</subject>  \r\n" + 
			"        <marks>83</marks>  \r\n" + 
			"    </student>  \r\n" + 
			"      \r\n" + 
			"    <student>  \r\n" + 
			"        <id>102</id>  \r\n" + 
			"        <firstname>Kapil</firstname>  \r\n" + 
			"        <lastname>Kumar</lastname>  \r\n" + 
			"        <subject>Chemistry</subject>  \r\n" + 
			"        <marks>60</marks>  \r\n" + 
			"    </student>  \r\n" + 
			"  \r\n" + 
			"    <student>  \r\n" + 
			"        <id>103</id>  \r\n" + 
			"        <firstname>Harsh</firstname>  \r\n" + 
			"        <lastname>Singh</lastname>  \r\n" + 
			"        <subject>English</subject>  \r\n" + 
			"        <marks>70</marks>  \r\n" + 
			"    </student>  \r\n" + 
			"  \r\n" + 
			"    <student>  \r\n" + 
			"        <id>104</id>  \r\n" + 
			"        <firstname>Jitesh</firstname>  \r\n" + 
			"        <lastname>Singh</lastname>  \r\n" + 
			"        <subject>Physics</subject>  \r\n" + 
			"        <marks>76</marks>  \r\n" + 
			"    </student>  \r\n" + 
			"  \r\n" + 
			"</class>  ";
	response="<res:CredentialData>\r\n" + 
			"                  <res:CredentialItem>\r\n" + 
			"                     <res:CredentialType>PIN</res:CredentialType>\r\n" + 
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
	//an instance of builder to parse the specified xml file  
	DocumentBuilder db = dbf.newDocumentBuilder();  
	Document doc = db.parse(new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8))); 
	doc.getDocumentElement().normalize();  
	System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
	NodeList nodeList = doc.getElementsByTagName("res:CredentialItem");  
	// nodeList is not iterable, so we are using for loop  
	for (int itr = 0; itr < nodeList.getLength(); itr++)   
	{  
	Node node = nodeList.item(itr);  
	System.out.println("\nNode Name :" + node.getNodeName());  
	if (node.getNodeType() == Node.ELEMENT_NODE)   
	{  
	Element eElement = (Element) node;  
	System.out.println("Student id: "+ eElement.getElementsByTagName("res:CredentialType").item(0).getTextContent());  
	System.out.println("First Name: "+ eElement.getElementsByTagName("res:CredentialStatus").item(0).getTextContent());  
	System.out.println("Last Name: "+ eElement.getElementsByTagName("res:ExpirationTime").item(0).getTextContent());  
	//System.out.println("Subject: "+ eElement.getElementsByTagName("subject").item(0).getTextContent());  
	//System.out.println("Marks: "+ eElement.getElementsByTagName("marks").item(0).getTextContent());  
	}  
	}  
	}  
	catch (Exception e)   
	{  
	e.printStackTrace();  
	} 
}
	 
	  
}

