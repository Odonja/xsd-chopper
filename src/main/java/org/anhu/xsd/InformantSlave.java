package org.anhu.xsd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.anhu.xsd.elements.XSDFile;

public class InformantSlave {

	private final List<XSDFile> xsdFiles;
	private final List<String> fileNames;
	private List<String> includes;

	public InformantSlave(final List<XSDFile> xsdFiles) {
		this.xsdFiles = xsdFiles;
		this.fileNames = new ArrayList<>();
		includes = new ArrayList<>();
	}

	public void report(String fileName) {
		fileNames.add(fileName);
		findAndSortIncludes();
		startReport();
	}

	public void reportAll(List<String> fileNames) {
		this.fileNames.addAll(fileNames);
		findAndSortIncludes();
		startReport();
	}

	private void findAndSortIncludes() {
		for (String file : fileNames) {
			findIncludes(file);
		}
		int current = 0;
		while (current < includes.size()) {
			String file = includes.get(current);
			findIncludes(file);
			current++;
		}
		includes.sort(String.CASE_INSENSITIVE_ORDER);
	}

	private void findIncludes(String file) {
		XSDFile xsdFile = fileNameToObject(file);
		if (xsdFile == null) {
			System.out.println(file + " is not present in informant memory -> skipping");
		} else {
			List<String> xsdincludes = xsdFile.getIncludeNames();
			for (String i : xsdincludes) {
				if (!fileNames.contains(i) && !includes.contains(i)) {
					includes.add(i);
					System.out.println(file + " contains include " + i);
				}
			}
		}
	}

	private void startReport() {
		String dir = "";
		try {
			dir = TestApp.getTargetDirectory(InformantSlave.class);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String outputFile = dir + "\\FileReport.txt";
		File file = new File(outputFile);
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.println("---start---");
			reportFiles(writer);
			writer.println("---end---");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void reportFiles(PrintWriter writer) {
		for (String currentFile : fileNames) {
			writeSingleFile(currentFile, writer);
		}
		for (String currentFile : includes) {
			writeSingleFile(currentFile, writer);
		}

	}

	private void writeSingleFile(String file, PrintWriter writer) {
		XSDFile xsdFile = fileNameToObject(file);
		if (xsdFile == null) {
			writer.println(file + " is not present in informant memory -> skipping");
		} else {
			writer.println("---------------------------------------");
			writer.println(xsdFile.getName());
			writer.println("");
			xsdFile.writeYourselfToFile(writer);
			writer.println("");
		}
	}

	private XSDFile fileNameToObject(String fileName) {
		for (XSDFile xsdFile : xsdFiles) {
			if (xsdFile.getName().equals(fileName)) {
				return xsdFile;
			}
		}
		return null;
	}

}
