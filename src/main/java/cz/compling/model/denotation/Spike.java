package cz.compling.model.denotation;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 1.5.14 17:49</dd>
 * </dl>
 */
public class Spike {

	/** number of Spike */
	private final int number;

	/** Words in spike */
	private final DenotationWordsMap words;

	public Spike(int number) {
		this.number = number;
		words = new DenotationWordsMap();
	}

	public int getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return String.valueOf(number);
	}

	public void remove(DenotationWord word) {
		final DenotationWord remove = words.remove(word);
		remove.onRemoveFromSpike(this);
	}

	public HashMap<DenotationWord, DenotationWord> getWords() {
		return words;
	}

	public int onRemove() {
		int lowestWordNumber = Integer.MAX_VALUE;
		for (DenotationWord word : words.keySet()) {
			if (word.getNumber() < lowestWordNumber) {
				lowestWordNumber = word.getNumber();
			}
		}
		words.clear();
		return lowestWordNumber;
	}

	public void addWord(DenotationWord word, String input) {
		this.words.put(word, word);
		word.onAddToSpike(this, input);
	}

	/**
	 * This map is set of DenotationWords in the spike
	 */
	private static class DenotationWordsMap extends HashMap<DenotationWord, DenotationWord> {
		@Override
		public String toString() {
			if (isEmpty()) {
				return "–";
			}
			final Iterator<DenotationWord> iterator = keySet().iterator();
			StringBuilder sb = new StringBuilder();
			appendWord(sb, iterator.next());
			while (iterator.hasNext()) {
				sb.append(", ");
				appendWord(sb, iterator.next());
			}
			return sb.toString();
		}

		void appendWord(StringBuilder sb, DenotationWord word) {
			sb.append(sb.length() > 0 ? ", " : "").append(word);
		}

	}
}
