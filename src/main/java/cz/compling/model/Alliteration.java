package cz.compling.model;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.procedure.TObjectIntProcedure;

import java.util.Collection;
import java.util.Collections;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 8.3.14 7:24</dd>
 * </dl>
 */
public class Alliteration {

	private final TIntObjectMap<LineAlliteration> alliteration;
	private int numberOfVerses;

	public Alliteration() {
		alliteration = new TIntObjectHashMap<LineAlliteration>();
		numberOfVerses = 0;
	}

	public void clean() {
		numberOfVerses = 0;
		alliteration.clear();
	}

	public int getVerseCount() {
		return numberOfVerses;
	}

	public LineAlliteration getAlliterationFor(int numberOfVerse) {
		if (numberOfVerse <= 0 || numberOfVerse > getVerseCount()) {
			String msg =
				String.format("Param numberOfVerse must be bigger than 0 and lower than return value of getVerseCount() (=%d). Was %d.", getVerseCount(), numberOfVerse);
			throw new IllegalArgumentException(msg);
		}
		return alliteration.get(numberOfVerse);
	}

	public void put(int numberOfVerse, LineAlliteration lineAlliteration) {
		alliteration.put(numberOfVerse, lineAlliteration.removeNotAlliterationChars());
		numberOfVerses++;
	}

	public static class LineAlliteration {
		private final int numberOfVerse;

		private int wordsInVerse;

		private final TObjectIntMap<String> alliteration;

		public LineAlliteration(int numberOfVerse) {
			this.numberOfVerse = numberOfVerse;
			this.wordsInVerse = 0;

			alliteration = new TObjectIntHashMap<String>();
		}

		public LineAlliteration add(String str) {
			alliteration.adjustOrPutValue(str, 1, 1);
			wordsInVerse++;
			return this;
		}

		public Collection<String> getFirstCharactersOfWords() {
			return Collections.unmodifiableCollection(alliteration.keySet());
		}

		public boolean hasAnyAlliteration() {
			return getFirstCharactersOfWords().size() > 0;
		}

		public int getAlliterationFor(char c) {
			return getAlliterationFor(String.valueOf(c));
		}

		public int getAlliterationFor(String str) {
			return alliteration.get(str);
		}


		public int getNumberOfVerse() {
			return numberOfVerse;
		}

		private LineAlliteration removeNotAlliterationChars() {
			alliteration.forEachEntry(new TObjectIntProcedure<String>() {
				@Override
				public boolean execute(String c, int i) {
					if (i < 2) {
						alliteration.remove(c);
					}
					return true;
				}
			});

			return this;
		}

		public int getCountOfWordsInVerse() {
			return wordsInVerse;
		}

		@Override
		public String toString() {
			return String.format("Verso no. %3d. Words in verse: %2d, alliteration: %s", numberOfVerse, wordsInVerse, alliteration.toString());
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			LineAlliteration that = (LineAlliteration) o;

			if (numberOfVerse != that.numberOfVerse) return false;
			if (wordsInVerse != that.wordsInVerse) return false;
			if (!alliteration.equals(that.alliteration)) return false;

			return true;
		}

		@Override
		public int hashCode() {
			int result = numberOfVerse;
			result = 31 * result + wordsInVerse;
			result = 31 * result + alliteration.hashCode();
			return result;
		}
	}
}
