package cz.compling.analysis.analysator.poems.impl;

import cz.compling.AbstTest;
import cz.compling.analysis.analysator.poems.verses.IVerses;
import cz.compling.model.Verses;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 9.3.14 14:20</dd>
 * </dl>
 */
public class VersesImplTest extends AbstTest {

	private static final int[] CHARS = new int[] {
		57, 49, 50, 45, 53, 24,
		53, 49, 52, 43, 50, 23,
		56, 41, 49, 52, 46, 23
	};

	private static final int[] WORDS = new int[] {
		12, 8, 8, 10, 11, 5,
		9, 10, 9, 8, 9, 5,
		12, 8, 8, 9, 8, 6
	};

	private static IVerses analyser;

	@BeforeClass
	public static void setUp() throws Exception {

		AbstTest.setUp();

		analyser = getCompLing().poemAnalysis().verses();
	}

	@Test
	public void testGetNumberOfVerses() throws Exception {
		int numberOfVerses = analyser.getVerses().getNumberOfVerses();

		Assert.assertEquals(108, numberOfVerses);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetVerseLengthsFor0() throws Exception {
		analyser.getVerses().getVerseLengthsFor(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetVerseLengthsForMax() throws Exception {
		Verses verses = analyser.getVerses();
		int numberOfVerses = verses.getNumberOfVerses();

		verses.getVerseLengthsFor(numberOfVerses + 1);
	}

	@Test
	public void testGetVerseLengthsFor() throws Exception {

		Verses verses = analyser.getVerses();
		//..first three strophes
		for (int i = 1; i <= 18; i++) {
			Verses.VerseLengths verseLengths = verses.getVerseLengthsFor(i);

			Assert.assertEquals("Wrong count of chars on line " + i, CHARS[i - 1],  verseLengths.charsCount);
			Assert.assertEquals("Wrong count of words on line " + i, WORDS[i - 1], verseLengths.wordsCount);
		}
		
	}

}
