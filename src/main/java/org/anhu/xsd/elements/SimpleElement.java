package org.anhu.xsd.elements;

public class SimpleElement {

	private String name;
	private String type;

	public SimpleElement(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public SimpleElement(String xmlLine) {
		if (xmlLine.contains("name") && xmlLine.contains("type")) {
			String[] choppedLine = xmlLine.split("\"");
			name = choppedLine[1];
			type = choppedLine[3];
		} else {
			throw new IllegalArgumentException("SimpleElement constructor: xml line does not contain name and type");
		}
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {

		return "SimpleElement: [name = " + name + "] [type = " + type + "]";

	}
}
