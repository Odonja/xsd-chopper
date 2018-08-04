package org.anhu.xsd.elements;

import java.util.ArrayList;
import java.util.List;

public class Element {

	private String name;
	private String type;
	private List<Element> elements;
	private final elementType etype;

	public enum elementType {
		ELEMENT, SEQUENCE, SIMPLETYPE, COMPLEXTYPE;
	}

	public Element(elementType etype) {
		this.etype = etype;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public elementType getElementtype() {
		return etype;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addElement(Element element) {
		if (elements == null) {
			elements = new ArrayList<>();
		}
		elements.add(element);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(etype + ":");
		if (name != null) {
			str.append(" name = " + name);
		}
		if (type != null) {
			str.append(" type = " + type);
		}
		if (elements != null) {
			str.append(elements);
		}

		return str.toString();
	}

}
