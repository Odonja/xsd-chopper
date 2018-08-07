package org.anhu.xsd;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Main {

	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
//		final String dir = "C:\\Users\\310160231\\Documents\\dps-css\\trunk\\software\\shared\\xsd";
//		final String dir = "C:\\Users\\s167710\\git\\xsd-chopper\\target\\xsd_example";
		final String dir = "C:\\Users\\anneke.huijsmans\\eclipse-workspace\\xsd-chopper\\target\\xsd_example";
		Informant sherlock = new Informant(dir);

		// of
		// final String dir = "C:\\a\\b\\c";
		// final String file = "d.xsd";
		// Informant sherlock = new Informant(dir, file);

		System.out.println(sherlock.reportUnIncludedFiles());
		System.out.println(sherlock.reportDoubleFiles());
		System.out.println(sherlock.reportUnusedSimpleAndComplexTypes());
		System.out.println(sherlock.reportFilesWithIdenticalInhoud());
		System.out.println(sherlock.reportAllElementsWithFile());

		sherlock.reportAndPrintAllFilesWithTopLevelElement();

	}

}