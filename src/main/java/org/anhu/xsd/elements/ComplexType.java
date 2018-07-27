package org.anhu.xsd.elements;

import java.util.ArrayList;
import java.util.List;

public class ComplexType {

	private String name;
	List<Sequence> sequences;
	List<SimpleElement> elements;

	public ComplexType() {
		sequences = new ArrayList<>();
		elements = new ArrayList<>();
	}

	public ComplexType(String name) {
		this.name = name;
		sequences = new ArrayList<>();
		elements = new ArrayList<>();
	}

	public void addSequence(Sequence sequence) {
		sequences.add(sequence);
	}

	public void addElement(SimpleElement element) {
		elements.add(element);
	}

	public String getName() {
		return name;
	}

	public void setNameFromXMLLine(String xmlLine) {
		if (xmlLine.contains("name")) {
			String[] choppedLine = xmlLine.split("\"");
			name = choppedLine[1];
		}
	}

	@Override
	public String toString() {
		if (name == null) {
			return "ComplexType: " + sequences + " " + elements;
		} else {
			return "ComplexType: name = " + name + " " + sequences + " " + elements;
		}

	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			return true;
//		}
//		if (obj == null) {
//			return false;
//		}
//		if (getClass() != obj.getClass()) {
//			return false;
//		}
//		ComplexType other = (ComplexType) obj;
//		if (name != other.getName()) {
//			return false;
//		}
//		return true;
//	}

}
