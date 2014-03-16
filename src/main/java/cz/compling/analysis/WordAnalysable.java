package cz.compling.analysis;

import cz.compling.analysis.analysator.frequency.words.IWordFrequency;

/**
 *
 * This interface contains methods which should be performed in character analysis
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 19:18</dd>
 * </dl>
 */
public interface WordAnalysable {

	/**
	 * Returns word frequency object that in the text
	 *
	 * @return frequency of words in the text
	 */
	IWordFrequency getWordFrequency();

}
