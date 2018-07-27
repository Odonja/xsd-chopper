package org.anhu.xsd.elements;

import java.util.ArrayList;
import java.util.List;

public class XSDFile {

	private String name;
	private List<Include> includes;
	private List<CompositeElement> elements;
	private List<ComplexType> complexities;
	private List<SimpleType> simplicities;

	public XSDFile(String name) {
		this.name = name;
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
	// getter methods for the lists or iterators?

}
