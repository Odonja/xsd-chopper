package org.anhu.xsd.elements;

import java.util.ArrayList;
import java.util.List;

public class XSDFile {

	private final String name;
	private final String location;
	private final List<Include> includes;
	private final List<Element> elements;

	public XSDFile(String name, String location) {
		this.name = name;
		this.location = location;
		includes = new ArrayList<>();
		elements = new ArrayList<>();

	}

	public void addInclude(Include include) {
		includes.add(include);
	}

	public void addElement(Element element) {
		elements.add(element);
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {

		String s = "xsd File: name = " + name + " " + includes + " " + elements;
		s = s.replaceAll("\\[\\]", "").trim().replaceAll("\\s+", " ");
		return s;
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
