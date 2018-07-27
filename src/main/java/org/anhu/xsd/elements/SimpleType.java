package org.anhu.xsd.elements;

public class SimpleType {

	private String name;

	public SimpleType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {

		return "SimpleType: name = " + name;

	}

}
