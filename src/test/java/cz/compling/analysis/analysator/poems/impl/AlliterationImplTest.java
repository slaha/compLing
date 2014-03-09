package cz.compling.analysis.analysator.poems.impl;

import cz.compling.AbstTest;
import cz.compling.analysis.analysator.AlliterationAnalyser;
import cz.compling.analysis.analysator.poems.IAlliteration;
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

	private static AlliterationAnalyser analyser;

	@BeforeClass
	public static void setUp() throws Exception {

		AbstTest.setUp();

		analyser = getCompLing().getAlliterationAnalyser();
	}
	@Test
	public void testGetAlliterationFor() throws Exception {
		IAlliteration iAlliteration = analyser.getAlliteration();

		//..V půlnoc kdysi v soumrak čirý chorý bděl jsem sám a sirý,
		Alliteration alliterationFor1 = iAlliteration.getAlliterationFor(1);
		Assert.assertEquals(0, alliterationFor1.getAlliterationFor('a'));
		Assert.assertEquals(2, alliterationFor1.getAlliterationFor('v'));
		Assert.assertEquals(3, alliterationFor1.getAlliterationFor('s'));
		Assert.assertEquals(1, alliterationFor1.getNumberOfVerse());
		Assert.assertEquals(12, alliterationFor1.getCountOfWordsInVerse());
		alliterationFor1 = null;

		//..po té, již zvou Leonora v nebi, prahla zoufajíc, —
		Alliteration alliterationFor11 = iAlliteration.getAlliterationFor(11);
		Assert.assertEquals(0, alliterationFor11.getAlliterationFor('a'));
		Assert.assertEquals(2, alliterationFor11.getAlliterationFor('p'));
		Assert.assertEquals(2, alliterationFor11.getAlliterationFor('z'));
		Assert.assertEquals(11, alliterationFor11.getNumberOfVerse());
		Assert.assertEquals(9, alliterationFor11.getCountOfWordsInVerse());
		alliterationFor11 = null;

		//..Tiše, srdce! podívám se na zjev u svých okenic!
		Alliteration alliterationFor35 = iAlliteration.getAlliterationFor(35);
		Assert.assertEquals(3, alliterationFor35.getAlliterationFor('s'));
		Assert.assertEquals(0, alliterationFor35.getAlliterationFor('o'));
		Assert.assertEquals(35, alliterationFor35.getNumberOfVerse());
		Assert.assertEquals(9, alliterationFor35.getCountOfWordsInVerse());
		alliterationFor35 = null;

		//..Přestaň rvát mi srdce, šelmo, kliď se z dveří prchajíc!“
		Alliteration alliterationFor101 = iAlliteration.getAlliterationFor(101);
		Assert.assertEquals(2, alliterationFor101.getAlliterationFor('p'));
		Assert.assertEquals(0, alliterationFor101.getAlliterationFor('r'));
		Assert.assertEquals(2, alliterationFor101.getAlliterationFor('s'));
		Assert.assertEquals(101, alliterationFor101.getNumberOfVerse());
		Assert.assertEquals(10, alliterationFor101.getCountOfWordsInVerse());
	}

	@Test
	public void testGetVerseCount() throws Exception {
		IAlliteration iAlliteration = analyser.getAlliteration();

		int verseCount = iAlliteration.getVerseCount();

		//..six verses in 18 strophes
		Assert.assertEquals(18 * 6, verseCount);
	}
}
