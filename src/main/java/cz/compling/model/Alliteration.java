package cz.compling.model;

import gnu.trove.map.TCharIntMap;
import gnu.trove.map.hash.TCharIntHashMap;
import gnu.trove.procedure.TCharIntProcedure;

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


	private final int numberOfVerse;

	private int wordsInVerse;

	private final TCharIntMap alliteration;

	public Alliteration(int numberOfVerse) {
		this.numberOfVerse = numberOfVerse;
		this.wordsInVerse = 0;

		alliteration = new TCharIntHashMap();
	}

	public Alliteration add(char firstChar) {
		alliteration.adjustOrPutValue(firstChar, 1, 1);
		wordsInVerse++;
		return this;
	}

	public char[] getFirstCharactersOfWords() {
		return alliteration.keys();
	}

	public boolean hasAnyAlliteration() {
		return getFirstCharactersOfWords().length > 0;
	}
	public int getAlliterationFor(char c) {
		return alliteration.get(c);
	}

	public int getNumberOfVerse() {
		return numberOfVerse;
	}

	public Alliteration removeNotAlliterationChars() {
		alliteration.forEachEntry(new TCharIntProcedure() {
			@Override
			public boolean execute(char c, int i) {
				if (i < 2) {
					alliteration.remove(c);
				}
				return true;
			};
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

		Alliteration that = (Alliteration) o;

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
