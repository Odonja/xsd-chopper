package org.anhu.word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class InformantSlaveDocument {

	private final XWPFDocument document;
	private final FileOutputStream out;
	private int requestCounter = 1;
	private int commonCounter = 1;
	private XWPFParagraph paragraph;

	public InformantSlaveDocument(String dir) throws FileNotFoundException {
		// Blank Document
		document = new XWPFDocument();
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd-HHmm");
		// Write the Document in file system
		System.out.println(dir);
		out = new FileOutputStream(
				new File(dir + "\\OriginalFilesAsWordReport" + dateFormat.format(new Date()) + ".docx"));
	}

	public void done() throws IOException {
		document.write(out);
		out.close();
		System.out.println("document written successfully");
	}

	public void requestedXSDsStart() {
		paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setBold(true);
		run.setFontFamily("Arial");
		run.setFontSize(12);
		run.setText("1");
		run.addTab();
		run.setText("REQUEST XSD");
		run.addCarriageReturn();
	}

	public void addRequestedXSD(BufferedReader br, String fileName) {
		XWPFRun run1 = paragraph.createRun();
		run1.setBold(true);
		run1.setFontFamily("Arial");
		run1.setFontSize(12);
		run1.addCarriageReturn();
		run1.setText("1." + requestCounter);
		requestCounter++;
		run1.addTab();
		run1.setText(fileName);
		run1.addCarriageReturn();

		XWPFRun run2 = paragraph.createRun();
		run2.setFontFamily("Arial");
		run2.setFontSize(8);
		String line;
		try {
			while ((line = br.readLine()) != null) {
				run2.setText(line);
				run2.addCarriageReturn();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void commonXSDsStart() {
		paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setBold(true);
		run.setFontFamily("Arial");
		run.setFontSize(12);
		run.addCarriageReturn();
		run.setText("2");
		run.addTab();
		run.setText("COMMON XSD");
		run.addCarriageReturn();
	}

	public void addCommonXSD(BufferedReader br, String fileName) {
		XWPFRun run1 = paragraph.createRun();
		run1.setBold(true);
		run1.setFontFamily("Arial");
		run1.setFontSize(12);
		run1.addCarriageReturn();
		run1.setText("2." + commonCounter);
		commonCounter++;
		run1.addTab();
		run1.setText(fileName);
		run1.addCarriageReturn();

		XWPFRun run2 = paragraph.createRun();
		run2.setFontFamily("Arial");
		run2.setFontSize(8);
		String line;
		try {
			while ((line = br.readLine()) != null) {
				run2.setText(line);
				run2.addCarriageReturn();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addFileCannotBeFoundLine(String fileName) {
		XWPFRun run1 = paragraph.createRun();
		run1.setBold(true);
		run1.setFontFamily("Arial");
		run1.setFontSize(12);
		run1.addCarriageReturn();
		run1.setText("2." + commonCounter);
		commonCounter++;
		run1.addTab();
		run1.setText(fileName);
		run1.addCarriageReturn();

		XWPFRun run2 = paragraph.createRun();
		run2.setFontFamily("Arial");
		run2.setFontSize(8);
		run2.setText("File was not found in program memory");
		run2.addCarriageReturn();
	}

}
