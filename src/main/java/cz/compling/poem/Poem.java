package cz.compling.poem;

import cz.compling.rules.TextModificationRule;

import java.util.Collection;

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
	 * Apply rule to text of poem. Do not register it (one shot)
	 *
	 * @param rule rule to apply
	 *
	 * @return text of the poem after {@code rule} is applied
	 */
	String applyRule(TextModificationRule rule);

	/**
	 * Returns verses of the poem. Does not return empty (strophes separating) lines
	 *
	 * @return verses of the poem
	 */
	Collection<? extends String> getVerses();

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

	/**
	 * Returns text of the {@code strophe}<sup>th</sup> strophe of the poem as collection of verses.
	 *
	 *  @param strophe number of strophe. Must be bigger than zero and <= {@code getCountOfStrophes()} return value
	 *
	 * @return text of the strophe as collection of verses
	 */
	Collection<? extends String> getVersesOfStrophe(int strophe);
}
