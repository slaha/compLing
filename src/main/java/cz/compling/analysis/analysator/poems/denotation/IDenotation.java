package cz.compling.analysis.analysator.poems.denotation;

import cz.compling.model.denotation.DenotationElement;
import cz.compling.model.denotation.DenotationWord;
import cz.compling.model.denotation.Spike;

import java.util.Collection;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 2.3.14 17:59</dd>
 * </dl>
 */
public interface IDenotation {

	/**
	 * Creates new empty spike
	 *
	 * @return number of spike (must be positive)
	 */
	int createNewSpike();

	/**
	 * Check if spike with number {@code number} exists
	 * @param number the number of spike
	 * @return true if spike exists; false otherwise
	 */
	boolean containsSpike(int number);

	/**
	 * Remove spike. All words in spike will be removed from the spike
	 *
	 * @param number the number of spike
	 * @return the lowest number of {@code DenotationPoemModel.DenotationWord} which was in removed spike
	 */
	int removeSpike(int number);

	Spike getSpike(int number);


	int getCountOfWords();

	DenotationWord getWord(int number);

	Collection<Spike> getSpikes();

	void addNewElementTo(int denotationWordNumber);

	void duplicateElement(int number, DenotationElement elementToDuplicate);

	void removeElement(int number, DenotationElement element);

	void joinWords(int word, int wordToJoin);

	void split(int wordNumber, int wordToSplitNumber);

	void ignoreWord(int number, boolean ignored);
}
