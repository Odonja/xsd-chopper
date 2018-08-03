package org.mahu.proto.xsdparser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.mahu.proto.xsdparser.SaxParser;
import org.xml.sax.SAXException;

public class SaxTest {

	@Test
	public void test() throws SAXException, ParserConfigurationException, IOException {
		String xmlSource = GetResource.getAsString("ColorProfileSchema.xsd");
		SaxParser.parse(xmlSource);
	}

}
