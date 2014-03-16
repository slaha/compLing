package cz.compling.analysis.analysator.impl;

import cz.compling.analysis.analysator.CharacterAnalyser;
import cz.compling.analysis.analysator.frequency.ICharacterFrequency;
import cz.compling.analysis.analysator.frequency.character.CharacterFrequencyImpl;
import cz.compling.text.Text;

/**
 *
 * Simple implementation of CharacterAnalyser interface
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 19:12</dd>
 * </dl>
 */
public class CharacterAnalyserImpl implements CharacterAnalyser {

	private final Text text;

	public CharacterAnalyserImpl(Text text) {
		this.text = text;
	}

	@Override
	public int getPlainTextLength() {
		return text.getPlainText().length();
	}

	@Override
	public ICharacterFrequency getCharacterFrequency() {

		return new CharacterFrequencyImpl(text);
	}
}
