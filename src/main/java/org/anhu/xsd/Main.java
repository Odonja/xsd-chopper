package org.anhu.xsd;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

//import javax.xml.parsers.ParserConfigurationException;
//
//import org.w3c.dom.bootstrap.DOMImplementationRegistry;
//import org.xml.sax.SAXException;

public class Main {

	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
		Informant sherlock = new Informant();
		System.out.println(sherlock.reportUnIncludedFiles());
		System.out.println(sherlock.reportDoubleFiles());
	}

}

// DocumentBuilderFactory docBuilderFactory =
// DocumentBuilderFactory.newInstance();
// DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
//
// for (Path file : files) {
// Document doc = docBuilder.parse(new File(file.toString()));
// NodeList list = doc.getElementsByTagName("xs:element");
// // loop to print data
// for (int i = 0; i < list.getLength(); i++) {
// Element first = (Element) list.item(i);
// if (first.hasAttributes()) {
// String nm = first.getAttribute("name");
// System.out.println(nm);
// String nm1 = first.getAttribute("type");
// System.out.println(nm1);
// }
// }
//
// }

// String test = " some words in a line ";
// test = test.trim().replaceAll("\\s+", " ");
// System.out.println(test);