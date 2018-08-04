package org.anhu.xsd.chopFiles;

import java.io.IOException;

import org.anhu.xsd.TestApp;
import org.anhu.xsd.elements.XSDFile;
import org.mahu.proto.xsdparser.SaxParser;

public class Chopper {

	public XSDFile chopFile(String location) {
		if (!location.endsWith(".xsd")) {
			throw new IllegalArgumentException("Chopper chopFile: file" + location + " is not an .xsd file");
		}

		int start = location.lastIndexOf("\\") + 1;
		String name = location.substring(start);
		XSDFile xsd = null;

		try {
			xsd = new XSDFile(name, location);
			String xmlSource = TestApp.getAsString(location);
			SaxParser.parse(xmlSource, xsd);
		} catch (IOException e) {
			System.out.println("error on file " + location);
			e.printStackTrace();
		}
		return xsd;
	}

}
