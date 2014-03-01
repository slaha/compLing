package cz.compling.analysis.analysator.frequency;

import cz.compling.utils.TrooveUtils;
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
 * <dd> 1.3.14 11:17</dd>
 * </dl>
 */
public interface CharacterFrequency {
	int getFrequencyFor(char character);

	int getFrequencyFor(String string);

	/**
	 * Returns sorted list of frequencies of characters
	 *
	 * @param order if true list will be sorted descending; if false list will be sorted ascending
	 *
	 * @return list with sorted frequencies, where first value in pair is character or string and second is its frequency
	 */
	List<Pair<String, Integer>> getAllByFrequency(TrooveUtils.SortOrder order);
}
