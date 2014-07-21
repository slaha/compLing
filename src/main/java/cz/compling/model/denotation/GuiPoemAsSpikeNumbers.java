package cz.compling.model.denotation;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 17.5.14 14:27</dd>
 * </dl>
 */
public class GuiPoemAsSpikeNumbers {

	private static final Comparator<DenotationWord> WORD_COMPARATOR = new Comparator<DenotationWord>() {
		@Override
		public int compare(DenotationWord o1, DenotationWord o2) {
			int diff;
			diff = o1.getStropheNumber() - o2.getStropheNumber();
			if (diff != 0) {
				return diff;
			}
			diff = o1.getVerseNumber() - o2.getVerseNumber();
			if (diff != 0) {
				return diff;
			}
			return o1.getNumber() - o2.getNumber();
		}
	};

	private static final Comparator<DenotationElement> ELEMENT_COMPARATOR = new Comparator<DenotationElement>() {

		@Override
		public int compare(DenotationElement o1, DenotationElement o2) {
			return o1.getNumber() - o2.getNumber();
		}
	};

	private final TIntObjectMap<Strophe> strophes;
	private final int versesCount;

	public GuiPoemAsSpikeNumbers(List<DenotationWord> allWords) {

		this.strophes = new TIntObjectHashMap<Strophe>();

		allWords = new ArrayList<DenotationWord>(allWords);
		Collections.sort(allWords, WORD_COMPARATOR);

		for (DenotationWord word : allWords) {
			final List<DenotationElement> elements = new ArrayList<DenotationElement>(word.getDenotationElements());
			Collections.sort(elements, ELEMENT_COMPARATOR);
			for (DenotationElement element : elements) {
				addElement(element, word);
			}
		}

		int verses = 0;
		for (Strophe strophe : strophes.valueCollection()) {
			verses += strophe.verses.size();
		}
		this.versesCount = verses;
	}

	public List<Strophe> getStrophes() {
		final ArrayList<Strophe> list = new ArrayList<Strophe>(strophes.valueCollection());
		Collections.sort(list);
		return Collections.unmodifiableList(list);
	}

	private void addElement(DenotationElement element, DenotationWord word) {
		Strophe strophe = strophes.get(word.getStropheNumber());
		if (strophe == null) {
			strophe = new Strophe(word.getStropheNumber());
			strophes.put(word.getStropheNumber(), strophe);
		}
		strophe.addElement(element, word);
	}

	int getVersesCount() {
		return versesCount;
	}

	int getVersesCountWith(int spikeNumber) {
		int verses = 0;

		for (Strophe strophe : strophes.valueCollection()) {
			for (Strophe.Verse verse : strophe.verses.valueCollection()) {
				for (Spike spike : verse.spikes) {
					if (spike.getNumber() == spikeNumber) {
						verses++;
						break;
					}
				}
			}
		}

		return verses;
	}

	public int getVersesCountWithBoth(int aNumber, int bNumber) {
		int verses = 0;

		for (Strophe strophe : strophes.valueCollection()) {
			for (Strophe.Verse verse : strophe.verses.valueCollection()) {
				boolean hasA = false;
				boolean hasB = false;
				for (Spike spike : verse.spikes) {
					if (spike.getNumber() == aNumber) {
						hasA = true;
					} else if (spike.getNumber() == bNumber) {
						hasB = true;
					}
					if (hasA && hasB) {
						verses++;
						break;
					}
				}
			}
		}

		return verses;
	}

	public static class Strophe implements Comparable<Strophe> {

		private final int stropheNumber;
		private final TIntObjectHashMap<Verse> verses;

		public Strophe(int stropheNumber) {
			this.stropheNumber = stropheNumber;
			this.verses = new TIntObjectHashMap<Verse>();
		}

		public void addElement(DenotationElement element, DenotationWord word) {
			Verse verse = verses.get(word.getVerseNumber());
			if (verse == null) {
				verse = new Verse(word.getVerseNumber());
				verses.put(word.getVerseNumber(), verse);
			}
			verse.addElement(element);
		}

		@Override
		public int compareTo(Strophe o) {
			return this.stropheNumber - o.stropheNumber;
		}

		public List<Verse> getVerses() {
			final ArrayList<Verse> list = new ArrayList<Verse>(verses.valueCollection());
			Collections.sort(list);
			return Collections.unmodifiableList(list);
		}

		public int getNumber() {
			return stropheNumber;
		}

		public static class Verse implements Comparable<Verse> {

			private final int verseNumber;
			private final List<Spike> spikes;

			public Verse(int verseNumber) {
				this.verseNumber = verseNumber;
				this.spikes = new ArrayList<Spike>();
			}

			public void addElement(DenotationElement element) {
				spikes.add(element.getSpike());
			}

			@Override
			public int compareTo(Verse o) {
				return this.verseNumber - o.verseNumber;
			}

			public List<Integer> getSpikes() {
				List<Integer> el = new ArrayList<Integer>();
				for (Spike spike : spikes) {
					el.add(spike.getNumber());
				}
				return Collections.unmodifiableList(el);
			}
		}
	}
}

