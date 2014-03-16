package cz.compling.analysis.analysator.frequency;

import cz.compling.analysis.analysator.frequency.words.WordFrequencyRule;
import cz.compling.model.WordFrequency;
import cz.compling.rules.RuleHandler;

/**
 *
 * Methods that must be implemented for analysing frequency of words in text
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 1.3.14 11:19</dd>
 * </dl>
 */
public interface IWordFrequency extends RuleHandler<WordFrequencyRule> {

	WordFrequency getWordFrequency();
}
