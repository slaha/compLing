package cz.compling.analysis.analysator.frequency.words;

import cz.compling.model.WordFrequency;
import cz.compling.rules.BaseRuleHandler;
import cz.compling.rules.RuleHandler;
import cz.compling.rules.RuleObserver;
import cz.compling.text.Text;
import cz.compling.text.TextModificationRule;
import cz.compling.utils.Reference;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 1.3.14 11:19</dd>
 * </dl>
 */
public class WordFrequencyImpl implements IWordFrequency {

	/** Text to analyse */
	private final Text text;

	/** Frequency of words (word is key, frequency is value) */
	private final WordFrequency frequency;

	private final RuleHandler<WordFrequencyRule> ruleHandler;

	public WordFrequencyImpl(Text text) {
		this.text = text;
		frequency = new WordFrequency();
		ruleHandler = new BaseRuleHandler<WordFrequencyRule>();
		compute();
	}

	private void compute() {
		//..first we have to remove all non-alphabet characters.
		//..All other characters are replaced by space
		TextModificationRule removeNonAlphabetCharacters = new TextModificationRule() {
			@Override
			public String modify(Text text) {
				String plainText = text.getPlainText();
				StringBuilder sb = new StringBuilder(plainText.length());
				for (char c : plainText.toCharArray()) {
					sb.append(Character.isLetter(c) ? c : ' ');
				}
				return sb.toString().trim();
			}
		};
		final String onlyAlphabetText = text.applyRule(removeNonAlphabetCharacters).getPlainText();

		//..Split by space(s). Used String.split because javadoc to StringTokenizer says "its use is discouraged in new code"
		String[] words = onlyAlphabetText.split("\\s+");

		final Reference<String> word = new Reference<String>();
		final Reference<Integer> length = new Reference<Integer>();
		for (String nextWord : words) {
			word.value = nextWord;
			length.value = nextWord.length();
			applyRules(word, length);
			frequency.put(word.value, length.value);
		}	
	}

	private void applyRules(Reference<String> word, Reference<Integer> length) {

		for (WordFrequencyRule rule : getRegisteredRules()) {
			if (rule.modify(word, length)) {
				return;
			}
		}
	}

	@Override
	public WordFrequency getWordFrequency() {
		return frequency;
	}

	@Override
	public Iterable<WordFrequencyRule> getRegisteredRules() {
		return ruleHandler.getRegisteredRules();
	}

	@Override
	public void registerRule(WordFrequencyRule rule) {
		ruleHandler.registerRule(rule);
	}

	@Override
	public boolean removeRule(WordFrequencyRule rule) {
		return ruleHandler.removeRule(rule);
	}

	@Override
	public void registerRuleObserver(RuleObserver<WordFrequencyRule> observer) {
		ruleHandler.registerRuleObserver(observer);
	}

	@Override
	public boolean unregisterRuleObserver(RuleObserver<WordFrequencyRule> observer) {
		return ruleHandler.unregisterRuleObserver(observer);
	}

	@Override
	public Iterable<RuleObserver<WordFrequencyRule>> getRegisteredObserves() {
		return ruleHandler.getRegisteredObserves();
	}
}
