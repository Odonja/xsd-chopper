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
	private int nrOfSimpleTypesStarted;
	private int nrOfUnwantedsStarted;

	public MySAXHandler(XSDFile file) {
		this.file = file;
		inProgress = new Stack<>();
		nrOfSimpleTypesStarted = 0;
		nrOfUnwantedsStarted = 0;
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
		boolean unprocessed = true;
		String lclocname = localName.toLowerCase();
		if (nrOfUnwantedsStarted == 0) {

			Element element = determineElementType(localName, attrs);

			if (element != null) {
				processElement(attrs, element);
				unprocessed = false;
			} else if (lclocname.equals("include")) {
				Include include = new Include(attrs.getValue(0));
				file.addInclude(include);
				unprocessed = false;
			}
		}

		if (unprocessed) {
			if (lclocname.equals("unique")) {
				System.out.println("   ignoring unique " + attrs.getValue("name"));
				nrOfUnwantedsStarted++;
			} else if (lclocname.equals("annotation")) {
				System.out.println("   ignoring typeless annotation");
				nrOfUnwantedsStarted++;
			} else if (lclocname.equals("simpletype")) {
				nrOfSimpleTypesStarted++;
				nrOfUnwantedsStarted++;
			} else if (lclocname.equals("attribute")) {
				nrOfUnwantedsStarted++;
			} else if (!localName.toLowerCase().equals("schema") && !localName.toLowerCase().equals("enumeration")
					&& nrOfUnwantedsStarted == 0) {
				System.out.println("Unprocessed element!!!!!!!!!!! MySAXHandler: Starting element=" + localName
						+ " qualifiedName=" + qualifiedName);
			}
		}
	}

	private void processElement(Attributes attrs, Element element) {
		for (int i = 0; i < attrs.getLength(); i++) {
			String qName = attrs.getQName(i);
			if (qName.equals("name")) {
				element.setName(attrs.getValue(i));
			} else if (qName.equals("type")) {
				element.setType(attrs.getValue(i));
			} else if (qName.equals("base")) {
				element.setBase(attrs.getValue(i));
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
			nrOfSimpleTypesStarted++;
			nrOfUnwantedsStarted++;
			return new Element(Element.elementType.SIMPLETYPE);
		case "choice":
			return new Element(Element.elementType.CHOICE);
		case "complexcontent":
			return new Element(Element.elementType.COMPLEXCONTENT);
		case "simplecontent":
			return new Element(Element.elementType.SIMPLECONTENT);
		case "extension":
			return new Element(Element.elementType.EXTENSION);
		case "group":
			return new Element(Element.elementType.GROUP);
		case "attribute":
			if (attrs.getValue("type") != null) {
				return new Element(Element.elementType.ATTRIBUTE);
			}
			System.out.println(
					"ignoring typeless attribute " + attrs.getValue("name") + " in file " + file.getLocation());
			nrOfUnwantedsStarted++;
			break;
		}
		return null;
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qualifiedName) throws SAXException {
		String lcLocalName = localName.toLowerCase();
		if (nrOfUnwantedsStarted == 0 || (nrOfUnwantedsStarted == 1 && nrOfSimpleTypesStarted == 1)) {

			if (lcLocalName.equals("element") || lcLocalName.equals("sequence") || lcLocalName.equals("complextype")
					|| lcLocalName.equals("simpletype") || lcLocalName.equals("choice")
					|| lcLocalName.equals("complexcontent") || lcLocalName.equals("simplecontent")
					|| lcLocalName.equals("extension") || lcLocalName.equals("group")
					|| lcLocalName.equals("attribute")) {
				inProgress.pop();
			}
			if (lcLocalName.equals("simpletype")) {
				nrOfSimpleTypesStarted--;
				nrOfUnwantedsStarted--;
			}

		} else if (lcLocalName.equals("unique") || lcLocalName.equals("annotation")
				|| lcLocalName.equals("attribute")) {
			nrOfUnwantedsStarted--;
		} else if (lcLocalName.equals("simpletype")) {
			nrOfUnwantedsStarted--;
			nrOfSimpleTypesStarted--;
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
