package org.anhu.slaves;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.anhu.xsd.elements.XSDFile;

public class OriginalTxtInformantSlave extends AbstractInformantSlave {

	public OriginalTxtInformantSlave(List<XSDFile> xsdFiles) {
		super(xsdFiles);
	}

	@Override
	protected String getOutputFileName() {
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd-HHmm");
		return "\\OriginalFileReport" + dateFormat.format(new Date()) + ".txt";
	}

	@Override
	protected void doFileWriting(XSDFile xsdFile, PrintWriter writer) {
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
		} catch (FileNotFoundException e) {
			System.out.println("readFileNamesFromFile, file: " + location + " was not found.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
