package cz.compling.analysis.analysator.frequency.impl;

import cz.compling.analysis.analysator.frequency.CharacterFrequencyRule;
import cz.compling.analysis.analysator.frequency.ICharacterFrequency;
import cz.compling.analysis.analysator.impl.CharacterAnalyserImplTest;
import cz.compling.text.Text;
import cz.compling.text.TextModificationRule;
import cz.compling.utils.Reference;
import cz.compling.utils.TrooveUtils;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 *
 * TODO
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 26.2.14 11:43</dd>
 * </dl>
 */
public class CharacterFrequencyImplTest extends CharacterAnalyserImplTest {

	private static ICharacterFrequency frequencyAnalyser;

	@BeforeClass
	public static void setUp() throws Exception {
		CharacterAnalyserImplTest.setUp();

		frequencyAnalyser = getAnalyser().getCharacterFrequency();
	}

	@Test
	public void testGetFrequencyFor() throws Exception {

	}

	@Test
	public void testGetFrequencyFor1() throws Exception {

		int aFr = frequencyAnalyser.getCharacterFrequency().getFrequencyFor('a');
		Assert.assertEquals(273, aFr);

		int eFr = frequencyAnalyser.getCharacterFrequency().getFrequencyFor('e');
		Assert.assertEquals(299, eFr);

		int xFr = frequencyAnalyser.getCharacterFrequency().getFrequencyFor('x');
		Assert.assertEquals(0, xFr);

		TextModificationRule toLowerCaseRule = new TextModificationRule() {
			@Override
			public String modify(Text text) {
				return text.getPlainText().toLowerCase(Locale.getDefault());
			}
		};

		getCompLing().getText().registerRule(toLowerCaseRule);

		aFr = frequencyAnalyser.getCharacterFrequency().getFrequencyFor('a');
		Assert.assertEquals(275, aFr);

		CharacterFrequencyRule chRule = new CharacterFrequencyRule() {
			@Override
			public boolean modify(String text, Reference<String> putToFrequency, Reference<Integer> position) {
				final int index = position.value;
				if (text.charAt(index) == 'c' && text.charAt(index + 1 ) == 'h') {
					position.value = index + 1;
					putToFrequency.value = "ch";
					return true;
				}
				return false;
			}
		};

		frequencyAnalyser.registerRule(chRule);
		int chFr = frequencyAnalyser.getCharacterFrequency().getFrequencyFor("ch");
		Assert.assertEquals(55, chFr);

		aFr = frequencyAnalyser.getCharacterFrequency().getFrequencyFor('a');
		Assert.assertEquals(275, aFr);


		CharacterFrequencyRule onlyCharactersRule = new CharacterFrequencyRule() {

			@Override
			public boolean modify(String text, Reference<String> putToFrequency, Reference<Integer> position) {
				final char character = text.charAt(position.value);

				if (!Character.isLetter(character)) {
					putToFrequency.value = null;
					return true;
				}
				return false;
			}
		};

		frequencyAnalyser.registerRule(onlyCharactersRule);
		Assert.assertEquals(0, frequencyAnalyser.getCharacterFrequency().getFrequencyFor("."));
		Assert.assertEquals(0, frequencyAnalyser.getCharacterFrequency().getFrequencyFor(","));
		Assert.assertEquals(0, frequencyAnalyser.getCharacterFrequency().getFrequencyFor("?"));
	}

	@Test
	public void testGetAllByFrequency() throws Exception {
		List<Pair<String,Integer>> allByFrequencyDesc = frequencyAnalyser.getCharacterFrequency().getAllByFrequency(TrooveUtils.SortOrder.DESCENDING);
		int lastFreq = -1;
		for (Pair<String, Integer> pair : allByFrequencyDesc) {
			if (lastFreq < 0) {
				lastFreq = pair.getValue1();
			} else {
				if (lastFreq - pair.getValue1() < 0) {
					Assert.fail(String.format("Not sorted descending. Old value %d ; new value %d", lastFreq, pair.getValue1()));
				}
				lastFreq = pair.getValue1();
			}
		}

		List<Pair<String,Integer>> allByFrequencyAsc = frequencyAnalyser.getCharacterFrequency().getAllByFrequency(TrooveUtils.SortOrder.ASCENDING);
		lastFreq = -1;
		for (Pair<String, Integer> pair : allByFrequencyAsc) {
			if (lastFreq < 0) {
				lastFreq = pair.getValue1();
			} else {
				if (lastFreq - pair.getValue1() > 0) {
					Assert.fail(String.format("Not sorted ascending. Old value %d ; new value %d", lastFreq, pair.getValue1()));
				}
				lastFreq = pair.getValue1();
			}
		}

		Collections.reverse(allByFrequencyAsc);

		Assert.assertEquals(allByFrequencyAsc, allByFrequencyDesc);
	}
}
