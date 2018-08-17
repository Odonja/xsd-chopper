package org.anhu.word;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class MyDocument {

	public static void main(String[] args) throws Exception {

		// Blank Document
		XWPFDocument document = new XWPFDocument();

		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(new File("mydocument.docx"));
		// create Paragraph
		XWPFParagraph paragraph1 = document.createParagraph();

		XWPFRun paragraphOneRunOne = paragraph1.createRun();
		paragraph1.setAlignment(ParagraphAlignment.CENTER);
		paragraphOneRunOne.addCarriageReturn();
		paragraphOneRunOne.setBold(true);
		paragraphOneRunOne.setFontFamily("Arial");
		paragraphOneRunOne.setFontSize(24);
		paragraphOneRunOne.setText("Software Interface Specification");
		paragraphOneRunOne.addCarriageReturn();

		XWPFRun paragraphOneRunTwo = paragraph1.createRun();
		paragraphOneRunTwo.setBold(true);
		paragraphOneRunTwo.setFontFamily("Arial");
		paragraphOneRunTwo.setFontSize(19);
		paragraphOneRunTwo.setText("Bla.1");

		XWPFParagraph paragraph2 = document.createParagraph();
		paragraph2.setPageBreak(true);
		document.createTOC();

		XWPFParagraph paragraph3 = document.createParagraph();
		paragraph3.setPageBreak(true);
		paragraph3.setStyle("Normal");
		XWPFRun paragraph3Run1 = paragraph3.createRun();
		paragraph3Run1.setBold(true);
		paragraph3Run1.setFontFamily("Arial");
		paragraph3Run1.setFontSize(12);
		paragraph3Run1.setText("1");
		paragraph3Run1.addTab();
		paragraph3Run1.setText("INTRODUCTION");
		paragraph3Run1.addCarriageReturn();
		paragraph3Run1.setText("1.1");
		paragraph3Run1.addTab();
		paragraph3Run1.setText("Scope");

		document.write(out);
		out.close();
		System.out.println("mysocument.docx written successfully");
	}

}
