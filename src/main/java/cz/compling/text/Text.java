package cz.compling.text;


import cz.compling.rules.CharacterModificationRule;
import cz.compling.rules.Rule;
import cz.compling.rules.TextModificationRule;

/**
 *
 * <p></p>This interface defines all possible analysis of text.
 *
 * <p>Classes that are implementing this interface should be immutable
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 18:31</dd>
 * </dl>
 */
public interface Text {

	/**
	 * @return text
	 */
	String getPlainText();

	/**
	 * @return text separated by lines
	 */
	String[] getLines();

	void registerRule(Rule rule);

	Iterable<? extends CharacterModificationRule> getCharacterModificationRules();

	String applyRule(TextModificationRule rule);
}
