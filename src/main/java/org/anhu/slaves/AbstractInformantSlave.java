package org.anhu.slaves;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.anhu.xsd.TestApp;
import org.anhu.xsd.elements.XSDFile;

public abstract class AbstractInformantSlave {

	private final List<XSDFile> xsdFiles;
	private final List<String> fileNames;
	private List<String> includes;
	private List<String> unfoundIncludes;

	public AbstractInformantSlave(final List<XSDFile> xsdFiles) {
		this.xsdFiles = xsdFiles;
		this.fileNames = new ArrayList<>();
		includes = new ArrayList<>();
		unfoundIncludes = new ArrayList<>();
	}

	public final void report(String fileName) {
		fileNames.add(fileName);
		findAndSortIncludes();
		startReport();
	}

	public final void reportAll(List<String> fileNames) {
		this.fileNames.addAll(fileNames);
		findAndSortIncludes();
		startReport();
	}

	private final void findAndSortIncludes() {
		for (String file : fileNames) {
			findIncludes(file);
		}
		int current = 0;
		while (current < includes.size()) {
			String file = includes.get(current);
			findIncludes(file);
			current++;
		}
		fileNames.sort(String.CASE_INSENSITIVE_ORDER);
		includes.sort(String.CASE_INSENSITIVE_ORDER);
	}

	private final void findIncludes(String file) {
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

	protected void startReport() {
		String dir = "";
		try {
			dir = TestApp.getTargetDirectory(AbstractInformantSlave.class);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String outputFile = dir + getOutputFileName();
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

	protected abstract String getOutputFileName();

	private final void reportFiles(PrintWriter writer) {
		System.out.println(
				"------top-level XSD's---------------------------------------------------------------------------------------------------------------------------");
		writer.println(
				"------top-level XSD's---------------------------------------------------------------------------------------------------------------------------");
		for (String currentFile : fileNames) {
			writeSingleFile(currentFile, writer);
		}
		System.out.println(
				"------common XSD's---------------------------------------------------------------------------------------------------------------------------");
		writer.println(
				"------common XSD's---------------------------------------------------------------------------------------------------------------------------");
		for (String currentFile : includes) {
			writeSingleFile(currentFile, writer);
		}
		System.out.println(
				"------not found included XSD's---------------------------------------------------------------------------------------------------------------------------");
		writer.println(
				"------not found included XSD's---------------------------------------------------------------------------------------------------------------------------");
		for (String currentFile : unfoundIncludes) {
			printUnfoundInclude(currentFile, writer);
		}
	}

	private final void writeSingleFile(String file, PrintWriter writer) {
		XSDFile xsdFile = fileNameToObject(file);
		if (xsdFile == null) {
			unfoundIncludes.add(file);
		} else {
			writer.println("---------------------------------------");
			writer.println(xsdFile.getName());
			System.out.println(xsdFile.getName());
			writer.println("");
			doFileWriting(xsdFile, writer);
			writer.println("");
		}
	}

	private final void printUnfoundInclude(String file, PrintWriter writer) {
		System.out.println(file + " is not present in informant memory -> skipped");
		writer.println(file + " is not present in informant memory -> skipped");
		System.out.println("It is included in files: ");
		writer.println("It is included in files: ");
		for (String s : findWhereIncluded(file)) {
			System.out.println(s);
			writer.println(s);
		}

	}

	protected void doFileWriting(XSDFile xsdFile, PrintWriter writer) {
	}

	protected final XSDFile fileNameToObject(String fileName) {
		for (XSDFile xsdFile : xsdFiles) {
			if (xsdFile.getName().equals(fileName)) {
				return xsdFile;
			}
		}
		return null;
	}

	protected List<String> getIncludes() {
		return includes;
	}

	protected List<String> getFileNames() {
		return fileNames;
	}

	protected List<String> findWhereIncluded(String file) {
		List<String> whereIncluded = new ArrayList<>();
		for (XSDFile xsdFile : xsdFiles) {
			if (xsdFile.isListedInInclude(file)) {
				whereIncluded.add(xsdFile.getLocation());
			}
		}
		return whereIncluded;
	}

}
