package cz.compling.model;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 19.3.14 10:48</dd>
 * </dl>
 */
public class Verses {

	private final TIntObjectMap<VerseLengths> versesAnalysis;
	private int numberOfVerses;

	public Verses() {
		versesAnalysis = new TIntObjectHashMap<VerseLengths>();
		numberOfVerses = 0;
	}

	public void put(int verseNumber, int charsCount, int wordsCount) {
		if (verseNumber < 1 || charsCount < 0 || wordsCount < 0) {
			String m = String.format("Illegal arguments in method put. " +
				"verseNumber must be > 0 (was %d), charsCount must be >= 0 (was %d) and wordsCount must be >= 0 (was %d)",
				verseNumber, charsCount, wordsCount);
			throw new IllegalArgumentException(m);
		}

		versesAnalysis.put(verseNumber, new VerseLengths(charsCount, wordsCount));
		numberOfVerses = Math.max(verseNumber, numberOfVerses);
	}

	public int getNumberOfVerses() {
		return numberOfVerses;
	}

	public VerseLengths getVerseLengthsFor(int verseNumber) {
		if (verseNumber < 1 || verseNumber > getNumberOfVerses()) {
			String m = String.format("Param 'verseNumber' must be bigger than 0 and lower or equal to %d. Was %d", getNumberOfVerses(), verseNumber);
			throw new IllegalArgumentException(m);
		}
		return versesAnalysis.get(verseNumber);
	}

	public class VerseLengths {
		public final int charsCount;
		public final int wordsCount;

		public VerseLengths(int charsCount, int wordsCount) {
			this.charsCount = charsCount;
			this.wordsCount = wordsCount;
		}
	}
}
