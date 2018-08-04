package org.anhu.xsd;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Main {

	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
		final String dir2 = "C:\\Users\\310160231\\Documents\\dps-css\\trunk\\software\\shared\\xsd";
		final String dir1 = "C:\\Users\\s167710\\git\\xsd-chopper\\target\\xsd_example";

		Informant sherlock = new Informant(dir1);
		System.out.println(sherlock.reportUnIncludedFiles());
		System.out.println(sherlock.reportDoubleFiles());
	}

}