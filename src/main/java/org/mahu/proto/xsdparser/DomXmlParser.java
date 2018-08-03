/**
 * Copyright Koninklijke Philips N.V. 2017
 *
 * All rights are reserved. Reproduction or transmission in whole or in part, in
 * any form or by any means, electronic, mechanical or otherwise, is prohibited
 * without the prior written consent of the copyright owner.
 *
 * Filename: DomXmlParser.java
 */
package org.mahu.proto.xsdparser;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This class create a DOM from a XML string. This class can be used in case no
 * JAXB class is generated for a xsd. The supported methods are for a specific
 * use. See the test case for how example on howto use the methods.
 */
public final class DomXmlParser {

    public static Document stringToDom(final String xmlSource)
            throws SAXException, ParserConfigurationException, IOException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlSource)));
    }

    public static int getRootElementAsInt(final String xmlDoc, final String name) {
        Document doc;
        try {
            doc = DomXmlParser.stringToDom(xmlDoc);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
        if (name.equals(doc.getDocumentElement().getNodeName())) {
            final String value = doc.getDocumentElement().getTextContent();
            return Integer.parseInt(value);
        }
        throw new RuntimeException("could not find element=" + name);
    }

    DomXmlParser() {
        // empty
    }

}