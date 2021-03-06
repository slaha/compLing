package cz.compling.analysis.analysator.poems.impl;

import cz.compling.AbstTest;
import cz.compling.CompLing;
import cz.compling.analysis.analysator.poems.denotation.IDenotation;
import cz.compling.model.denotation.DenotationWord;
import cz.compling.model.denotation.Hreb;
import cz.compling.model.denotation.HrebNotFoundException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DenotationmplTest extends AbstTest {



	private static IDenotation analyser;

	@BeforeClass
	public static void setUp() throws Exception {

		AbstTest.setUp();

		CompLing compLing = CompLing.getInstance(SOKOL_POEM);
		analyser = compLing.poemAnalysis().denotationAnalysis();
	}

	@Test
	public void test001AddNewHreb() throws Exception {
		int newHreb = analyser.createNewHreb();

		Assert.assertEquals(1, newHreb);
	}

	@Test(expected = HrebNotFoundException.class)
	public void test010GetHreb() throws Exception {
		Hreb hreb = analyser.getHreb(1);

		Assert.assertNotNull(hreb);

		analyser.getHreb(10);
	}

	@Test
	public void test015ContainsHreb() throws Exception {
		Assert.assertEquals(true, analyser.containsHreb(1));
		Assert.assertEquals(false, analyser.containsHreb(10));
	}

	@Test(expected = HrebNotFoundException.class)
	public void test020RemoveHreb() throws Exception {
		final int hreb = analyser.removeHreb(1);

		Assert.assertEquals(1, 1); //..no exception

		analyser.removeHreb(10);
	}

	@Test
	public void test100AddWord() throws Exception {
		final int newHreb = analyser.createNewHreb();
		final Hreb hreb = analyser.getHreb(newHreb);

		DenotationWord word = analyser.getWord(1);
		DenotationWord word2 = analyser.getWord(2);
		analyser.joinWords(2, 3);
		analyser.joinWords(word.getNumber(), analyser.getWord(2).getNumber());
		hreb.addWord(word, null);

		for (int i = 1; i <= analyser.getCountOfWords(); i++) {
			Assert.assertNotNull(analyser.getWord(i));
		}
		

		Assert.assertEquals("Letel", word.toString());

	}

	private final static String SOKOL_POEM =
		"Letel mladý sokol ponad Hron odhora,\n" +
		"čakala naň v horách zlatoústá zora:\n" +
		"Nevzdychal, nežialil, len mu duša vädla,\n" +
		"len mu kedys' v hronské vody jedna slza spadla.\n" +
		"\n" +
		"Spytuje sa zora: sokolík, čo ti je?\n" +
		"Či hladom umieraš? Či ťa orol bije?\n" +
		"Či ti strelec v poli krídelko poranil?\n" +
		"Či ti pánboh širým svetom prelietať  zabránil?\n" +
		"\n" +
		"Krásná zora moja! Hladu sa nebojím,\n" +
		"Brok ma nedočiahne, pred orlom obstojím,\n" +
		"Slobodno mi lietať v širej sveta diali:\n" +
		"ale mi môj háj zelený vrahovia zoťali.\n" +
		"\n" +
		"Drahý môj, nermúť sa, že ti háj zoťali,\n" +
		"veď oni môj zlatý zámok nezrúcali:\n" +
		"v ňom si poletujme ponad biedne svety,\n" +
		"tak žiaľ nikdy nezkalí naše šťastné lety!\n" +
		"\n" +
		"Oj, nie, duša moja! - sokol jej otvárá -\n" +
		"Zámok tvoj zaplaví hromová mrákava:\n" +
		"Zleť ty so mnou k stráňám zrúbanej dúbravy,\n" +
		"v zlatom blesku tvojich očí háj môj sa zotaví.\n" +
		"\n" +
		"Na jarabom hrdle sokola prikľatá\n" +
		"Ligoce sa slávou zorička zo zlata:\n" +
		"v očiach sokolových sladko sa uziera,\n" +
		"pozlacujúc voľných krídel milencových piera.\n" +
		"\n" +
		"Letel mladý sokol ponad Hron oddola,\n" +
		"Letela zorička na hrdle sokola:\n" +
		"Mrákava zablysla, skrížili sa strely\n" +
		"Ach, bože môj! Nad milými vody sa zareli!";
}
