package cz.compling.model.denotation;

import java.util.*;

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
	private int number;

	/** Words in spike */
	private final DenotationWordsMap words;

	public Spike(int number) {
		this.number = number;
		words = new DenotationWordsMap();
	}

	void setNumber(int number) {
		this.number = number;
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

	public Collection<DenotationWord> getWords() {
		List<DenotationWord> denotationWords = new ArrayList<DenotationWord>(words.values());
		Collections.sort(denotationWords, new Comparator<DenotationWord>() {
			@Override
			public int compare(DenotationWord o1, DenotationWord o2) {
				return o1.getNumber() - o2.getNumber();
			}
		});

		return denotationWords;
	}

	public int size() {
		return words.size();
	}

	public int onRemove() {
		int lowestWordNumber = Integer.MAX_VALUE;
		for (DenotationWord word : words.keySet()) {
			if (word.getNumber() < lowestWordNumber) {
				lowestWordNumber = word.getNumber();
			}
			if (word.isInSpike(this)) {
				word.onRemoveFromSpike(this);
			}
		}
		words.clear();
		return lowestWordNumber;
	}

	public void addWord(DenotationWord word, String input) {
		this.words.put(word, word);
		word.onAddToSpike(this, input);
	}

	public void addWord(DenotationWord word, String input, int elementNumber) {
		this.words.put(word, word);
		word.onAddToSpike(this, input ,elementNumber);
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
