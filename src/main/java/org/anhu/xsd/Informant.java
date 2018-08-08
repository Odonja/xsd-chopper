package org.anhu.xsd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.anhu.xsd.chopFiles.Chopper;
import org.anhu.xsd.chopFiles.RetrieveFiles;
import org.anhu.xsd.elements.Element;
import org.anhu.xsd.elements.XSDFile;

public class Informant {

	private final List<XSDFile> xsdFiles;

	public Informant(String dir) throws UnsupportedEncodingException {
		xsdFiles = new ArrayList<>();
		List<Path> files = RetrieveFiles.findXSDFiles(dir);
		chopFiles(files);
	}

	public Informant(String dir, String thefile) {
		xsdFiles = new ArrayList<>();
		List<Path> files = readFilesFromFile(dir, thefile);
		chopFiles(files);
	}

	private List<Path> readFilesFromFile(String dir, String thefile) {
		List<Path> files = new ArrayList<>();
		String fileLocation = dir + "\\" + thefile;
		File file = new File(fileLocation);

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((fileLocation = br.readLine()) != null) {
				files.add(Paths.get(dir + "\\" + fileLocation));
			}
			br.close();

		} catch (FileNotFoundException e) {
			System.out.println("Informant constructor, file: " + fileLocation + " was not found.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return files;
	}

	private void chopFiles(List<Path> files) {
		Chopper chopper = new Chopper();
		for (Path file : files) {
			try {
				XSDFile xsdFile = chopper.chopFile(file.toString());
				xsdFiles.add(xsdFile);
			} catch (IllegalArgumentException e) {
				System.out.println("exception on file: " + file.toString());
				e.printStackTrace();
			}

		}
	}

	public String reportUnIncludedFiles() {
		StringBuilder str = new StringBuilder();
		str.append(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		str.append("The following files are not present in the include of any file:\n");
		for (XSDFile xsdFile : xsdFiles) {
			boolean notIncluded = true;
			for (XSDFile xsd : xsdFiles) {
				String name = xsdFile.getName();
				if (xsd.isListedInInclude(name)) {
					notIncluded = false;
					break;
				}
			}
			if (notIncluded && xsdFile.hasNoElement()) {
				str.append(xsdFile.getLocation() + "\n");
			}
		}
		str.append(
				"\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		return str.toString();
	}

	public String reportDoubleFiles() {
		List<XSDFile> doubles = new ArrayList<>();
		for (XSDFile xsdFile : xsdFiles) {
			for (XSDFile xsd : xsdFiles) {
				if (xsdFile.getName().equals(xsd.getName()) && !xsdFile.getLocation().equals(xsd.getLocation())) {
					if (!doubles.contains(xsdFile)) {
						doubles.add(xsdFile);
					}
					if (!doubles.contains(xsd)) {
						doubles.add(xsd);
					}
				}
			}

		}
		StringBuilder str = new StringBuilder();
		str.append(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		str.append("The following files appear multiple times:\n");
		for (XSDFile xsdFile : doubles) {
			str.append(xsdFile.getLocation() + "\n");
		}
		str.append(
				"\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		return str.toString();
	}

	public String reportUnusedSimpleAndComplexTypes() {
		StringBuilder str = new StringBuilder();
		str.append(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		str.append("The following simple and complex types are not used in any file:\n");

		for (XSDFile xsdFile : xsdFiles) {
			List<Element> types = xsdFile.getSimpleAndComplexTypes();
			for (Element type : types) {
				boolean isUsed = false;
				if (type.getName() != null) {
					isUsed = searchForUse(xsdFile, type);
					if (!isUsed) {
						str.append("Unused " + type.getElementtype() + " " + type.getName() + " in file: "
								+ xsdFile.getLocation() + "\n");
					}
				}
			}
		}
		str.append(
				"\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		return str.toString();

	}

	public String reportFilesWithIdenticalInhoud() {
		StringBuilder str = new StringBuilder();
		str.append(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		str.append("The following files have identical inhoud:\n");

		for (XSDFile xsdFile : xsdFiles) {
			String inhoud = xsdFile.identifier();
			boolean startFound = false;
			for (XSDFile xsd : xsdFiles) {
				if (startFound) {
					if (inhoud.equals(xsd.identifier())) {
						str.append(xsdFile.getLocation() + "   and    " + xsd.getLocation() + "\n");
					}
				} else if (xsd == xsdFile) {
					startFound = true;
				}
			}

		}

		str.append(
				"\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		return str.toString();
	}

	public String reportAllElementsWithFile() {
		StringBuilder str = new StringBuilder();
		str.append(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		str.append("Elements with their file:\n");

		for (XSDFile xsdFile : xsdFiles) {
			xsdFile.appendAllElements(str);
			str.append("\n");
		}

		str.append(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		return str.toString();
	}

	private boolean searchForUse(XSDFile xsdFile, Element type) {
		boolean isUsed;
		isUsed = xsdFile.usesType(type.getName());
		if (!isUsed) {
			for (XSDFile xsd : xsdFiles) {
				if (xsd.usesType(type.getName())) {
					isUsed = true;
					break;
				}
			}
		}
		return isUsed;
	}

	public void reportAndPrintAllFilesWithTopLevelElement(String directory, String fileName) {
		String filelocation = directory + "\\" + fileName;
		File file = new File(filelocation);
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.println(
					"--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			writer.println("Files that have a top level element:");

			for (XSDFile xsdFile : xsdFiles) {
				if (xsdFile.hasTopLevelElement()) {
					String location = xsdFile.getLocation().substring(directory.length() + 1);
					writer.println(location);
				}
			}
			writer.println(
					"--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void reportSingleXSDToFile(String fileName) {
		if (fileNameToObject(fileName) == null) {
			throw new IllegalArgumentException("reportSingleXSDToFile: file not in memory " + fileName);
		}
		String dir = "";
		try {
			dir = TestApp.getTargetDirectory(Informant.class);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String outputFile = dir + "\\singleFileReport.txt";
		File file = new File(outputFile);
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.println("---start---");
			writeFileWithIncludesToFile(fileName, writer);
			writer.println("---end---");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void writeFileWithIncludesToFile(String fileName, PrintWriter writer) {
		List<String> todo = new ArrayList<>();
		List<String> done = new ArrayList<>();// to prevent looping
		todo.add(fileName);

		while (!todo.isEmpty()) {
			String currentFile = todo.get(0);
			todo.remove(0);
			done.add(currentFile);
			XSDFile xsdFile = fileNameToObject(currentFile);
			if (xsdFile == null) {
				writer.println(fileName + " is not present in informant memory -> skipping");
			}
			writeSingleFile(xsdFile, writer);
			List<String> includes = xsdFile.getIncludeNames();
			for (String i : includes) {
				if (!done.contains(i)) {
					todo.add(i);
				}
			}
		}

	}

	private void writeSingleFile(XSDFile xsd, PrintWriter writer) {
		writer.println(xsd.getName());
		writer.println("");
		xsd.writeYourselfToFile(writer);
		writer.println("");
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
