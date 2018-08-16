package org.anhu.xsd;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Main {

	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
		final String dir = "C:\\Users\\310160231\\Documents\\dps-css\\trunk\\software\\shared\\xsd";
		// final String dir =
		// "C:\\Users\\s167710\\git\\xsd-chopper\\target\\xsd_example";
		// final String dir =
		// "C:\\Users\\anneke.huijsmans\\eclipse-workspace\\xsd-chopper\\target\\xsd_example";
		Informant sherlock = new Informant(dir);

		// System.out.println(sherlock.reportUnIncludedFiles());
		// System.out.println(sherlock.reportDoubleFiles());
		// System.out.println(sherlock.reportUnusedSimpleAndComplexTypes());
		// System.out.println(sherlock.reportFilesWithIdenticalInhoud());
		// System.out.println(sherlock.reportAllElementsWithFile());
		// System.out.println(sherlock.reportAllDataTypesFromIncludedXSDThatAreUsedExactlyOnce());
		//
		// final String dirdir =
		// "C:\\Users\\anneke.huijsmans\\eclipse-workspace\\xsd-chopper\\target";
		// final String file = "FilesWithATopLevelElement.txt";
		// sherlock.reportAndPrintAllFilesWithTopLevelElement(dirdir, file);

		/*
		 * fill in the name of the xsd you want the inhoud off, report will apear in
		 * target folder and is called singleFileReport.txt
		 */
		final String fileName = "BotSizeSchema.xsd";
		sherlock.reportSingleXSDToFile(fileName);

		/*
		 * fill in the name of the xsd you want the original inhoud off, report will
		 * appear in target folder and is called OriginalFileReport.txt final
		 */
		String fileName2 = "BotSizeSchema.xsd";
		sherlock.reportSingleXSDOriginalToFile(fileName2);

		/*
		 * output file can be found in target -> FileReport.txt specify in dirdir2 the
		 * directory of the file containing the files specify in file2 the filename of
		 * the file containing the files
		 */
		final String dirdir2 = "C:\\Users\\anneke.huijsmans\\eclipse-workspace\\xsd-chopper\\target";

		final String file2 = "filesToReport.txt";
		sherlock.reportTheseFiles(dirdir2, file2);

		/*
		 * output file can be found in target -> FileReport.txt specify in dirdir2 the
		 * directory of the file containing the files specify in file2 the filename of
		 * the file containing the files
		 */
		final String dirdir3 = "C:\\Users\\anneke.huijsmans\\eclipse-workspace\\xsd-chopper\\target";
		final String file3 = "filesToReport.txt";
		sherlock.reportTheseFilesOriginal(dirdir3, file3);

	}

}