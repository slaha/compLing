package cz.compling.analysis.analysator.frequency.impl;

import cz.compling.analysis.analysator.frequency.CharacterFrequency;
import cz.compling.rules.CharacterModificationRule;
import cz.compling.text.Text;
import cz.compling.utils.Reference;
import cz.compling.utils.TrooveUtils;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.javatuples.Pair;

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
public class CharacterFrequencyImpl implements CharacterFrequency {

	/** Text to analyse */
	private final Text text;

	/** Frequency of characters */
	private final TObjectIntHashMap<String> frequency;

	public CharacterFrequencyImpl(Text text) {
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

	@Override
	public int getFrequencyFor(char character) {
		return getFrequencyFor(String.valueOf(character));
	}

	@Override
	public int getFrequencyFor(String freqFor) {
		if (freqFor == null || freqFor.trim().isEmpty()) {
			throw new IllegalArgumentException("getFrequencyFor cannot be called with NULL or empty or whitespaces only argument");
		}
		return frequency.get(freqFor);
	}

	@Override
	public List<Pair<String, Integer>> getAllByFrequency(TrooveUtils.SortOrder order) {
		return new TrooveUtils.Lists<String, Pair<String, Integer>>()
			.toList(frequency)
			.sort(order)
			.getList();
	}

}