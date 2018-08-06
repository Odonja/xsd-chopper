package org.anhu.xsd;

import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.anhu.xsd.chopFiles.Chopper;
import org.anhu.xsd.chopFiles.RetrieveFiles;
import org.anhu.xsd.elements.Element;
import org.anhu.xsd.elements.XSDFile;

public class Informant {

	private final List<XSDFile> xsdFiles;

	public Informant(String dir) throws UnsupportedEncodingException {
		List<Path> files = RetrieveFiles.findXSDFiles(dir);
		Chopper chopper = new Chopper();
		xsdFiles = new ArrayList<>();
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

}
