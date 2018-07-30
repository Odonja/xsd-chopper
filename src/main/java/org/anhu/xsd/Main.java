package org.anhu.xsd;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.anhu.xsd.findFiles.RetrieveFiles;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {
		String dir = TestApp.getTargetDirectory(Main.class);
		List<Path> files = RetrieveFiles.findXSDFiles(dir);
		Chopper chopper = new Chopper();
		for (Path file : files) {
			System.out.println(chopper.chopFile(file.toString()));
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
	}

}
