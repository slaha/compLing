package cz.compling.analysis.poem;

import cz.compling.analysis.analysator.poems.assonance.IAssonance;

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
public interface AssonanceAnalysable {

	/**
	 * Returns assonance of text
	 *
	 * @return assonance in text
	 */
	IAssonance getAssonance(String[] vocals);

}
