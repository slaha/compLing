package cz.compling.analysis.analysator.frequency.character;

import cz.compling.model.CharacterFrequency;
import cz.compling.rules.RuleHandler;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 1.3.14 11:17</dd>
 * </dl>
 */
public interface ICharacterFrequency extends RuleHandler<CharacterFrequencyRule> {

	CharacterFrequency getCharacterFrequency();
}
