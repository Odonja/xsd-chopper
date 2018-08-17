package org.anhu.slaves;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.anhu.xsd.elements.XSDFile;

public class ElementTxtInformantSlave extends AbstractInformantSlave {

	public ElementTxtInformantSlave(List<XSDFile> xsdFiles) {
		super(xsdFiles);
	}

	@Override
	protected String getOutputFileName() {
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd-HHmm");
		return "\\FileReport" + dateFormat.format(new Date()) + ".txt";
	}

	@Override
	protected void doFileWriting(XSDFile xsdFile, PrintWriter writer) {
		xsdFile.writeYourselfToFile(writer);
	}

}
