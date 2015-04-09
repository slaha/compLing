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
	 * Creates new empty spike
	 *
	 * @return number of spike (must be positive)
	 */
	int createNewSpike();

	int addSpike(Spike spike);

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

	void addNewElementTo(int denotationWordNumber, int elementNumber);

	DenotationElement duplicateElement(int number, DenotationElement elementToDuplicate);

	void removeElement(int number, DenotationElement element);

	void joinWords(int word, int wordToJoin);

	void split(int wordNumber, int wordToSplitNumber);

	void ignoreWord(int number, boolean ignored);

	void clearAllWords();

	void addNewWord(int number, DenotationWord word);

	double computeTopicality(Spike spike, double cardinalNumber);

	double getTextCompactness();

	double getTextCentralization();

	double getMacIntosh();

	double getDiffusionFor(int spike);

	List<DenotationWord> getAllWords();

	List<Coincidence> getCoincidenceFor(int spikeNumber);

	List<Coincidence> getDeterministicFor(int spikeNumber);

	GuiPoemAsSpikeNumbers getPoemAsSpikeNumbers();

	double getNonContinuousIndex();

	double getNonIsolationIndex();

	double getReachabilityIndex();
}