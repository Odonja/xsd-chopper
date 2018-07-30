package org.anhu.xsd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.anhu.xsd.chopFiles.Chopper;
import org.anhu.xsd.chopFiles.RetrieveFiles;
import org.anhu.xsd.elements.XSDFile;

public class Informant {

	private final List<XSDFile> xsdFiles;

	public Informant() throws IllegalArgumentException, FileNotFoundException, IOException {
		String dir = TestApp.getTargetDirectory(Informant.class);
		List<Path> files = RetrieveFiles.findXSDFiles(dir);
		Chopper chopper = new Chopper();
		xsdFiles = new ArrayList<>();
		for (Path file : files) {
			XSDFile xsdFile = chopper.chopFile(file.toString());
			xsdFiles.add(xsdFile);
			System.out.println(xsdFile);
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

}
