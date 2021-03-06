package org.anhu.xsd.chopFiles;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class SortFiles extends SimpleFileVisitor<Path> {

	private List<Path> xsdPaths = new ArrayList<>();

	// Print information about
	// each type of file.
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
		if (attr.isRegularFile() && file.toString().endsWith(".xsd")) {
			xsdPaths.add(file);
		}
		return CONTINUE;
	}

	// Print each directory visited.
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
		return CONTINUE;
	}

	// If there is some error accessing the file, let the user know.
	// If you don't override this method and an error occurs, an IOException
	// is thrown.
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) {
		System.err.println(exc);
		return CONTINUE;
	}

	public List<Path> retrieveAllXsdFiles() {
		return xsdPaths;
	}
}
