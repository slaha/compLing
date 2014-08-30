package cz.compling.model;

import cz.compling.utils.TrooveUtils;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.apache.commons.lang3.ArrayUtils;
import org.javatuples.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 16.3.14 11:02</dd>
 * </dl>
 */
public class WordFrequency {

	private final TObjectIntMap<String> frequency;
	private final TIntIntHashMap wordsByLength;
	private int countOfWords;

	private final Set<Integer> differentWordLengths;

	public WordFrequency() {
		this.frequency = new TObjectIntHashMap<String>();
		this.wordsByLength = new TIntIntHashMap();
		differentWordLengths = new HashSet<Integer>();
		this.countOfWords = 0;
	}

	public void put(String word, int length) {
		frequency.adjustOrPutValue(word, 1, 1);
		wordsByLength.adjustOrPutValue(length, 1, 1);
		differentWordLengths.add(length);
		countOfWords++;
	}

	/**
	 * Returns count of words in text.
	 *
	 * @return count of words in text.
	 */
	public int getCountOfWords()  {

		return countOfWords;
	}


	/**
	 * Returns count of different word lengths in text.
	 *
	 * @return count of different word lengths in text.
	 */
	public int getCountOfWordLengths()  {

		return wordsByLength.size();
	}

	/**
	 * Returns frequency for {@code word}
	 *
	 * @param word word to look for its frequency
	 *
	 * @return frequency of occasions of the word. Zero if it is not in the text
	 */
	public int getFrequencyFor(String word) {
		if (word == null || word.trim().isEmpty()) {
			throw new IllegalArgumentException("Param word cannot be NULL or empty or only whitespaces");
		}
		return frequency.get(word);
	}

	/**
	 * Returns frequency for all words of length {@code length}
	 *
	 * @param length length of words to look for their frequency. Must be > 0
	 *
	 * @return frequency of occasions of words of the length. Zero if it is not in the text
	 */
	public int getFrequencyFor(int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("Param length must be positive, was " + length);
		}

		return wordsByLength.get(length);
	}

	public double getRelativeFrequencyFor(int length) {
		final int frequency = getFrequencyFor(length);
		final int countOfWords = getCountOfWords();

		return ( ((double)frequency) / ((double)countOfWords) );
	}

	/**
	 * Returns list of all words with count of their occasions in the text
	 *
	 * @param order how the list will be sorted
	 *
	 * @return sorted list with pairs of occasions for each word.
	 */
	public List<Pair<String, Integer>> getAllWordsByFrequency(TrooveUtils.SortOrder order) {
		return new TrooveUtils.Lists<String, Pair<String, Integer>>()
			.toList(frequency)
			.sort(order)
			.getList();
	}

	/**
	 * Returns list of all lengths od words with count of their occasions in the text
	 *
	 * @param order how the list will be sorted
	 *
	 * @return sorted list with pairs where first value is length and second value is number of occasions.
	 */
	public List<Pair<Integer, Integer>> getAllWordsLengthsByFrequency(TrooveUtils.SortOrder order) {
		return new TrooveUtils.Lists<Integer, Pair<Integer, Integer>>()
			.toList(wordsByLength)
			.sort(order)
			.getList();
	}

	public int[] getWordLengths() {
		return ArrayUtils.toPrimitive(differentWordLengths.toArray(new Integer[differentWordLengths.size()]));
	}
}
