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

	public boolean usesType(String someType) {
		if (type != null && type.equals(someType)) {
			return true;
		}
		if (elements != null) {
			for (Element element : elements) {
				if (element.usesType(someType)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Element> getSimpleAndComplexTypes() {
		List<Element> types = new ArrayList<>();
		if (elements != null) {
			for (Element element : elements) {
				elementType et = element.getElementtype();
				if (et == elementType.COMPLEXTYPE || et == elementType.SIMPLETYPE) {
					types.add(element);
					types.addAll(element.getSimpleAndComplexTypes());

				}
			}
		}
		return types;
	}

	public void appendAllElements(String fileName, StringBuilder str) {
		str.append(etype + " " + name + " in file " + fileName + "\n");
		if (elements != null) {
			for (Element element : elements) {
				element.appendAllElements(fileName, str);
			}
		}
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
