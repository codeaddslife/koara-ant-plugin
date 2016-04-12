package io.koara.ant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class ConvertTaskTest {

	private ConvertTask task;
	private File srcDir;
	private File destDir;
	@Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
	@Rule public ExpectedException ex = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
		srcDir = new File("src/test/resources");
		destDir = temporaryFolder.newFolder("output");
		
		task = new ConvertTask();
		task.setTodir(destDir);
		
		FileSet fileset = new FileSet();
		fileset.setDir(srcDir);
		fileset.setProject(new Project());
		task.add(fileset);
	}
	
	@Test
	public void execute() throws Exception {
		task.execute();
		
		File[] files = destDir.listFiles();
		assertEquals(1, files.length);
		assertEquals("input.htm", files[0].getName());
		assertEquals("<p>Hello World!</p>", readFileAsString(files[0]));
	}
	
	@Test
	public void executeWithOutputFormatXml() throws Exception {
		task.setOutputFormat("xml");
		task.execute();
		
		File[] files = destDir.listFiles();
		assertEquals(1, files.length);
		assertEquals("input.xml", files[0].getName());
		assertTrue(readFileAsString(files[0]).startsWith("<?xml version"));
	}
	
	@Test
	public void executeWithOutputFormatUnknown() throws Exception {
		ex.expect(BuildException.class);
		ex.expectMessage("Outputformat 'bla' is unknown. Possible values: html5, xml");
		task.setOutputFormat("bla");
		task.execute();
	}
	
	@Test
	public void executeWithModulesSet() throws Exception {
		task.setModules(" HEADINGS");
		task.execute();
		assertEquals("Hello World!", readFileAsString(destDir.listFiles()[0]));
	}
	
	@Test
	public void executeToDirNotSet() {
		ex.expect(BuildException.class);
		ex.expectMessage("Todir must be set.");
		task.setTodir(null);
		task.execute();
	}
	
	private String readFileAsString(File file) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
	
}
