package cz.compling.model;

import cz.compling.utils.TrooveUtils;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
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

	public CharacterFrequency() {
		this.frequency = new TObjectIntHashMap<String>();
	}

	public void flush() {
		frequency.clear();
	}

	public void put(String value) {
		frequency.adjustOrPutValue(value, 1, 1);
	}

	public int getFrequencyFor(char character) {
		return getFrequencyFor(String.valueOf(character));
	}

	public int getFrequencyFor(String freqFor) {
		if (freqFor == null || freqFor.trim().isEmpty()) {
			throw new IllegalArgumentException("getFrequencyFor cannot be called with NULL or empty or whitespaces only argument");
		}
		return frequency.get(freqFor);
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
