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

	private String outputDir;

	@Override
	public void execute() throws BuildException {
		String output = parseDocument("Hello World!");
		writeFile(new File(outputDir, "x.htm"), output);
	}
	
	private String parseDocument(String text) {
		Parser parser = new Parser();
		Document document = parser.parse("Hello World!");

		Html5Renderer renderer = new Html5Renderer();
		document.accept(renderer);
		return renderer.getOutput();
	}

	private void writeFile(File outputFile, String output) {
		try {
			FileWriter fw = new FileWriter(outputFile);
			fw.write(output);
			fw.close();
		} catch (IOException e) {
			throw new BuildException("Cannot write file: ", e);
		}
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

}