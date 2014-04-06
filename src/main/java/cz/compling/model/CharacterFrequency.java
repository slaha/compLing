package cz.compling.model;

import cz.compling.utils.TrooveUtils;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import java.util.List;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 16.3.14 12:23</dd>
 * </dl>
 */
public class CharacterFrequency {

	/** Frequency of characters */
	private final TObjectIntMap<String> frequency;

	private final int plainTextLength;
	private int charactersCount;


	public CharacterFrequency(int plainTextLength) {
		this.plainTextLength = plainTextLength;
		this.charactersCount = 0;
		this.frequency = new TObjectIntHashMap<String>();
	}

	public void flush() {
		frequency.clear();
	}

	public void put(String value) {
		value = applyWhiteSpaceRule(value);

		frequency.adjustOrPutValue(value, 1, 1);
		charactersCount++;
	}

	private String applyWhiteSpaceRule(String value) {
		if ("\n".equals(value)) {
			value = "\\n";
		} else if ("\t".equals(value)) {
			value = "\\t";
		} else if (StringUtils.isWhitespace(value)) {
			value = "'" + value + "'";
		}
		return value;
	}


	/**
	 * Returns length of the text in characters. Counts every character
	 *
	 * @return length of the text in characters
	 */
	public int getPlainTextLength() {
		return this.plainTextLength;
	}

	/**
	 * Returns count of characters. Counts only characters that can be retrieved by calling {@code getFrequencyFor}
	 *
	 * @return
	 */
	public int getCharactersCount() {
		return this.charactersCount;
	}

	public int getFrequencyFor(char character) {
		return getFrequencyFor(String.valueOf(character));
	}

	public int getFrequencyFor(String freqFor) {
		if (freqFor == null || freqFor.isEmpty()) {
			throw new IllegalArgumentException("getFrequencyFor cannot be called with NULL or empty only argument");
		}
		return frequency.get(applyWhiteSpaceRule(freqFor));
	}
	/**
	 * Returns sorted list of frequencies of characters
	 *
	 * @param order if true list will be sorted descending; if false list will be sorted ascending
	 *
	 * @return list with sorted frequencies, where first value in pair is character or string and second is its frequency
	 */
	public List<Pair<String, Integer>> getAllByFrequency(TrooveUtils.SortOrder order) {
		return new TrooveUtils.Lists<String, Pair<String, Integer>>()
			.toList(frequency)
			.sort(order)
			.getList();
	}
}
