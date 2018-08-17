package org.anhu.word;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class CreateDocument {

	public static void main(String[] args) throws Exception {

		// Blank Document
		XWPFDocument document = new XWPFDocument();

		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(new File("createdocument.docx"));
		// create Paragraph
		XWPFParagraph paragraph = document.createParagraph();
		// Set bottom border to paragraph
		paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);

		// Set left border to paragraph
		paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);

		// Set right border to paragraph
		paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);

		// Set top border to paragraph
		paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);

		XWPFRun run = paragraph.createRun();
		run.setText("At tutorialspoint.com, we strive hard to " + "provide quality tutorials for self-learning "
				+ "purpose in the domains of Academics, Information "
				+ "Technology, Management and Computer Programming Languages.");

		// Set Bold an Italic
		XWPFRun paragraphOneRunOne = paragraph.createRun();
		paragraphOneRunOne.setBold(true);
		paragraphOneRunOne.setItalic(true);
		paragraphOneRunOne.setText("Font Style");
		paragraphOneRunOne.addBreak();

		// Set text Position
		XWPFRun paragraphOneRunTwo = paragraph.createRun();
		paragraphOneRunTwo.setText("Font Style two");
		paragraphOneRunTwo.setTextPosition(100);

		// Set Strike through and Font Size and Subscript
		XWPFRun paragraphOneRunThree = paragraph.createRun();
		paragraphOneRunThree.setStrike(true);
		paragraphOneRunThree.setFontSize(20);
		paragraphOneRunThree.setSubscript(VerticalAlign.SUBSCRIPT);
		paragraphOneRunThree.setText(" Different Font Styles");

		// Set alignment paragraph to RIGHT
		paragraph.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun runn = paragraph.createRun();
		runn.setText("At tutorialspoint.com, we strive hard to " + "provide quality tutorials for self-learning "
				+ "purpose in the domains of Academics, Information "
				+ "Technology, Management and Computer Programming " + "Languages.");

		// Create Another paragraph
		paragraph = document.createParagraph();

		// Set alignment paragraph to CENTER
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		runn = paragraph.createRun();
		runn.setText("The endeavour started by Mohtashim, an AMU "
				+ "alumni, who is the founder and the managing director "
				+ "of Tutorials Point (I) Pvt. Ltd. He came up with the "
				+ "website tutorialspoint.com in year 2006 with the help"
				+ "of handpicked freelancers, with an array of tutorials" + " for computer programming languages. ");

		// create table
		XWPFTable table = document.createTable();

		// create first row
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).setText("col one, row one");
		tableRowOne.addNewTableCell().setText("col two, row one");
		tableRowOne.addNewTableCell().setText("col three, row one");

		// create second row
		XWPFTableRow tableRowTwo = table.createRow();
		tableRowTwo.getCell(0).setText("col one, row two");
		tableRowTwo.getCell(1).setText("col two, row two");
		tableRowTwo.getCell(2).setText("col three, row two");

		// create third row
		XWPFTableRow tableRowThree = table.createRow();
		tableRowThree.getCell(0).setText("col one, row three");
		tableRowThree.getCell(1).setText("col two, row three");
		tableRowThree.getCell(2).setText("col three, row three");

		document.write(out);
		out.close();
		System.out.println("createparagraph.docx written successfully");
	}
}