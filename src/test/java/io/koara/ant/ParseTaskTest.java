package io.koara.ant;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class ParseTaskTest {

	private ParseTask task;
	private File srcDir;
	private File destDir;
	@Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
	@Rule public ExpectedException ex = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
		srcDir = new File("src/test/resources");
		destDir = temporaryFolder.newFolder("output");
		
		task = new ParseTask();
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
		assertEquals("test.htm", files[0].getName());
		assertEquals("<p>Hello World!</p>", getContent(files[0]));
	}
	
	@Test
	public void executeWithOutputFormatXml() throws Exception {
		task.setOutputFormat("xml");
		task.execute();
		
		File[] files = destDir.listFiles();
		assertEquals(1, files.length);
		assertEquals("test.xml", files[0].getName());
		assertTrue(getContent(files[0]).startsWith("<?xml version"));
	}
	
	@Test
	public void executeWithOutputFormatUnknown() throws Exception {
		ex.expect(BuildException.class);
		ex.expectMessage("Outputformat 'bla' is unknown. Possible values: html5, xml");
		task.setOutputFormat("bla");
		task.execute();
	}
	
	@Test
	public void executeToDirNotSet() {
		ex.expect(BuildException.class);
		ex.expectMessage("Todir must be set.");
		task.setTodir(null);
		task.execute();
	}
	
	private String getContent(File file) throws Exception {
		return new String(Files.readAllBytes(file.toPath()), "UTF-8");
	}
	
}
