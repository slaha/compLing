package cz.compling.text;

import cz.compling.rules.BaseRuleHandler;
import cz.compling.rules.RuleHandler;
import cz.compling.rules.RuleObserver;

import java.util.ArrayList;
import java.util.Collection;

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

	private final RuleHandler<TextModificationRule> ruleHandler;
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
		ruleHandler = new BaseRuleHandler<TextModificationRule>();
	}

	@Override
	public String getPlainText() {
		if (getRegisteredRules().iterator().hasNext()) {
			TextImplInternal txt = new TextImplInternal(plainText);
			for (TextModificationRule textModificationRule : getRegisteredRules()) {
				String afterRule = textModificationRule.modify(txt);
				txt = new TextImplInternal(afterRule);
			}
			return txt.getPlainText();
		}


		return plainText;
	}

	@Override
	public Collection<Line> getLines() {
		Collection<Line> lines = new ArrayList<Line>();
		for (String line : plainText.split("\\r?\\n")) {
			lines.add(new Line(line));
		}
		return lines;
	}

	@Override
	public Text applyRule(TextModificationRule removeNonAlphabetCharacters) {
		return new TextImpl(removeNonAlphabetCharacters.modify(this));
	}

	@Override
	public Iterable<TextModificationRule> getRegisteredRules() {
		return ruleHandler.getRegisteredRules();
	}

	@Override
	public void registerRule(TextModificationRule rule) {
		ruleHandler.registerRule(rule);
	}

	@Override
	public boolean removeRule(TextModificationRule rule) {
		return ruleHandler.removeRule(rule);
	}

	@Override
	public void registerRuleObserver(RuleObserver<TextModificationRule> observer) {
		ruleHandler.registerRuleObserver(observer);
	}

	@Override
	public boolean unregisterRuleObserver(RuleObserver<TextModificationRule> observer) {
		return ruleHandler.unregisterRuleObserver(observer);
	}

	@Override
	public Iterable<RuleObserver<TextModificationRule>> getRegisteredObserves() {
		return ruleHandler.getRegisteredObserves();
	}

	/**
	 * This class is used in {@code getPlainText} because of avoiding recursive calls (in lots of rules there is call to {@code getPlainText}.
	 *
	 * This class just simply returns plain text without applying rules
	 */
	private class TextImplInternal extends TextImpl {

		private final String plainTextLocal;

		public TextImplInternal(String text) {
			super(text);
			this.plainTextLocal = text;
		}

		@Override
		public String getPlainText() {
			return plainTextLocal;
		}
	}
}
