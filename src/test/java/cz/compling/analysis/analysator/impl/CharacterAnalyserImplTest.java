package cz.compling.analysis.analysator.impl;

import cz.compling.AbstTest;
import cz.compling.TestUtils;
import cz.compling.analysis.analysator.CharacterAnalyser;
import org.junit.Assert;
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
public class CharacterAnalyserImplTest extends AbstTest {

	private static final int TEXT_LENGTH = 5120;

	protected static CharacterAnalyser getAnalyser() {
		return analyser;
	}

	private static CharacterAnalyser analyser;

	@BeforeClass
	public static void setUp() throws Exception {

		AbstTest.setUp();

		analyser = getCompLing().getCharacterAnalyser();
	}

	@Test
	public void testGetPlainTextLength() throws Exception {
		int length = analyser.getCharacterFrequency().getCharacterFrequency().getPlainTextLength();

		int realLength;
		if (TestUtils.isLinux()) {
			realLength = TestUtils.fileSize(AbstTest.FILE, TEXT_LENGTH);
		} else {
			realLength = TEXT_LENGTH;
		}
		Assert.assertEquals(realLength, length);

	}
}
