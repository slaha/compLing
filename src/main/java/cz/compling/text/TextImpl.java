package cz.compling.text;

import cz.compling.rules.CharacterModificationRule;
import cz.compling.rules.Rule;
import cz.compling.rules.TextModificationRule;
import cz.compling.rules.WordModificationRule;

/**
 *
 * Basic implementation of Text interface
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 18:31</dd>
 * </dl>
 */
public class TextImpl implements Text {

	/** Text to analyse. Should be encoded in utf-8 */
	private final String plainText;

	private String rulesAppliedText;

	private final RuleHolder ruleHolder;

	/**
	 * Create new instance.
	 *
	 * @param text text to analyse. Cannot be null
	 *
	 * @throws java.lang.IllegalArgumentException if text is null
	 */
	public TextImpl(String text) {
		if (text == null) {
			throw new IllegalArgumentException("text cannot be null");
		}
		this.plainText = text;
		this.ruleHolder = new RuleHolderImpl();

	}

	@Override
	public String getPlainText() {

		if (ruleHolder.anyModificationRule()) {
			this.rulesAppliedText = plainText;
			for (TextModificationRule rule : ruleHolder.getModificationRules()) {
				this.rulesAppliedText = rule.modify(rulesAppliedText);
			}
			return rulesAppliedText;
		}

		return plainText;
	}

	@Override
	public String[] getLines() {
		return plainText.split("\\r?\\n");
	}

	@Override
	public void registerRule(Rule rule) {
		ruleHolder.add(rule);

	}

	@Override
	public Iterable<? extends CharacterModificationRule> getCharacterModificationRules() {
		return ruleHolder.getCharacterModificationRules();
	}

	@Override
	public String applyRule(TextModificationRule rule) {
		return rule.modify(getPlainText());

	}

	@Override
	public Iterable<? extends WordModificationRule> getWordModificationRules() {
		return ruleHolder.getWordModificationRules();
	}

}
