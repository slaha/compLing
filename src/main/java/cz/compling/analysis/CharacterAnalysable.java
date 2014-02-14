package cz.compling.analysis;

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
public interface CharacterAnalysable {

	/**
	 * Returns length of the text in characters. Counts every character
	 *
	 * @return length of the text in characters
	 */
	int getPlainTextLength();

}
