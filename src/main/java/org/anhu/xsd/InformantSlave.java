package org.anhu.xsd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	public void reportOriginal(String fileName) {
		fileNames.add(fileName);
		findAndSortIncludes();
		startReportOriginal();
	}

	public void reportAllOriginal(List<String> fileNames) {
		this.fileNames.addAll(fileNames);
		findAndSortIncludes();
		startReportOriginal();
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

	private void startReportOriginal() {
		String dir = "";
		try {
			dir = TestApp.getTargetDirectory(InformantSlave.class);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd-HHmm");
		String outputFile = dir + "\\OriginalFileReport" + dateFormat.format(new Date()) + ".txt";
		File file = new File(outputFile);
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.println("---start---");
			reportFilesOriginal(writer);
			writer.println("---end---");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void startReport() {
		String dir = "";
		try {
			dir = TestApp.getTargetDirectory(InformantSlave.class);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd-HHmm");

		String outputFile = dir + "\\FileReport" + dateFormat.format(new Date()) + ".txt";
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

	private void reportFilesOriginal(PrintWriter writer) {
		for (String currentFile : fileNames) {
			writeSingleFileOriginal(currentFile, writer);
		}
		for (String currentFile : includes) {
			writeSingleFileOriginal(currentFile, writer);
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

	private void writeSingleFileOriginal(String file, PrintWriter writer) {
		XSDFile xsdFile = fileNameToObject(file);
		if (xsdFile == null) {
			writer.println(file + " is not present in informant memory -> skipping");
		} else {
			writer.println("---------------------------------------");
			writer.println(xsdFile.getName());
			writer.println("");
			String location = xsdFile.getLocation();
			File openfile = new File(location);
			BufferedReader br;
			String line;
			try {
				br = new BufferedReader(new FileReader(openfile));
				while ((line = br.readLine()) != null) {
					writer.println(line);
				}
				br.close();
				writer.println("");
			} catch (FileNotFoundException e) {
				System.out.println("readFileNamesFromFile, file: " + location + " was not found.");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
