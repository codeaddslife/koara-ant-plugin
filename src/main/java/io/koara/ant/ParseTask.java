package io.koara.ant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import io.koara.Parser;
import io.koara.ast.Document;
import io.koara.html.Html5Renderer;
import io.koara.xml.XmlRenderer;

public class ParseTask extends Task {

	private String srcdir;
	private String destdir;
	private String outputFormat = "htm";

	@Override
	public void execute() throws BuildException {
		checkRequiredField("srcdir", srcdir);
		checkRequiredField("destdir", destdir);
		
		File srcdirFile = new File(srcdir);
		
		List<File> documents = FileUtils.getKoaraDocuments(new File(srcdir));		
		for(File document : documents) {
			String path = document.getAbsolutePath().substring(srcdirFile.getAbsolutePath().length(), document.getAbsolutePath().length() - document.getName().length());
			String output = parseDocument(document);
			writeFile(new File(destdir + path, document.getName().substring(0, document.getName().length() - 3) + "." + outputFormat), output);
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
			return render(document);
		} catch(IOException e) {
			throw new BuildException("'" + file.getName() + "' could not be parsed");
		}
	}
	
	private String render(Document document) {
		if(outputFormat != null && outputFormat.equalsIgnoreCase("xml")) {
			XmlRenderer renderer = new XmlRenderer();
			document.accept(renderer);
			return renderer.getOutput();
		} else {
			Html5Renderer renderer = new Html5Renderer();
			document.accept(renderer);
			return renderer.getOutput();
		}
	}

	private void writeFile(File destdir, String output) {
		try {
			destdir.getParentFile().mkdirs();
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
	
	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

}	