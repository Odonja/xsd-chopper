package org.anhu.slaves;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.anhu.word.InformantSlaveDocument;
import org.anhu.xsd.TestApp;
import org.anhu.xsd.elements.XSDFile;

public class OriginalWordInformantSlave extends AbstractInformantSlave {

	public OriginalWordInformantSlave(final List<XSDFile> xsdFiles) {
		super(xsdFiles);
	}

	@Override
	protected void startReport() {
		String dir = "";
		try {
			dir = TestApp.getTargetDirectory(OriginalWordInformantSlave.class);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			InformantSlaveDocument doc = new InformantSlaveDocument(dir);
			reportFiles(doc);
			doc.done();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void reportFiles(InformantSlaveDocument doc) {
		for (String currentFile : getFileNames()) {
			doc.requestedXSDsStart();
			writeRequestedFile(currentFile, doc);
		}
		for (String currentFile : getIncludes()) {
			doc.commonXSDsStart();
			writeIncludedFile(currentFile, doc);
		}
	}

	private void writeRequestedFile(String file, InformantSlaveDocument doc) {
		XSDFile xsdFile = fileNameToObject(file);
		if (xsdFile == null) {
			doc.addFileCannotBeFoundLine(file);
		} else {
			String location = xsdFile.getLocation();
			File openfile = new File(location);
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(openfile));
				doc.addRequestedXSD(br, file);
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void writeIncludedFile(String file, InformantSlaveDocument doc) {
		XSDFile xsdFile = fileNameToObject(file);
		if (xsdFile == null) {
			doc.addFileCannotBeFoundLine(file);
		} else {
			String location = xsdFile.getLocation();
			File openfile = new File(location);
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(openfile));
				doc.addCommonXSD(br, file);
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected String getOutputFileName() {
		return null;
	}

}
