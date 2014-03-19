package cz.compling.analysis.analysator.poems.impl;

import cz.compling.AbstTest;
import cz.compling.CompLing;
import cz.compling.analysis.analysator.poems.alliteration.AlliterationRule;
import cz.compling.analysis.analysator.poems.alliteration.IAlliteration;
import cz.compling.model.Alliteration;
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
public class AlliterationImplTest extends AbstTest {

	private static IAlliteration analyser;

	@BeforeClass
	public static void setUp() throws Exception {

		AbstTest.setUp();

		analyser = getCompLing().poemAnalysis().alliteration();
	}
	@Test
	public void testGetAlliterationFor() throws Exception {
		IAlliteration iAlliteration = analyser;

		//..V půlnoc kdysi v soumrak čirý chorý bděl jsem sám a sirý,
		Alliteration.LineAlliteration alliterationFor1 = iAlliteration.getAlliteration().getAlliterationFor(1);
		Assert.assertEquals(0, alliterationFor1.getAlliterationFor('a'));
		Assert.assertEquals(2, alliterationFor1.getAlliterationFor('v'));
		Assert.assertEquals(3, alliterationFor1.getAlliterationFor('s'));
		Assert.assertEquals(1, alliterationFor1.getNumberOfVerse());
		Assert.assertEquals(12, alliterationFor1.getCountOfWordsInVerse());
		alliterationFor1 = null;

		//..po té, již zvou Leonora v nebi, prahla zoufajíc, —
		Alliteration.LineAlliteration alliterationFor11 = iAlliteration.getAlliteration().getAlliterationFor(11);
		Assert.assertEquals(0, alliterationFor11.getAlliterationFor('a'));
		Assert.assertEquals(2, alliterationFor11.getAlliterationFor('p'));
		Assert.assertEquals(2, alliterationFor11.getAlliterationFor('z'));
		Assert.assertEquals(11, alliterationFor11.getNumberOfVerse());
		Assert.assertEquals(9, alliterationFor11.getCountOfWordsInVerse());
		alliterationFor11 = null;

		//..Tiše, srdce! podívám se na zjev u svých okenic!
		Alliteration.LineAlliteration alliterationFor35 = iAlliteration.getAlliteration().getAlliterationFor(35);
		Assert.assertEquals(3, alliterationFor35.getAlliterationFor('s'));
		Assert.assertEquals(0, alliterationFor35.getAlliterationFor('o'));
		Assert.assertEquals(35, alliterationFor35.getNumberOfVerse());
		Assert.assertEquals(9, alliterationFor35.getCountOfWordsInVerse());
		alliterationFor35 = null;

		//..Přestaň rvát mi srdce, šelmo, kliď se z dveří prchajíc!“
		Alliteration.LineAlliteration alliterationFor101 = iAlliteration.getAlliteration().getAlliterationFor(101);
		Assert.assertEquals(2, alliterationFor101.getAlliterationFor('p'));
		Assert.assertEquals(0, alliterationFor101.getAlliterationFor('r'));
		Assert.assertEquals(2, alliterationFor101.getAlliterationFor('s'));
		Assert.assertEquals(101, alliterationFor101.getNumberOfVerse());
		Assert.assertEquals(10, alliterationFor101.getCountOfWordsInVerse());
	}

	@Test
	public void testGetVerseCount() throws Exception {
		IAlliteration iAlliteration = analyser;

		int verseCount = iAlliteration.getAlliteration().getVerseCount();

		//..six verses in 18 strophes
		Assert.assertEquals(18 * 6, verseCount);
	}

	@Test
	public void testGetAlliterationChRule() throws Exception {
		String poem = "Chceme do Chrudimi choditi častěji";

		CompLing instance = CompLing.getInstance(poem);
		IAlliteration alliteration = instance.poemAnalysis().alliteration();

		AlliterationRule rule = new AlliterationRule() {
			@Override
			public String modify(String word) {
				if (word.toLowerCase().startsWith("ch")) {
					return "ch";
				}
				return null;
			}
		};
		alliteration.registerRule(rule);

		Alliteration.LineAlliteration alliterationFor = alliteration.getAlliteration().getAlliterationFor(1);
		int ch = alliterationFor.getAlliterationFor("ch");

		Assert.assertEquals(3, ch);
	}
}
