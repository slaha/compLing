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

	int getFrequencyFor(String string);

	List<Pair<String, Integer>> getAllByFrequency(TrooveUtils.SortOrder order);

}
