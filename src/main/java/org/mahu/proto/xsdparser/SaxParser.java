package org.mahu.proto.xsdparser;

import java.io.StringReader;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SaxParser {
	public static void main(String[] args) {
		parse("entity.xml");
	}
	
	public static void parse(String xmlDoc) {
		parse(new InputSource(new StringReader(xmlDoc)));
	}	
	
	public static void parse(InputSource xmlDoc) {
		XMLReader parser;
		MySAXHandler msh;
		CustomResolver myResolver = new CustomResolver();
		try {
			parser = XMLReaderFactory.createXMLReader();
			msh = new MySAXHandler();
			parser.setContentHandler(msh);
			parser.setEntityResolver(myResolver);
			parser.parse(xmlDoc);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}	

}
