package cz.compling.analysis.analysator.poems.impl;

import cz.compling.AbstTest;
import cz.compling.analysis.analysator.poems.assonance.IAssonance;
import cz.compling.model.Assonance;
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
public class AssonanceImplTest extends AbstTest {

	private static IAssonance analyser;

	//..reg exp '(a|e|i|o|u|y|á|é|í|ó|ů|ú|ý)'
	private final static String ONLY_VOCALS = "ůoyiouaiýoýeáaiýiyaéaoéuáeeouíooáioeaíaueýeaáíoaeaaeáíaééeaaioeoaaíoeáoaíeioueoaaiíaaáiaíoieyuýaýuoáiaaýeeaaaíaueouoíayeaeíoueéíeeíueoeouaíoéioueooaeiaaouaíeieáioíeááeuaoaoíuíoíoáyíeaíáaéeaíaeeioááááaieíááoeoáoeoouoýoeiaýooeoíouíuýoeioeoaaiíueáayaíyeáaeaiíiaeeeoaíeyeeaoíaaeeooaíeyeeeaáíaeeéaeáíaééeaaiyieeioeíOeuaioieuaaaiíouooyaíiýoíueáeiíauyiaéioeeiáaíiooeeueoeoiueoéoeoooyoeoeeoaíiáoeaeoooaoíaíoueoaaiíaoaueaíaouuíyeáíáyaeeoyíaieíaíiaíioieáaeouoíoíáeaýiýeouýoeiieeoíáeaeuýoeieoíaiíOeuéooáeíiýueeaeeeaáíaaaýaýeííaioueoaiaiueeaaiaéeaáeaíaioáouíaaayueouaáeáouíeeaiíeýáeeéeuáýaaíéuaiouoúuoiuouuouíAáeeoíaýeeieaaýaáeoeeáyouíaáaíeaueeeeaiuoiaíaoaaiyíaeíaooáaooeooaááaéeoooieoeiyaiaououáiiíaeoaeiíáaeeooíieyeoeiáaeoíeeéaouieaíaoyeiyíáaiéoeeoueeoooeoueoeyaiéeieaíeeíeooiéeeiéeíiéaáayeaieuůeaiíaéeaouíeaíaaiíaaaiiyíououeíouyuioeioáeuiiiooooouáaaeíiiiaeoáaouuaaouáaaeueeoáaeéeeouaíoiaouaeuuýeéouaíeéiyiyíouaaeéuaůioúuyíiioiueeíoeeuíoaaeáoaeuuauuíáeuyéáieeeůeaaouíoeíeáaeáeaaouíáaííiyíoyeuaeaueáeaeaíeieáaeaeoeáaííáueeaááooeaýaeéoeaayáaeaíiaoýaeeaoíiaeaíaeeeoaíeuueůaayaieiaaůioůeoooouíaíuiuoiuayůeauyáueoieůeyuaouaíeoouaoaoieůouaíaoaaiyioouíáýaůeáeeoáuaaaiouaůaaeeaííéoaueaéoueéyaeáéaueeůaáeiiauaíieieaaáyeoiiaíiaoaaiyíoouíáýaůeáeeoáuoeeioaoíeiyíaíueoeuuuaoeeáouueooueouuaůeaiíáííuáouuaiuííííaoaaiyíaeaiéoauáuáeaýaueoeoueaaeoýoeiaiéaeeayaioaiéáyaoéeeayeeeiieaáieeoieeíaíaoaaiyíAeaaeeeíáeeíaeeíaeeioyíéeýíeíííeooíeeíáéoííaoíáayáeoeíáeoíoaíaáueooíueoíeouaíeoíeiyí";

	@BeforeClass
	public static void setUp() throws Exception {

		AbstTest.setUp();

		analyser = getCompLing().geAssonanceAnalyser(new String[]{"a", "e", "i", "o", "u", "y", "á", "é", "í", "ó", "ů", "ú", "ý"});
	}

	@Test
	public void testGetMaxStep() throws Exception {
		Assonance assonance = analyser.getAssonance();

		int step = assonance.getMaxStep();

		Assert.assertEquals(ONLY_VOCALS.length() / 2 + 1, step);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAlliterationFor0() throws Exception {
		Assonance assonance = analyser.getAssonance();
		assonance.getAssonanceFor(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAlliterationForMax() throws Exception {
		Assonance assonance = analyser.getAssonance();
		assonance.getAssonanceFor(assonance.getMaxStep()+1);
	}

	private int expectedFor(int step) {
		step = step - 1;

		int exp = 0;
		char last = 0;
		char current;
		for (int i = 0; i < ONLY_VOCALS.length() - step; i++) {
			if (last > 0) {
				current = ONLY_VOCALS.charAt(i + step);
				if (current == last) {
					exp++;
				}
			}
			last = ONLY_VOCALS.charAt(i);
		}
		return exp;
	}

	@Test
	public void testGetAlliterationFor1() throws Exception {
		Assonance assonance = analyser.getAssonance();
		int assonanceFor1 = assonance.getAssonanceFor(1);

		Assert.assertEquals(expectedFor(1), assonanceFor1);
	}

	@Test
	public void testGetAlliterationFor10() throws Exception {
		Assonance assonance = analyser.getAssonance();
		int assonanceFor1 = assonance.getAssonanceFor(10);

		Assert.assertEquals(expectedFor(10), assonanceFor1);
	}
}
