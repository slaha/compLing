package cz.compling.model;

import gnu.trove.map.TCharIntMap;
import gnu.trove.map.hash.TCharIntHashMap;

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

	public void add(char firstChar) {
		alliteration.adjustOrPutValue(firstChar, 1, 1);
		wordsInVerse++;
	}

	public char[] getFirstCharactersOfWords() {
		return alliteration.keys();
	}

	public int getAlliterationFor(char c) {
		return alliteration.get(c);
	}

	public int getNumberOfVerse() {
		return numberOfVerse;
	}

	public int getCountOfWordsInVerse() {
		return wordsInVerse;
	}
	@Override
	public String toString() {
		return String.format("Verso no. %3d. Words in verse: %2d, alliteration: %s", numberOfVerse, wordsInVerse, alliteration.toString());
	}
}
