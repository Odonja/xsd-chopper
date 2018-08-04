package org.mahu.proto.xsdparser;

import java.io.StringReader;

import org.anhu.xsd.elements.XSDFile;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SaxParser {
	public static void main(String[] args) {
		parse("entity.xml", new XSDFile("", ""));
	}

	public static void parse(String xmlDoc, XSDFile file) {
		parse(new InputSource(new StringReader(xmlDoc)), file);
	}

	public static void parse(InputSource xmlDoc, XSDFile file) {
		XMLReader parser;
		MySAXHandler msh;
		CustomResolver myResolver = new CustomResolver();
		try {
			parser = XMLReaderFactory.createXMLReader();
			msh = new MySAXHandler(file);
			parser.setContentHandler(msh);
			parser.setEntityResolver(myResolver);
			parser.parse(xmlDoc);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

}
