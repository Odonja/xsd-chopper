package org.anhu.word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.anhu.xsd.TestApp;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;

public class InformantSlaveDocument {

	private final static String H1 = "Heading1";
	private final static String H2 = "Heading2";

	private final XWPFDocument document;
	private final FileOutputStream out;
	private int commonCounter = 1;

	public InformantSlaveDocument(String dir) throws IOException, XmlException {
		// Blank Document
		document = readTemplate(); // new XWPFDocument();
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd-HHmm");
		// Write the Document in file system
		System.out.println(dir);
		out = new FileOutputStream(
				new File(dir + "\\OriginalFilesAsWordReport" + dateFormat.format(new Date()) + ".docx"));

//		copyNumbering(getWordTemplateAsString());
//		readStylesFromTemplate();
		// addToc();
	}

	// The print function of this method is still interesting. It exposes
	// information about existing class.
	// It should moved to seperate class.
	private void readStylesFromTemplate() throws FileNotFoundException, IOException, XmlException {
		try (XWPFDocument template = new XWPFDocument(new FileInputStream(getTemplateFile()))) {
			// let's copy styles from template to new doc
			XWPFStyles newStyles = document.createStyles();
			newStyles.setStyles(template.getStyle());

			Stream.of(template.getStyle().getStyleArray()).forEach(style -> System.out.println(style));
		}
	}

	private static XWPFDocument readTemplate() throws FileNotFoundException, IOException, XmlException {
		XWPFDocument wwpfDocument = new XWPFDocument(new FileInputStream(getTemplateFile1()));
		// printForAllParagraphText(wwpfDocument);

		// This is the really interesting one, because it replaced an existing
		// paragraph.
		// However that does not belong in this method. A method should not have side
		// effects.
		replaceOneParagraph(wwpfDocument);
		return wwpfDocument;
	}

	// Interesting to see content of existing document.
	// Move to own class.
	private static void printForAllParagraphText(XWPFDocument wwpfDocument) {
		Iterator<XWPFParagraph> it = wwpfDocument.getParagraphsIterator();
		while (it.hasNext()) {
			XWPFParagraph par = it.next();
			System.out.println(par.getText());
		}
	}

	private static void replaceOneParagraph(XWPFDocument wwpfDocument) {
		Iterator<XWPFParagraph> it = wwpfDocument.getParagraphsIterator();
		while (it.hasNext()) {
			XWPFParagraph par = it.next();
			if ("TopLevelXsd".equals(par.getText())) {
				replaceParagraphBy(par,
						// Here is a list of paragrapg that shall replace the existing paragraph
						Arrays.asList(new XWPFParagraph[] { createHeading2(wwpfDocument, "NewHeading2a"),
								createBody(wwpfDocument, "SomeText-a"), createHeading2(wwpfDocument, "NewHeading2b"),
								createBody(wwpfDocument, "SomeText-b")

						}));
				break;
			}
		}
	}

	// based on:
	// https://stackoverflow.com/questions/20667619/insert-a-paragraph-in-xwpfdocument
	private static void replaceParagraphBy(XWPFParagraph existingPar, List<XWPFParagraph> newParagraps) {
		if (existingPar != null) {
			// No clue what this code does, but it works
			XmlCursor existingCursor = existingPar.getCTP().newCursor();
			newParagraps.forEach(newP -> {
				XmlCursor c2 = newP.getCTP().newCursor();
				c2.moveXml(existingCursor);
				c2.dispose();
			});
			existingCursor.removeXml();
			existingCursor.dispose();
		}
	}

	private static XWPFParagraph createHeading2(XWPFDocument doc, final String text) {
		XWPFParagraph newP = doc.createParagraph();
		newP.setStyle(H2);
		XWPFRun run = newP.createRun();
		run.setText(text);
		return newP;
	}

	private static XWPFParagraph createBody(XWPFDocument doc, final String text) {
		XWPFParagraph newP = doc.createParagraph();
		newP.setStyle("Body");
		XWPFRun run2 = newP.createRun();
		run2.setText(text);
		run2.addCarriageReturn();
		return newP;
	}

//	public void copyNumbering(String numberingXmlString) throws XmlException {
//		CTNumbering cTNumbering = CTNumbering.Factory.parse(numberingXmlString);
//		XWPFNumbering numbering = document.createNumbering();
//		numbering.setNumbering(cTNumbering);
//	}

//	private void addToc() {
//		//
//		XWPFParagraph paragraph = document.createParagraph();
//		CTP ctP = paragraph.getCTP();
//		CTSimpleField toc = ctP.addNewFldSimple();
//		toc.setInstr("TOC \\h");
//		toc.setDirty(STOnOff.TRUE);
//	}

