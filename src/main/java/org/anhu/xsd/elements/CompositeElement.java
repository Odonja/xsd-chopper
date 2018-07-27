package org.anhu.xsd.elements;

import java.util.ArrayList;
import java.util.List;

public class CompositeElement {

	private String name;
	private List<ComplexType> complexities;
	private List<SimpleType> simplicities;

	public CompositeElement() {
		complexities = new ArrayList<>();
		simplicities = new ArrayList<>();
	}

	public CompositeElement(String name) {
		this.name = name;
		complexities = new ArrayList<>();
		simplicities = new ArrayList<>();
	}

	public void addComplexType(ComplexType type) {
		complexities.add(type);
	}

	public void addSimpleType(SimpleType type) {
		simplicities.add(type);
	}

	// getter methods for the lists or iterators?

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
			return "CompositeElement: " + complexities + " " + simplicities;
		} else {
			return "CompositeElement: name = " + name + " " + complexities + " " + simplicities;
		}
	}
}
