package org.mahu.proto.xsdparser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.anhu.xsd.Informant;
import org.anhu.xsd.TestApp;
import org.anhu.xsd.elements.XSDFile;
import org.junit.Test;
import org.xml.sax.SAXException;

public class SaxTest {

	@Test
	public void test() throws SAXException, ParserConfigurationException, IOException {
		String fileName = "ColorProfileSchema.xsd";
		String xmlSource = GetResource.getAsString(fileName);

		String location = TestApp.getTargetDirectory(Informant.class) + "\\" + fileName;

		XSDFile xsd = new XSDFile(fileName, location);
		SaxParser.parse(xmlSource, xsd);
		System.out.println(xsd + "\n" + xsd.getLocation());
	}

}
