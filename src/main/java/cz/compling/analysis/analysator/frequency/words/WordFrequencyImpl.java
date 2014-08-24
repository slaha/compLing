package cz.compling.analysis.analysator.frequency.words;

import cz.compling.model.WordFrequency;
import cz.compling.model.Words;
import cz.compling.rules.BaseRuleHandler;
import cz.compling.rules.RuleHandler;
import cz.compling.rules.RuleObserver;
import cz.compling.text.Text;
import cz.compling.utils.Reference;
import org.apache.commons.lang3.StringUtils;

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

	private boolean recompute = true;

	/** Text to analyse */
	private final Text text;

	private final Words words;

	/** Frequency of words (word is key, frequency is value) */
	private final WordFrequency frequency;

	private final RuleHandler<WordFrequencyRule> ruleHandler;

	public WordFrequencyImpl(Text text, IWords words) {
		this.text = text;
		this.words = words.getWords();
		frequency = new WordFrequency();
		ruleHandler = new BaseRuleHandler<WordFrequencyRule>();
	}

	private void compute() {

		final Reference<String> word = new Reference<String>();
		final Reference<Integer> length = new Reference<Integer>();
		for (String nextWord : words) {
			word.value = nextWord;
			length.value = nextWord.length();
			applyRules(word, length);
			if (StringUtils.isNotBlank(word.value) && length.value > 0) {
				frequency.put(word.value, length.value);
			}
		}
		recompute = false;
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
		if (recompute) {
			compute();
		}
		return frequency;
	}

	@Override
	public Iterable<WordFrequencyRule> getRegisteredRules() {
		return ruleHandler.getRegisteredRules();
	}

	@Override
	public void registerRule(WordFrequencyRule rule) {
		ruleHandler.registerRule(rule);
		recompute = true;
	}

	@Override
	public boolean removeRule(WordFrequencyRule rule) {
		boolean removed = ruleHandler.removeRule(rule);
		if (removed) {
			recompute = true;
		}
		return removed;
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
