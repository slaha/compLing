package cz.compling.analysis.analysator.impl;

import cz.compling.analysis.CharacterFrequency;
import cz.compling.rules.CharacterModificationRule;
import cz.compling.rules.TextModificationRule;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Reference;

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
public class CharacterFrequencyTest extends CharacterAnalyserImplTest {

	private static CharacterFrequency frequency;

	@BeforeClass
	public static void setUp() throws Exception {
		CharacterAnalyserImplTest.setUp();

		frequency = getAnalyser().getCharacterFrequency();
	}

	@Test
	public void testGetFrequencyFor() throws Exception {

	}

	@Test
	public void testGetFrequencyFor1() throws Exception {

		int aFr = frequency.getFrequencyFor('a');
		Assert.assertEquals(1, aFr);

		int eFr = frequency.getFrequencyFor('e');
		Assert.assertEquals(4, eFr);

		int xFr = frequency.getFrequencyFor('x');
		Assert.assertEquals(0, xFr);

		TextModificationRule rule = new TextModificationRule() {
			@Override
			public String modify(String text) {
				return text.toLowerCase(Locale.getDefault());
			}
		};

		getCompLing().registerRule(rule);

		CharacterFrequency frequency1 = getAnalyser().getCharacterFrequency();
		aFr = frequency1.getFrequencyFor('a');
		Assert.assertEquals(2, aFr);

		CharacterModificationRule chRule = new CharacterModificationRule() {
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

		getCompLing().registerRule(chRule);
		frequency1 = getAnalyser().getCharacterFrequency();
		int chFr = frequency1.getFrequencyFor("ch");
		Assert.assertEquals(1, chFr);

		aFr = frequency1.getFrequencyFor('a');
		Assert.assertEquals(2, aFr);


		CharacterModificationRule onlyCharactersRule = new CharacterModificationRule() {

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

		getCompLing().registerRule(onlyCharactersRule);
		frequency1 = getAnalyser().getCharacterFrequency();
		Assert.assertEquals(0, frequency1.getFrequencyFor("."));
		Assert.assertEquals(0, frequency1.getFrequencyFor(","));
		Assert.assertEquals(0, frequency1.getFrequencyFor("?"));
	}

	@Test
	public void testGetAllByFrequency() throws Exception {
		List<Pair<String,Integer>> allByFrequencyDesc = frequency.getAllByFrequency(true);
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

		List<Pair<String,Integer>> allByFrequencyAsc = frequency.getAllByFrequency(false);
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
