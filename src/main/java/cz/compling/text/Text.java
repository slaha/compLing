package cz.compling.text;


import cz.compling.rules.CharacterModificationRule;
import cz.compling.rules.Rule;
import cz.compling.rules.TextModificationRule;
import cz.compling.rules.WordModificationRule;

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

	/**
	 * Register new {@code Rule} to manipulate with text
	 */
	void registerRule(Rule rule);

	/**
	 *  @return all registered {@code CharacterModificationRule}s
	 */
	Iterable<? extends CharacterModificationRule> getCharacterModificationRules();

	/**
	 * Apply {@code rule} to the text (respect all previously added rules). Does not register the {@code rule} (one shot)
	 *
	 * @param rule rule to apply
	 *
	 * @return text after all rules (including {@code rule}) are applied
	 * */
	String applyRule(TextModificationRule rule);

	Iterable<? extends WordModificationRule> getWordModificationRules();
}
