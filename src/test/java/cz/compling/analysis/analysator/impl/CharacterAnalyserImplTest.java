package cz.compling.analysis.analysator.impl;

import cz.compling.AbstTest;
import cz.compling.analysis.CharacterFrequency;
import cz.compling.analysis.analysator.CharacterAnalyser;
import cz.compling.rules.CharacterModificationRule;
import cz.compling.rules.TextModificationRule;
import utils.Reference;

import java.util.Locale;

/**
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 19:22</dd>
 * </dl>
 */
public class CharacterAnalyserImplTest extends AbstTest {

	private CharacterAnalyser analyser;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		analyser = compLing.getCharacterAnalyser();
	}

	public void testGetPlainTextLength() throws Exception {
		int length = analyser.getPlainTextLength();

		assertEquals(38, length);

	}

	public void testGetCharacterFrequency() {
		CharacterFrequency frequency = analyser.getCharacterFrequency();

		int aFr = frequency.getFrequencyFor('a');
		assertEquals(1, aFr);

		int eFr = frequency.getFrequencyFor('e');
		assertEquals(4, eFr);

		int xFr = frequency.getFrequencyFor('x');
		assertEquals(0, xFr);

		TextModificationRule rule = new TextModificationRule() {
			@Override
			public String modify(String text) {
				return text.toLowerCase(Locale.getDefault());
			}
		};

		compLing.registerRule(rule);

		frequency = analyser.getCharacterFrequency();
		aFr = frequency.getFrequencyFor('a');
		assertEquals(2, aFr);

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

		compLing.registerRule(chRule);

		frequency = analyser.getCharacterFrequency();
		int chFr = frequency.getFrequencyFor("ch");
		assertEquals(1, chFr);

		aFr = frequency.getFrequencyFor('a');
		assertEquals(2, aFr);

	}
}
