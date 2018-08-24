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

import org.anhu.slaves.AbstractInformantSlave;
import org.anhu.slaves.ElementTxtInformantSlave;
import org.anhu.slaves.OriginalTxtInformantSlave;
import org.anhu.slaves.OriginalWordInformantSlave;
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

	public Informant(String[] dirs) {
		xsdFiles = new ArrayList<>();
		for (String dir : dirs) {
			List<Path> files = RetrieveFiles.findXSDFiles(dir);
			chopFiles(files);
		}
	}

	public void reportTopAndCommon() {
		List<String> files = new ArrayList<>();
		for (XSDFile xsdFile : xsdFiles) {
			if (!isIncluded(xsdFile)) {
				files.add(xsdFile.getName());
				System.out.println("adding not included file:" + xsdFile.getName());
			}
		}
		reportTheseFilesOriginal(files);
	}

	public void reportTheseFiles(String directory, String thefile) {
		List<String> files = readFileNamesFromFile(directory, thefile);
		reportTheseFiles(files);
	}

	public void reportTheseFiles(List<String> files) {
		AbstractInformantSlave mySlave = new ElementTxtInformantSlave(xsdFiles);
		mySlave.reportAll(files);
	}

	public void reportTheseFilesOriginal(String directory, String thefile) {
		List<String> files = readFileNamesFromFile(directory, thefile);
		reportTheseFilesOriginal(files);
	}

	public void reportTheseFilesOriginal(List<String> files) {
		AbstractInformantSlave mySlave = new OriginalTxtInformantSlave(xsdFiles);
		mySlave.reportAll(files);
	}

	public void reportTheseFilesOriginalToWord(String directory, String thefile) {
		List<String> files = readFileNamesFromFile(directory, thefile);
		reportTheseFilesOriginalToWord(files);
	}

	public void reportTheseFilesOriginalToWord(List<String> files) {
		AbstractInformantSlave mySlave = new OriginalWordInformantSlave(xsdFiles);
		mySlave.reportAll(files);
	}

	private List<String> readFileNamesFromFile(String dir, String thefile) {
		List<String> files = new ArrayList<>();
		String fileLocation = dir + "\\" + thefile;
		File file = new File(fileLocation);

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((fileLocation = br.readLine()) != null) {
				String filename = Paths.get(dir + "\\" + fileLocation).getFileName().toString();
				System.out.println("readFileNamesFromFile : found " + filename);
				files.add(filename);
			}
			br.close();

		} catch (FileNotFoundException e) {
			System.out.println("readFileNamesFromFile, file: " + fileLocation + " was not found.");
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
			if (!isIncluded(xsdFile) && xsdFile.hasNoElement()) {
				str.append(xsdFile.getLocation() + "\n");
			}
		}
		str.append(
				"\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		return str.toString();
	}

	private boolean isIncluded(XSDFile xsdFile) {
		for (XSDFile xsd : xsdFiles) {
			String name = xsdFile.getName();
			if (xsd.isListedInInclude(name)) {
				return true;
			}
		}
		return false;
	}

	private List<XSDFile> findWhereIncluded(XSDFile xsdFile) {
		List<XSDFile> here = new ArrayList<>();
		for (XSDFile xsd : xsdFiles) {
			String name = xsdFile.getName();
			if (xsd.isListedInInclude(name)) {
				here.add(xsd);
			}
		}
		return here;
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

	public String reportAllDataTypesFromIncludedXSDThatAreUsedExactlyOnce() {

		StringBuilder str = new StringBuilder();
		str.append(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		str.append("The following types are used exactly once:\n");
		for (XSDFile xsdFile : xsdFiles) {
			List<XSDFile> isincluded = findWhereIncluded(xsdFile);
			List<Element> types = xsdFile.getSimpleAndComplexTypes();
			for (Element type : types) {
				int nrOfUses = 0;
				XSDFile f = null;
				if (type.getName() != null) {
					for (XSDFile isincl : isincluded) {
						if (isincl.usesType(type.getName())) {
							f = isincl;
							nrOfUses++;
						}
					}
				}

				if (nrOfUses == 1) {
					str.append(type.getElementtype() + " " + type.getName() + "\n in file: " + xsdFile.getLocation()
							+ "\n is only used in " + f.getLocation() + "\n");
				}

			}
		}
		str.append(
				"\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		return str.toString();

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
			reportSingleXSDToFile(fileName, writer);
			writer.println("---end---");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void reportSingleXSDOriginalToTxtFile(String fileName) {
		AbstractInformantSlave mySlave = new OriginalTxtInformantSlave(xsdFiles);
		mySlave.report(fileName);
	}

	public void reportSingleXSDOriginalToWordFile(String fileName) {
		AbstractInformantSlave mySlave = new OriginalWordInformantSlave(xsdFiles);
		mySlave.report(fileName);
	}

	private void reportSingleXSDToFile(String fileName, PrintWriter writer) {
		if (fileNameToObject(fileName) == null) {
			throw new IllegalArgumentException("reportSingleXSDToFile: file not in memory " + fileName);
		}
		writer.println("------------------------------------------");
		writeFileWithIncludesToFile(fileName, writer);
		writer.println("------------------------------------------");
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
