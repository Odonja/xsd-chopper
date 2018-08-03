package org.anhu.xsd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.anhu.xsd.chopFiles.Chopper;
import org.anhu.xsd.chopFiles.RetrieveFiles;
import org.anhu.xsd.elements.XSDFile;

public class Informant {

	private final List<XSDFile> xsdFiles;

	public Informant() throws UnsupportedEncodingException {
		String dir = TestApp.getTargetDirectory(Informant.class);
		List<Path> files = RetrieveFiles.findXSDFiles(dir);
		Chopper chopper = new Chopper();
		xsdFiles = new ArrayList<>();
		for (Path file : files) {
			XSDFile xsdFile;
			try {
				xsdFile = chopper.chopFile(file.toString());
				xsdFiles.add(xsdFile);
				System.out.println(xsdFile);
			} catch (IllegalArgumentException | IOException e) {
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
			if (notIncluded) {
				str.append(xsdFile.getLocation() + "\n");
			}
		}
		str.append(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
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
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		return str.toString();
	}

}
