package org.anhu.xsd;

import java.io.IOException;

import org.junit.Test;

public class TestTestApp {

	@Test
	public void test() {
		String location = "C:\\Users\\s167710\\git\\xsd-chopper\\target\\xsd_example\\ColorProfileSchema.xsd";
		try {
			System.out.println(TestApp.getAsString(location));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
