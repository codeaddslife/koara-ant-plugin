package io.koara.ant;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FilenameExtensionFilterTest {

	private FilenameExtensionFilter filter;
	
	@Before
	public void setUp() {
		filter = new FilenameExtensionFilter("kd");
	}
	
	@Test
	public void filter() {
		assertTrue(filter.accept(null, "test.kd"));
		assertTrue(filter.accept(null, "test.kd.kd"));
		assertFalse(filter.accept(null, "test"));
		assertFalse(filter.accept(null, "test.kda"));
		assertFalse(filter.accept(null, "test.kd.txt"));
	}
	
}
