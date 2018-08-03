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

//		System.setProperty(DOMImplementationRegistry.PROPERTY,
//				"com.sun.org.apache.xerces.internal.dom.DOMXSImplementationSourceImpl");
//		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
//		com.sun.org.apache.xerces.internal.impl.xs.XSImplementationImpl impl = (com.sun.org.apache.xerces.internal.impl.xs.XSImplementationImpl) registry
//				.getDOMImplementation("XS-Loader");
//		com.sun.org.apache.xerces.internal.xs.XSLoader schemaLoader = impl.createXSLoader(null);
//		com.sun.org.apache.xerces.internal.xs.XSModel model = schemaLoader.loadURI(
//				"C:\\Users\\anneke.huijsmans\\eclipse-workspace\\xsd-chopper\\target\\xsd_example\\CommonDataTypesSchema.xsd");
	}

}

//		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
//
//		for (Path file : files) {
//			Document doc = docBuilder.parse(new File(file.toString()));
//			NodeList list = doc.getElementsByTagName("xs:element");
//			// loop to print data
//			for (int i = 0; i < list.getLength(); i++) {
//				Element first = (Element) list.item(i);
//				if (first.hasAttributes()) {
//					String nm = first.getAttribute("name");
//					System.out.println(nm);
//					String nm1 = first.getAttribute("type");
//					System.out.println(nm1);
//				}
//			}
//
//		}