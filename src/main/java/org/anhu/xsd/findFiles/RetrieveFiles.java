package org.anhu.xsd.findFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RetrieveFiles {

	public static List<Path> findXSDFiles(String dir) {
		PrintFiles pf = new PrintFiles();
		try {
			Files.walkFileTree(Paths.get(dir), pf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pf.retrieveAllXsdFiles();
	}

}
