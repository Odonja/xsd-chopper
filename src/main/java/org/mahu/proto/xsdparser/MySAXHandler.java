package org.mahu.proto.xsdparser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class MySAXHandler implements ContentHandler {

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
		System.out.println("MySAXHandler: Starting element=" + localName + " qualifiedName=" + qualifiedName);
		for (int i = 0; i < attrs.getLength(); i++) {
			System.out.println("  " + attrs.getQName(i) + "=" + attrs.getValue(i));
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qualifiedName) throws SAXException {
		System.out.println("MySAXHandler: Ending element=" + localName + " qualifiedName=" + qualifiedName);
	}

	@Override
	public void characters(char[] text, int start, int length) throws SAXException {
		// For XSD all useful information is in the attributes (what I have seen sofar).
		// String data = new String(text);
		// System.out.println(data.substring(start, start + length));
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
