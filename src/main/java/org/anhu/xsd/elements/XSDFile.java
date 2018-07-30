package org.anhu.xsd.elements;

import java.util.ArrayList;
import java.util.List;

public class XSDFile {

	private final String name;
	private final String location;
	private final List<Include> includes;
	private final List<CompositeElement> elements;
	private final List<ComplexType> complexities;
	private final List<SimpleType> simplicities;

	public XSDFile(String name, String location) {
		this.name = name;
		this.location = location;
		includes = new ArrayList<>();
		elements = new ArrayList<>();
		complexities = new ArrayList<>();
		simplicities = new ArrayList<>();
	}

	public void addInclude(Include include) {
		includes.add(include);
	}

	public void addElement(CompositeElement element) {
		elements.add(element);
	}

	public void addComplexType(ComplexType type) {
		complexities.add(type);
	}

	public void addSimpleType(SimpleType type) {
		simplicities.add(type);
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {

		String s = "xsd File: name = " + name + " " + includes + " " + elements + " " + complexities + " "
				+ simplicities;

		return s.replaceAll("\\[\\]", "");
	}

	public boolean isListedInInclude(String file) {
		for (Include incl : includes) {
			if (incl.getSchemaLocation().equals(file)) {
				return true;
			}
		}
		return false;
	}
}
