package cz.compling.analysis;

import cz.compling.analysis.analysator.frequency.character.ICharacterFrequency;

/**
 *
 * This interface contains methods which should be performed in character analysis
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 19:18</dd>
 * </dl>
 */
public interface CharacterAnalysable {

	/**
	 * Returns frequency of characters in the text
	 *
	 * @return frequency of characters in the text
	 */
	ICharacterFrequency getCharacterFrequency();

}
