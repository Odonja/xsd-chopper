package org.mahu.proto.xsdparser;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class GetResource {

	public final static String getTargetDirectory(final Class<?> cls) throws UnsupportedEncodingException {
		final String relPath = getDirectoryOfClass(cls);
		// return a path with a collapsed filename (i.e. without /..).
		return new File(relPath).toPath().resolve("../../target").normalize().toFile().getAbsolutePath();

	}

	public final static String getDirectoryOfClass(final Class<?> cls) {
		try {
			return cls.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		} catch (final URISyntaxException e) {
			throw new RuntimeException("Failed to retrieve path for class " + cls.getName(), e);
		}
	}
	
	public static String getAsString(final String name) throws IOException {
		File file = new File (getDirectoryOfClass(GetResource.class), name);
		return new String(Files.readAllBytes(file.toPath()), "UTF-8");
	}

}
