package io.koara.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public static List<File> getKoaraDocuments(File baseDir) {
		List<File> files = new ArrayList<File>();
		for(File f : baseDir.listFiles()) {
			if(f.isDirectory()) {
				files.addAll(getKoaraDocuments(f));
			} else if(f.getName().toLowerCase().endsWith(".kd")) {
				files.add(f);
			}
		}
		return files;
	}
	
}
