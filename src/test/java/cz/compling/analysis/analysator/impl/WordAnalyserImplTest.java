package cz.compling.analysis.analysator.impl;

import cz.compling.AbstTest;
import cz.compling.analysis.analysator.frequency.words.IWordFrequency;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 19:22</dd>
 * </dl>
 */
public class WordAnalyserImplTest extends AbstTest {

	protected static IWordFrequency getAnalyser() {
		return analyser;
	}

	private static IWordFrequency analyser;

	@BeforeClass
	public static void setUp() throws Exception {

		AbstTest.setUp();

		analyser = getCompLing().generalAnalysis().wordFrequency();
	}

	@Test
	public void testDummy() {

	}
}
