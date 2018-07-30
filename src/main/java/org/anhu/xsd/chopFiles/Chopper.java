package org.anhu.xsd.chopFiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.anhu.xsd.elements.ComplexType;
import org.anhu.xsd.elements.CompositeElement;
import org.anhu.xsd.elements.Include;
import org.anhu.xsd.elements.Sequence;
import org.anhu.xsd.elements.SimpleElement;
import org.anhu.xsd.elements.SimpleType;
import org.anhu.xsd.elements.XSDFile;

public class Chopper {

	public XSDFile chopFile(String filepath) throws IllegalArgumentException, FileNotFoundException, IOException {
		if (!filepath.toString().endsWith(".xsd")) {
			throw new IllegalArgumentException("Chopper chopFile: file is not an .xsd file");
		}
		File file = new File(filepath);
		BufferedReader br = new BufferedReader(new FileReader(file));
		XSDFile xsdFile = new XSDFile(file.getName(), filepath);
		delegator(br, xsdFile);
		br.close();
		return xsdFile;
	}

	private void delegator(BufferedReader br, XSDFile xsdFile) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			// System.out.println(line + line.contains("<xs:include"));
			if (line.contains("<xs:include")) {
				Include thisInclude = includeProcessor(line);
				xsdFile.addInclude(thisInclude);
			} else if (line.contains("<xs:complexType")) {
				ComplexType thisCT = complexTypeProcessor(br, line);
				xsdFile.addComplexType(thisCT);
			} else if (line.contains("<xs:simpleType")) {
				SimpleType thisST = simpleTypeProcessor(br, line);
				xsdFile.addSimpleType(thisST);
			} else if (line.contains("<xs:element")) {
				CompositeElement ce = compositeElementProcessor(br, line);
				xsdFile.addElement(ce);
			}
		}
	}

	// the name of the include is between "'s
	private CompositeElement compositeElementProcessor(BufferedReader br, String line) throws IOException {
		CompositeElement ce = new CompositeElement();
		ce.setNameFromXMLLine(line);

		while (!(line = br.readLine()).contains("</xs:element")) {
			if (line.contains("<xs:complexType")) {
				ComplexType thisCT = complexTypeProcessor(br, line);
				ce.addComplexType(thisCT);
			} else if (line.contains("<xs:simpleType")) {
				SimpleType thisST = simpleTypeProcessor(br, line);
				ce.addSimpleType(thisST);
			}
		}

		return ce;
	}

	// the name of the include is between "'s
	private Include includeProcessor(String includeLine) {
		if (!includeLine.contains("include")) {
			throw new IllegalArgumentException("includeProcessor: include line does not contain include");
		}
		String[] choppedLine = includeLine.split("\"");
		return new Include(choppedLine[1]);
	}

	// the name of the complex type is between "'s
	private ComplexType complexTypeProcessor(BufferedReader br, String line) throws IOException {
		ComplexType ct = new ComplexType();
		ct.setNameFromXMLLine(line);

		while (!(line = br.readLine()).contains("</xs:complexType")) {
			if (line.contains("<xs:sequence")) {
				Sequence sequence = sequenceProcessor(br, line);
				ct.addSequence(sequence);
			} else if (line.contains("<xs:element")) {
				SimpleElement se = simpleElementProcessor(line);
				ct.addElement(se);
			}
		}
		return ct;
	}

	private Sequence sequenceProcessor(BufferedReader br, String line) throws IOException {
		Sequence sequence = new Sequence();
		while (!(line = br.readLine()).contains("</xs:sequence")) {
			if (line.contains("<xs:element")) {
				SimpleElement se = simpleElementProcessor(line);
				sequence.addElement(se);
			}
		}
		return sequence;
	}

	// simple element lines looke like <xs:element name="theName" type="thetype"
	// other stuff />
	private SimpleElement simpleElementProcessor(String line) {
		String[] choppedLine = line.split("\"");
		return new SimpleElement(choppedLine[1], choppedLine[3]);
	}

	// the name of the complex type is between "'s
	private SimpleType simpleTypeProcessor(BufferedReader br, String line) throws IOException {
		String[] choppedLine = line.split("\"");
		SimpleType st = new SimpleType(choppedLine[1]);
		while (!(line = br.readLine()).contains("</xs:simpleType")) {
			// skip all non relevant lines before returning
		}
		return st;
	}

}
