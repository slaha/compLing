package cz.compling.analysis;

import cz.compling.rules.CharacterModificationRule;
import cz.compling.text.Text;
import gnu.trove.map.hash.TObjectIntHashMap;
import utils.Reference;


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
			putToMap.value = null;
			for (CharacterModificationRule rule : text.getCharacterModificationRules()) {
				//..if rule matches, putToMap and position's values are set
				if (rule.modify(plainText, putToMap, position)) {
					index = position.value;
					break;
				}
			}
			if (putToMap.value == null) {
				//..no rule or no rule matched. Put the character to the map
				putToMap.value = String.valueOf(charAtIndex);
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
}