	public void done() throws IOException {
		// enforce updating of TOC when document is opened
		document.enforceUpdateFields();
		//
		document.write(out);
		out.close();
		System.out.println("document written successfully");
	}

	public void requestedXSDsStart() {
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setStyle(H1);
		XWPFRun run = paragraph.createRun();
//		run.setBold(true);
//		run.setFontFamily("Arial");
//		run.setFontSize(12);
//		run.setText("1");
//		run.addTab();
		run.setText("REQUEST XSD");
//		run.addCarriageReturn();
		//
	}

	public void addRequestedXSD(BufferedReader br, String fileName) {
		{
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setStyle(H2);
			XWPFRun run1 = paragraph.createRun();
			System.out.println(fileName);
//		run1.setBold(true);
//		run1.setFontFamily("Arial");
//		run1.setFontSize(12);
//		run1.addCarriageReturn();
//		run1.setText("1." + requestCounter);
//		requestCounter++;
//		run1.addTab();
			run1.setText(fileName);
//		run1.addCarriageReturn();
		}

		{
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setStyle("Body");
			XWPFRun run2 = paragraph.createRun();
			run2.setFontFamily("Arial");
			run2.setFontSize(10);
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
	}

	public void commonXSDsStart() {
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setStyle(H1);

		XWPFRun run = paragraph.createRun();
//		run.setBold(true);
//		run.setFontFamily("Arial");
//		run.setFontSize(12);
//		run.addCarriageReturn();
//		run.setText("2");
//		run.addTab();
		run.setText("COMMON XSD");
//		run.addCarriageReturn();
	}

	public void startNewChapter(String chapterName) {
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setStyle(H1);

		XWPFRun run = paragraph.createRun();
//		run.setBold(true);
//		run.setFontFamily("Arial");
//		run.setFontSize(12);
//		run.addCarriageReturn();
//		run.setText("2");
//		run.addTab();
		run.setText(chapterName);
	}

	public void addCommonXSD(BufferedReader br, String fileName) {
		{
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setStyle(H2);
			XWPFRun run1 = paragraph.createRun();
			System.out.println(fileName);
//		run1.setBold(true);
//		run1.setFontFamily("Arial");
//		run1.setFontSize(12);
//		run1.addCarriageReturn();
//		run1.setText("2." + commonCounter);
//		commonCounter++;
//		run1.addTab();
			run1.setText(fileName);
//		run1.addCarriageReturn();
		}

		{
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setStyle("Body");
			XWPFRun run2 = paragraph.createRun();
			run2.setFontFamily("Arial");
			run2.setFontSize(10);
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
	}

	public void addFileCannotBeFoundLine(String fileName, List<String> whereIncluded) {
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setStyle(H2);
		XWPFRun run1 = paragraph.createRun();
//		run1.setBold(true);
//		run1.setFontFamily("Arial");
//		run1.setFontSize(12);
//		run1.addCarriageReturn();
//		run1.setText("2." + commonCounter);
//		commonCounter++;
//		run1.addTab();
		run1.setText(fileName);
		run1.addCarriageReturn();
		System.out.println(fileName + " was not found. \n it was included in: ");
		XWPFRun run2 = paragraph.createRun();
		run2.setFontFamily("Arial");
		run2.setFontSize(10);
		run2.setText("File was not found in program memory");
		run2.addCarriageReturn();
		if (whereIncluded != null && whereIncluded.size() > 0) {
			run2.setText("It was included in:");
			run2.addCarriageReturn();
			for (String s : whereIncluded) {
				System.out.println(s);
				run2.setText(s);
				run2.addCarriageReturn();
			}
		}
	}

	public static String getWordTemplateAsString() throws IOException {
		String content = new String(Files.readAllBytes(getTemplateFile().toPath()));
		return content;
	}

	private static File getTemplateFile() throws UnsupportedEncodingException {
		String dir = TestApp.getTargetDirectory(InformantSlaveDocument.class);
		return new File(dir, "classes/WordTemplate.dotx");
	}

	private static File getTemplateFile1() throws UnsupportedEncodingException {
		String dir = TestApp.getTargetDirectory(InformantSlaveDocument.class);
		return new File(dir, "classes/Owner.docx");
	}

}
