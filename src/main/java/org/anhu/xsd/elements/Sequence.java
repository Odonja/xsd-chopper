package org.anhu.xsd.elements;

import java.util.ArrayList;
import java.util.List;

public class Sequence {

	List<SimpleElement> elements;

	public Sequence() {
		elements = new ArrayList<>();
	}

	public void addElement(SimpleElement element) {
		elements.add(element);
	}

}
