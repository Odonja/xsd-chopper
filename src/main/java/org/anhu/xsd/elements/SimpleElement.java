package org.anhu.xsd.elements;

public class SimpleElement {

	private String name;
	private String type;

	public SimpleElement(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
}
