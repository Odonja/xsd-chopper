package org.anhu.slaves;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.anhu.word.InformantSlaveDocument;
import org.anhu.xsd.TestApp;
import org.anhu.xsd.elements.XSDFile;
import org.apache.xmlbeans.XmlException;

public class OriginalWordInformantSlave extends AbstractInformantSlave {

	private List<String> unfoundIncludes;
	private List<String> unfoundRequested;

	public OriginalWordInformantSlave(final List<XSDFile> xsdFiles) {
		super(xsdFiles);
		unfoundIncludes = new ArrayList<>();
		unfoundRequested = new ArrayList<>();
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlException e) {
			e.printStackTrace();
		}
	}

	private void reportFiles(InformantSlaveDocument doc) {
		doc.requestedXSDsStart();
		System.out.println(
				"------top-level XSD's---------------------------------------------------------------------------------------------------------------------------");
		for (String currentFile : getFileNames()) {
			writeRequestedFile(currentFile, doc);
		}
		doc.commonXSDsStart();
		System.out.println(
				"------common XSD's---------------------------------------------------------------------------------------------------------------------------");
		for (String currentFile : getIncludes()) {
			writeIncludedFile(currentFile, doc);
		}
		if (unfoundRequested.size() > 0) {
			doc.startNewChapter("Requested XSD's that were not found");
			System.out.println(
					"------Requested XSD's that were not found---------------------------------------------------------------------------------------------------------------------------");
			for (String currentFile : unfoundRequested) {
				doc.addFileCannotBeFoundLine(currentFile, null);
			}
		}
		if (unfoundIncludes.size() > 0) {
			doc.startNewChapter("Included XSD's that were not found");
			System.out.println(
					"------Included XSD's that were not found---------------------------------------------------------------------------------------------------------------------------");
			for (String currentFile : unfoundIncludes) {
				List<String> whereIncluded = findWhereIncluded(currentFile);
				doc.addFileCannotBeFoundLine(currentFile, whereIncluded);
			}
		}
	}

	private void writeRequestedFile(String file, InformantSlaveDocument doc) {
		XSDFile xsdFile = fileNameToObject(file);
		if (xsdFile == null) {
			unfoundRequested.add(file);
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
			unfoundIncludes.add(file);
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
