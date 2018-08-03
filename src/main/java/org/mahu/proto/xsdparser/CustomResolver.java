package org.mahu.proto.xsdparser;

import java.io.StringReader;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class CustomResolver implements EntityResolver {

	public InputSource resolveEntity(String publicId, String systemId) {
		System.out.println("CustomResolver: Resolving publicId=" + publicId + " systemId=" + systemId);

		StringReader strReader = new StringReader("This is a custom	 entity");
		if (systemId.equals("http://www.builder.com/xml/entities/MyCusomEntity")) {
			return new InputSource(strReader);
		} else {
			return null;
		}
	}
}
