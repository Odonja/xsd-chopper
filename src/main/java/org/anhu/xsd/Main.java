package org.anhu.xsd;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.anhu.xsd.findFiles.RetrieveFiles;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {
	private static final String STARTDIRECTORY = "C:\\Users\\anneke.huijsmans\\Documents";

	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {
		List<Path> files = RetrieveFiles.findXSDFiles(STARTDIRECTORY);
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

		for (Path file : files) {
			Document doc = docBuilder.parse(new File(file.toString()));
			NodeList list = doc.getElementsByTagName("xsd:element");
			// loop to print data
			for (int i = 0; i < list.getLength(); i++) {
				Element first = (Element) list.item(i);
				if (first.hasAttributes()) {
					String nm = first.getAttribute("name");
					System.out.println(nm);
					String nm1 = first.getAttribute("type");
					System.out.println(nm1);
				}
			}

		}
	}
}
