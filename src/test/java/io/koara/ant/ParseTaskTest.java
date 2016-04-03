package io.koara.ant;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class ParseTaskTest {

	private ParseTask task;
	private File destDir;
	@Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
	@Rule public ExpectedException ex = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
		destDir = temporaryFolder.newFolder();
		task = new ParseTask();
		task.setSrcdir("src/test/resources");
		task.setDestdir(destDir.getAbsolutePath());
	}
	
	@Test
	public void execute() {
		task.execute();
		assertEquals(1, destDir.listFiles().length);
		assertTrue(new File(destDir, "test.htm").exists());
	}
	
	@Test
	public void noSrcDirDefined() {
		ex.expect(BuildException.class);
		ex.expectMessage("'srcdir' is a required field");
		task.setSrcdir(null);
		task.execute();
	}
	
	@Test
	public void noDestDirDefined() {
		ex.expect(BuildException.class);
		ex.expectMessage("'destdir' is a required field");
		task.setDestdir(null);
		task.execute();
	}
	
	
}
