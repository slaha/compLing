package cz.compling.analysis.analysator.frequency.words;

import cz.compling.model.Words;
import cz.compling.rules.BaseRuleHandler;
import cz.compling.rules.RuleHandler;
import cz.compling.rules.RuleObserver;
import cz.compling.text.Text;

/**
 *
 * TODO
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 6.4.14 12:18</dd>
 * </dl>
 */
public class WordsImpl implements IWords {

	private final Text text;
	private final RuleHandler<WordFrequencyRule> ruleHandler;
	private final Words words;

	public WordsImpl(Text text) {
		this.text = text;
		this.ruleHandler = new BaseRuleHandler<WordFrequencyRule>();
		this.words = new Words();

		compute();
	}

	private void compute() {
		//..first we have to remove all non-alphabet characters.
		//..All other characters are replaced by space
		final String onlyAlphabetText = text.applyRule(IWords.SPLIT_TO_WORDS).getPlainText();

		//..Split by space(s). Used String.split because javadoc to StringTokenizer says "its use is discouraged in new code"
		String[] words = onlyAlphabetText.split("\\s+");

		for (String word : words) {
			this.words.add(word);
		}

	}

	@Override
	public Words getWords() {
		return words;
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
