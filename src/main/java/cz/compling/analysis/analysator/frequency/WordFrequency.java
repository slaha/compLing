package cz.compling.analysis.analysator.frequency;

import cz.compling.utils.TrooveUtils;
import org.javatuples.Pair;

import java.util.List;

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
public interface WordFrequency {
	/**
	 * Returns count of words in text.
	 *
	 * @return count of words in text.
	 */
	int getCountOfWords();

	/**
	 * Returns frequency for {@code word}
	 *
	 * @param word word to look for its frequency
	 *
	 * @return frequency of occasions of the word. Zero if it is not in the text
	 */
	int getFrequencyFor(String word);

	/**
	 * Returns frequency for all words of length {@code length}
	 *
	 * @param length length of words to look for their frequency. Must be > 0
	 *
	 * @return frequency of occasions of words of the length. Zero if it is not in the text
	 */
	int getFrequencyFor(int length);

	/**
	 * Returns list of all words with count of their occasions in the text
	 *
	 * @param order how the list will be sorted
	 *
	 * @return sorted list with pairs of occasions for each word.
	 */
	List<Pair<String, Integer>> getAllWordsByFrequency(TrooveUtils.SortOrder order);

	/**
	 * Returns list of all lengths od words with count of their occasions in the text
	 *
	 * @param order how the list will be sorted
	 *
	 * @return sorted list with pairs where first value is length and second value is number of occasions.
	 */
	List<Pair<Integer, Integer>> getAllWordsLengthsByFrequency(TrooveUtils.SortOrder order);
}
