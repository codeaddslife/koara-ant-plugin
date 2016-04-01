package io.koara.ant;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ParseTaskTest {

	private ParseTask task;
	private String outputDir;
	@Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
	
	@Before
	public void setUp() throws Exception {
		outputDir = temporaryFolder.newFolder().getAbsolutePath();

		task = new ParseTask();
		task.setOutputDir(outputDir);
	}
	
	@Test
	public void execute() {
		task.execute();
		
		File outputFile = new File(outputDir, "x.htm");
		assertTrue(outputFile.exists());
	}
	
}
