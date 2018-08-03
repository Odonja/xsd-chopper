package org.mahu.proto.xsdparser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.mahu.proto.xsdparser.DomXmlParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomTest {

     @Test
     public void test() throws SAXException, ParserConfigurationException, IOException   {
    	String xmlSource = GetResource.getAsString("ColorProfileSchema.xsd");
		Document doc =  DomXmlParser.stringToDom(xmlSource);
		doSomething(doc.getDocumentElement());
     }
     
     public static void doSomething(Node node) {
    	    // do something with the current node instead of System.out
    	    System.out.println(node.getNodeName());

    	    NodeList nodeList = node.getChildNodes();
    	    for (int i = 0; i < nodeList.getLength(); i++) {
    	        Node currentNode = nodeList.item(i);
    	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
    	            //calls this method for all the children which is Element
    	            doSomething(currentNode);
    	        }
    	    }
    	}

}
