package cz.compling.analysis.analysator.poems.denotation;

import cz.compling.model.denotation.*;

import java.util.Collection;
import java.util.List;

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
	 * Creates new empty hreb
	 *
	 * @return number of hreb (must be positive)
	 */
	int createNewHreb();

	int addHreb(Hreb hreb);

	/**
	 * Check if hreb with number {@code number} exists
	 * @param number the number of hreb
	 * @return true if hreb exists; false otherwise
	 */
	boolean containsHreb(int number);

	/**
	 * Remove hreb. All words in hreb will be removed from the hreb
	 *
	 * @param number the number of hreb
	 * @return the lowest number of {@code DenotationPoemModel.DenotationWord} which was in removed hreb
	 */
	int removeHreb(int number);

	Hreb getHreb(int number);


	int getCountOfWords();

	DenotationWord getWord(int number);

	Collection<Hreb> getHrebs();

	void addNewElementTo(int denotationWordNumber);

	void addNewElementTo(int denotationWordNumber, int elementNumber);

	DenotationElement duplicateElement(int number, DenotationElement elementToDuplicate);

	void removeElement(int number, DenotationElement element);

	void joinWords(int word, int wordToJoin);

	void split(int wordNumber, int wordToSplitNumber);

	void ignoreWord(int number, boolean ignored);

	void clearAllWords();

	void addNewWord(int number, DenotationWord word);

	double computeTopicality(Hreb hreb, double cardinalNumber);

	double getTextCompactness();

	double getTextCentralization();

	double getMacIntosh();

	double getDiffusionFor(int hreb);

	List<DenotationWord> getAllWords();

	List<Coincidence> getCoincidenceFor(int hrebNumber);

	List<Coincidence> getDeterministicFor(int hrebNumber);

	PoemAsHrebNumbers getPoemAsHrebNumbers();

	double getNonContinuousIndex();

	double getNonIsolationIndex();

	double getReachabilityIndex();
}