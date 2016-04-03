package io.koara.ant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import io.koara.Parser;
import io.koara.ast.Document;
import io.koara.html.Html5Renderer;

public class ParseTask extends Task {

	private String srcdir;
	private String destdir;

	@Override
	public void execute() throws BuildException {
		checkRequiredField("srcdir", srcdir);
		checkRequiredField("destdir", destdir);
		
		File[] documents = new File(srcdir).listFiles(new FilenameExtensionFilter("kd"));
		for(File document : documents) {
			String output = parseDocument(document);
			writeFile(new File(destdir, document.getName().substring(0, document.getName().length() - 3) + ".htm"), output);
		}
	}
	
	private void checkRequiredField(String fieldName, String field) {
		if(field == null) {
			throw new BuildException("'" + fieldName + "' is a required field");
		}
	}
	
	private String parseDocument(File file) {
		try {
			Parser parser = new Parser();
			Document document = parser.parseFile(file);
	
			Html5Renderer renderer = new Html5Renderer();
			document.accept(renderer);
			return renderer.getOutput();
		} catch(IOException e) {
			throw new BuildException("'" + file.getName() + "' could not be parsed");
		}
	}

	private void writeFile(File destdir, String output) {
		try {
			FileWriter fw = new FileWriter(destdir);
			fw.write(output);
			fw.close();
		} catch (IOException e) {
			throw new BuildException("Cannot write file: ", e);
		}
	}
	
	public void setSrcdir(String srcdir) {
		this.srcdir = srcdir;
	}

	public void setDestdir(String destdir) {
		this.destdir = destdir;
	}

}