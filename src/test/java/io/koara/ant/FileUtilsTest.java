package io.koara.ant;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileUtilsTest {

	@Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void getKoaraDocuments() throws IOException {
		temporaryFolder.newFile("test.kd");
		List<File> files = FileUtils.getKoaraDocuments(temporaryFolder.getRoot());
		assertEquals(1, files.size());
		assertEquals("test.kd", files.get(0).getName());
	} 
	
	@Test
	public void getKoaraDocumentsExtensionInUppercase() throws IOException {
		temporaryFolder.newFile("test.KD");
		List<File> files = FileUtils.getKoaraDocuments(temporaryFolder.getRoot());
		assertEquals(1, files.size());
		assertEquals("test.KD", files.get(0).getName());
	} 
	
}
