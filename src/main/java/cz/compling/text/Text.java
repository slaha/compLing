package cz.compling.text;


import cz.compling.rules.RuleHandler;

import java.util.Collection;

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
public interface Text extends RuleHandler<TextModificationRule> {

	/**
	 * @return text
	 */
	String getPlainText();

	/**
	 * @return text separated by lines
	 */
	Collection<Line> getLines();

	Text applyRule(TextModificationRule rule);

}
