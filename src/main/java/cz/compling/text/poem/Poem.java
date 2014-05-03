package cz.compling.text.poem;

import java.util.Collection;
import java.util.List;

/**
 *
 * This class represents poem.
 * <p>
 *     Each line of poem is a verse.
 * </p>
 * <p>
 *     One or more empty lines split poem to strophes
 * </p>
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 3.3.14 7:09</dd>
 * </dl>
 */
public interface Poem {

	/**
	 * @return poem as plain text
	 */
	String getPlainText();

	/**
	 * Returns verses of the poem. Does not return empty (strophes separating) lines
	 *
	 * @return verses of the poem
	 */
	List<Verse> getVerses();

	/**
	 * @return count of strophes in the poem
	 */
	int getCountOfStrophes();

	/**
	 * Returns text of the {@code strophe}<sup>th</sup> strophe of the poem as text.
	 *
	 * @param strophe number of strophe. Must be bigger than zero and <= {@code getCountOfStrophes()} return value
	 *
	 * @return text of the strophe
	 */
	String getStrophe(int strophe);

	Collection<Strophe> getStrophes();
	/**
	 * Returns text of the {@code strophe}<sup>th</sup> strophe of the poem as collection of verses.
	 *
	 *  @param strophe number of strophe. Must be bigger than zero and <= {@code getCountOfStrophes()} return value
	 *
	 * @return text of the strophe as collection of verses
	 */
	Collection<Verse> getVersesOfStrophe(int strophe);

	Poem applyRule(PoemModificationRule rule);
}
