package cz.compling.analysis.analysator.frequency.character;

import cz.compling.model.CharacterFrequency;
import cz.compling.rules.BaseRuleHandler;
import cz.compling.rules.RuleHandler;
import cz.compling.rules.RuleObserver;
import cz.compling.text.Text;
import cz.compling.text.TextModificationRule;
import cz.compling.utils.Reference;


/**
 *
 * This class computes and holds frequency of characters in text. It respects registered rules
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 19:51</dd>
 * </dl>
 */
public class CharacterFrequencyImpl implements ICharacterFrequency, RuleObserver<TextModificationRule> {

	/** Text to analyse */
	private final Text text;

	private final CharacterFrequency frequency;

	private final RuleHandler<CharacterFrequencyRule> ruleHandler;

	private boolean dirty;

	public CharacterFrequencyImpl(Text text) {
		if (text == null) {
			throw new IllegalArgumentException("Parameter text cannot be null");
		}
		this.ruleHandler = new BaseRuleHandler<CharacterFrequencyRule>();
		this.text = text;
		text.registerRuleObserver(this);
		this.frequency = new CharacterFrequency(text.getPlainText().length());
		
		compute();
	}

	private void compute() {
		final String plainText = text.getPlainText();
		final Reference<Integer> position = new Reference<Integer>();
		final Reference<String> putToMap = new Reference<String>();

		for (int index = 0; index < plainText.length(); index++) {
			final char charAtIndex = plainText.charAt(index);

			position.value = index;
			putToMap.value = String.valueOf(charAtIndex);
			for (CharacterFrequencyRule rule : getRegisteredRules()) {
				//..if rule matches, putToMap and position's values are set properly within modify method
				if (rule.modify(plainText, putToMap, position)) {
					//..rule matches. Save returned position
					index = position.value;
					break;
				}
			}
			if (putToMap.value == null) {
				//..skip this loop
				continue;
			}

			frequency.put(putToMap.value);
		}

		dirty = false;
	}

	@Override
	public CharacterFrequency getCharacterFrequency() {

		if (dirty) {
			frequency.flush();
			compute();
		}
		return frequency;
	}

	@Override
	public void onRuleAdded(TextModificationRule addedRule, Iterable<TextModificationRule> rules) {
		dirty = true;
	}

	@Override
	public void onRuleRemoved(TextModificationRule removedRule, Iterable<TextModificationRule> rules) {
		dirty = true;
	}

	@Override
	public Iterable<CharacterFrequencyRule> getRegisteredRules() {
		return ruleHandler.getRegisteredRules();
	}

	@Override
	public void registerRule(CharacterFrequencyRule rule) {
		ruleHandler.registerRule(rule);
		dirty = true;
	}

	@Override
	public boolean removeRule(CharacterFrequencyRule rule) {
		if (ruleHandler.removeRule(rule)) {
			dirty = true;
			return true;
		}
		return false;
	}

	@Override
	public void registerRuleObserver(RuleObserver<CharacterFrequencyRule> observer) {
		ruleHandler.registerRuleObserver(observer);
	}

	@Override
	public boolean unregisterRuleObserver(RuleObserver<CharacterFrequencyRule> observer) {
		return ruleHandler.unregisterRuleObserver(observer);
	}

	@Override
	public Iterable<RuleObserver<CharacterFrequencyRule>> getRegisteredObserves() {
		return ruleHandler.getRegisteredObserves();
	}
}