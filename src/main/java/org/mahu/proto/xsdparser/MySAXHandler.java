package org.mahu.proto.xsdparser;

import java.util.Stack;

import org.anhu.xsd.elements.Element;
import org.anhu.xsd.elements.Include;
import org.anhu.xsd.elements.XSDFile;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class MySAXHandler implements ContentHandler {

	private final XSDFile file;
	private final Stack<Element> inProgress;
	private boolean uniqueNotStarted;
	private boolean uselessAttributeNotStarted;
	private boolean simpleTypeNotStarted;

	public MySAXHandler(XSDFile file) {
		this.file = file;
		inProgress = new Stack<>();
		uniqueNotStarted = true;
		uselessAttributeNotStarted = true;
		simpleTypeNotStarted = true;
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		// empty
	}

	@Override
	public void startDocument() throws SAXException {
		// empty
	}

	@Override
	public void endDocument() throws SAXException {
		// empty
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		// empty
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		// empty
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qualifiedName, Attributes attrs)
			throws SAXException {
		if (uniqueNotStarted && uselessAttributeNotStarted && simpleTypeNotStarted) {

			Element element = determineElementType(localName, attrs);

			if (element != null) {
				processElement(attrs, element);
			} else if (localName.toLowerCase().equals("include")) {
				Include include = new Include(attrs.getValue(0));
				file.addInclude(include);
			} else if (localName.toLowerCase().equals("unique")) {
				System.out.println("ignoring unique " + attrs.getValue("name") + " in file " + file.getLocation());
				uniqueNotStarted = false;
			} else if (!localName.toLowerCase().equals("schema") && !localName.toLowerCase().equals("enumeration")
					&& uselessAttributeNotStarted) {
				System.out.println("Unprocessed element!!!!!!!!!!! MySAXHandler: Starting element=" + localName
						+ " qualifiedName=" + qualifiedName);
			}
		}
	}

	private void processElement(Attributes attrs, Element element) {
		for (int i = 0; i < attrs.getLength(); i++) {
			if (attrs.getQName(i).equals("name")) {
				element.setName(attrs.getValue(i));
			} else if (attrs.getQName(i).equals("type")) {
				element.setType(attrs.getValue(i));
			}
		}
		if (inProgress.isEmpty()) {
			file.addElement(element);
			inProgress.push(element);
		} else {
			inProgress.peek().addElement(element);
			inProgress.push(element);
		}
	}

	private Element determineElementType(String localName, Attributes attrs) {
		switch (localName.toLowerCase()) {
		case "element":
			return new Element(Element.elementType.ELEMENT);
		case "sequence":
			return new Element(Element.elementType.SEQUENCE);
		case "complextype":
			return new Element(Element.elementType.COMPLEXTYPE);
		case "simpletype":
			simpleTypeNotStarted = false;
			return new Element(Element.elementType.SIMPLETYPE);
		case "choice":
			return new Element(Element.elementType.CHOICE);
		case "attribute":
			if (attrs.getValue("type") != null) {
				return new Element(Element.elementType.ATTRIBUTE);
			}
			System.out.println(
					"ignoring typeless attribute " + attrs.getValue("name") + " in file " + file.getLocation());
			uselessAttributeNotStarted = false;
			break;
		}
		return null;
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qualifiedName) throws SAXException {
		if (uniqueNotStarted && uselessAttributeNotStarted) {
			if (localName.toLowerCase().equals("element") || localName.toLowerCase().equals("sequence")
					|| localName.toLowerCase().equals("complextype") || localName.toLowerCase().equals("simpletype")
					|| localName.toLowerCase().equals("choice")) {
				inProgress.pop();
			}
			if (localName.toLowerCase().equals("simpletype")) {
				simpleTypeNotStarted = true;
			}

		} else if (localName.toLowerCase().equals("unique")) {
			uniqueNotStarted = true;
		} else if (localName.toLowerCase().equals("attribute")) {
			uselessAttributeNotStarted = true;
		}
	}

	@Override
	public void characters(char[] text, int start, int length) throws SAXException {
		// empty
	}

	@Override
	public void ignorableWhitespace(char[] text, int start, int length) throws SAXException {
		// empty
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		// empty
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		// empty
	}
}
