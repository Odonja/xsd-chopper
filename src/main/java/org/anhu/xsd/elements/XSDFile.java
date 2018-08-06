package org.anhu.xsd.elements;

import java.util.ArrayList;
import java.util.List;

import org.anhu.xsd.elements.Element.elementType;

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

	public boolean hasNoElement() {
		boolean hasNoElement = true;
		for (Element element : elements) {
			if (element.getElementtype() == Element.elementType.ELEMENT) {
				hasNoElement = false;
				break;
			}
		}
		return hasNoElement;
	}

	public List<Element> getSimpleAndComplexTypes() {
		List<Element> types = new ArrayList<>();
		if (elements != null) {
			for (Element element : elements) {
				elementType et = element.getElementtype();
				if (et == elementType.COMPLEXTYPE || et == elementType.SIMPLETYPE) {
					types.add(element);

				}
				types.addAll(element.getSimpleAndComplexTypes());
			}
		}
		return types;
	}

	public boolean usesType(String someType) {
		if (elements != null) {
			for (Element element : elements) {
				if (element.usesType(someType)) {
					return true;
				}
			}
		}
		return false;
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
