package cz.compling.analysis;

import cz.compling.rules.CharacterModificationRule;
import cz.compling.text.Text;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.javatuples.Pair;
import utils.Reference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 *
 * This class computes and holds frequency of characters in text. It respects registered rules
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 19:51</dd>
 * </dl>
 */
public class CharacterFrequency {

	/** Text to analyse */
	private final Text text;

	/** Frequency of characters */
	private final TObjectIntHashMap<String> frequency;

	public CharacterFrequency(Text text) {
		if (text == null) {
			throw new IllegalArgumentException("Parameter text cannot be null");
		}
		this.text = text;
		this.frequency = new TObjectIntHashMap<String>();
		
		compute();
	}

	private void compute() {
		final String plainText = text.getPlainText();
		final Reference<Integer> position = new Reference<Integer>();
		final Reference<String> putToMap = new Reference<String>();

		for (int index = 0; index < plainText.length(); index++) {
			final char charAtIndex = plainText.charAt(index);

			position.value = index;
			putToMap.value = String.valueOf(charAtIndex);
			for (CharacterModificationRule rule : text.getCharacterModificationRules()) {
				//..if rule matches, putToMap and position's values are set properly
				if (rule.modify(plainText, putToMap, position)) {
					//..rule matches. Save returned position
					index = position.value;
					break;
				}
			}
			if (putToMap.value == null) {
				//..skip this loop
				continue;
			}

			frequency.adjustOrPutValue(putToMap.value, 1, 1);
		}

	}

	public int getFrequencyFor(char character) {
		return getFrequencyFor(String.valueOf(character));
	}

	public int getFrequencyFor(String string) {
		return frequency.get(string);
	}

	/**
	 * Returns sorted list of frequencies of characters
	 *
	 * @param descending if true list will be sorted descending; if false list will be sorted ascending
	 *
	 * @return list with sorted frequencies, where first value in pair is character or string and second is its frequency
	 */
	public List<Pair<String, Integer>> getAllByFrequency(boolean descending) {
		List<Pair<String, Integer>> list = toList();

		Comparator<? super Pair<String, Integer>> comparator = createComparator(descending);

		Collections.sort(list, comparator);

		return list;
	}


	/**
	 * Converts frequency table to list
	 */
	private List<Pair<String, Integer>> toList() {
		List<Pair<String, Integer>> list = new ArrayList<Pair<String, Integer>>(frequency.size());

		for (String key : frequency.keySet()) {
			list.add(
				new Pair<String, Integer>(key, frequency.get(key))
			);
		}

		return list;
	}

	/**
	 * Creates comparator for sorting list from {@code toList()} method.
	 * If frequency of two characters is the same it alphabet order is used (
	 *
	 * @param descending if true comparator will sort from big to lower; if false comparator will sort from low values to big
	 *
	 * @return comparator for sorting in desired order
	 */
	private Comparator<? super Pair<String, Integer>> createComparator(final boolean descending) {
		Comparator<? super Pair<String, Integer>> comparator;

		comparator = new Comparator<Pair<String, Integer>>() {
			@Override
			public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
				int diff =  o2.getValue1() - o1.getValue1();

				if (diff == 0) {
					//..frequency is the same. Compare characters
					diff = o1.getValue0().compareTo(o2.getValue0());
				}

				return descending ? diff : -diff;
			}
		};

		return comparator;
	}
}