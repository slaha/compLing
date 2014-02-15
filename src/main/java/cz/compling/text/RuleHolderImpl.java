package cz.compling.text;

import cz.compling.rules.CharacterModificationRule;
import cz.compling.rules.Rule;
import cz.compling.rules.TextModificationRule;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 15.2.14 9:17</dd>
 * </dl>
 */
public class RuleHolderImpl implements RuleHolder {

	private final List<TextModificationRule> textModificationRules;
	private final List<CharacterModificationRule> characterModificationRules;
	private final List<Rule> plainRules;

	public RuleHolderImpl() {
		textModificationRules = new ArrayList<TextModificationRule>();
		characterModificationRules = new ArrayList<CharacterModificationRule>();
		plainRules = new ArrayList<Rule>();
	}


	private void add(TextModificationRule rule) {
		textModificationRules.add(rule);
	}

	private void add(CharacterModificationRule rule) {
		characterModificationRules.add(rule);
	}

	public void add(Rule rule) {
		if (rule instanceof TextModificationRule) {
			add((TextModificationRule)rule);
		} else if (rule instanceof CharacterModificationRule) {
			add((CharacterModificationRule)rule);
		}
	}

	public boolean anyModificationRule() {
		return !textModificationRules.isEmpty();
	}

	@Override
	public Iterable<? extends CharacterModificationRule> getCharacterModificationRules() {
		return characterModificationRules;
	}

	public Iterable<? extends TextModificationRule> getModificationRules() {
		return textModificationRules;
	}
}
