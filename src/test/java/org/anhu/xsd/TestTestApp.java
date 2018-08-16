package org.anhu.xsd;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	@Test
	public void test2() {
		String a = "a";
		List<String> list = new ArrayList<>();
		list.add(a);
		String b = "a";
		assertTrue(list.contains(b));
	}

	@Test
	public void test3() {
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd-HH:mm");
		System.out.println(dateFormat.format(new Date())); // 2016/11/16 12:08:43
	}

}
