package cz.compling.text;

import cz.compling.rules.CharacterModificationRule;
import cz.compling.rules.Rule;
import cz.compling.rules.TextModificationRule;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 15.2.14 9:34</dd>
 * </dl>
 */
public interface RuleHolder {

	void add(Rule rule);

	boolean anyModificationRule();

	Iterable<? extends CharacterModificationRule> getCharacterModificationRules();

	Iterable<? extends TextModificationRule> getModificationRules();
}
