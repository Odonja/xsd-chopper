package org.mahu.proto.xsdparser;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.anhu.xsd.elements.XSDFile;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SaxParser {
	public static void main(String[] args) {
		parse("entity.xml", new XSDFile("", ""));
	}

	public static void parse(String xmlDoc, XSDFile file) {
		System.out.println("Processing xsd=" + file.getLocation());
		try {
			// Next line may seem "complex", but my initial attempt
			// parse(new InputSource(new StringReader(xmlDoc)), file);
			// failed for some XSD's
			parse(new InputSource(new ByteArrayInputStream(xmlDoc.getBytes("UTF-8"))), file);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
			e.printStackTrace();
		}
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
