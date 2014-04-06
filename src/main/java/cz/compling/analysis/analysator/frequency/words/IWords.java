package cz.compling.analysis.analysator.frequency.words;

import cz.compling.model.Words;
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
public interface IWords extends RuleHandler<WordFrequencyRule> {

	Words getWords();
}
