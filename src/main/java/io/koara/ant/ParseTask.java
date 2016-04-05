package io.koara.ant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.ResourceCollection;

import io.koara.Parser;
import io.koara.ast.Document;
import io.koara.html.Html5Renderer;
import io.koara.xml.XmlRenderer;

public class ParseTask extends Task {

	private File destDir;
	private String outputFormat;
	protected Vector<ResourceCollection> rcs = new Vector<ResourceCollection>();
	
	@Override
	public void execute() throws BuildException {
		validateAttributes();
		
		for (int i = 0; i < rcs.size(); i++) {
			ResourceCollection rc = rcs.elementAt(i);
			if(rc instanceof FileSet) {
				 final FileSet fs = (FileSet) rc;
				 DirectoryScanner ds = fs.getDirectoryScanner();
			     String[] files = ds.getIncludedFiles();
			     for(String file : files) {
			    	 parse(ds.getBasedir(), file);
			     }
			}
		}
	}
	
	private void parse(File srcdir, String file) {
		try {
			log("Parse '" + new File(srcdir, file) + "'");
			
			Parser parser = new Parser();
			Document document = parser.parseFile(new File(srcdir, file));
			
			if(outputFormat == null || outputFormat.equalsIgnoreCase("html5")) {
				Html5Renderer renderer = new Html5Renderer();
				document.accept(renderer);
				writeToFile(new File(destDir, "test.htm"), renderer.getOutput());
			} else if(outputFormat.equalsIgnoreCase("xml")) {
				XmlRenderer renderer = new XmlRenderer();
				document.accept(renderer);
				writeToFile(new File(destDir, "test.xml"), renderer.getOutput());
			} else {
				throw new BuildException("Outputformat '" + outputFormat + "' is unknown. Possible values: html5, xml");
			}
		} catch(IOException e) {
			throw new BuildException("Could not parse " + file);
		}
	}
	

	private void writeToFile(File file, String content) {
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();
		} catch(IOException e) {
			throw new BuildException("Could not write to file " + file.getAbsolutePath() + ".");
		}
	}
	
	private void validateAttributes() {
		if (destDir == null) {
            throw new BuildException("Todir must be set.");
        }
	}
	
	public void add(final ResourceCollection res) {
        rcs.add(res);
    }

	public void setTodir(final File destDir) {
		this.destDir = destDir;
	}
	
	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

}
