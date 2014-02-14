package cz.compling.analysis.analysator.impl;

import cz.compling.CompLing;
import cz.compling.CompLingTest;
import cz.compling.analysis.CharacterAnalysable;
import cz.compling.analysis.analysator.CharacterAnalyser;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 19:22</dd>
 * </dl>
 */
public class CharacterAnalyserImplTest extends TestCase {

	private static CharacterAnalyser analyser;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		analyser = CompLing.getInstance(FileUtils.readFileToString(CompLingTest.file)).getCharacterAnalyser();
		System.out.println("analyser init to " + analyser);
	}

	@Test
	public void testGetPlainTextLength() throws Exception {
		int length = analyser.getPlainTextLength();

		Assert.assertEquals(35, length);

	}
}
