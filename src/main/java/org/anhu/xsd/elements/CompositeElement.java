package org.anhu.xsd.elements;

import java.util.ArrayList;
import java.util.List;

public class CompositeElement {

	private List<ComplexType> complexities;
	private List<SimpleType> simplicities;

	public CompositeElement() {
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

}
