package cz.compling.analysis.analysator.impl;

import cz.compling.AbstTest;
import cz.compling.analysis.analysator.WordFrequencyAnalyser;
import org.junit.BeforeClass;

/**
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 19:22</dd>
 * </dl>
 */
public class WordFrequencyAnalyserImplTest extends AbstTest {

	protected static WordFrequencyAnalyser getAnalyser() {
		return analyser;
	}

	private static WordFrequencyAnalyser analyser;

	@BeforeClass
	public static void setUp() throws Exception {

		AbstTest.setUp();

		analyser = getCompLing().getWordFrequencyAnalyser();
	}
}
