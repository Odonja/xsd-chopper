package org.anhu.xsd.elements;

public class Include {

	private String schemaLocation;

	public Include(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}

	@Override
	public String toString() {

		return "Include: schemaLocation = " + schemaLocation;

	}

}
