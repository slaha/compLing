package cz.compling.model.denotation;

import cz.compling.text.poem.Poem;
import cz.compling.text.poem.Strophe;
import cz.compling.text.poem.Verse;
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
 * <dd> 1.5.14 18:01</dd>
 * </dl>
 */
class DenotationPoem {
	private final TIntObjectMap<DenotationWord> words;
	private final int countOfWords;

	DenotationPoem(Poem poem) {
		this.words = new TIntObjectHashMap<DenotationWord>();

		int currentWordNumber = 0;
		int stropheNumber = 0;
		for (Strophe strophe : poem.getStrophes()) {
			stropheNumber++;
			int verseNumber = 0;
			for (Verse verse : strophe.getVerses()) {
				verseNumber++;
				for (String word: verse.getWords(false)) {
					currentWordNumber++;
					putWord(currentWordNumber, new DenotationWord(word, currentWordNumber, verseNumber, stropheNumber));
				}
			}
		}

		this.countOfWords = currentWordNumber;
	}

	public DenotationWord getWord(int number) {
		DenotationWord word = words.get(number);
		if (word == null) {
			throw new DenotationWordNotFoundException("cannot found word with number " + number);
		}
		return word;
	}

	public int getCountOfWords() {
		return countOfWords;

	}

	TIntObjectMap<DenotationWord> getAllWords() {
		return words;
	}

	public void clearAllWords() {
		words.clear();
	}

	public void putWord(int number, DenotationWord word) {
		this.words.put(number, word);
	}
}
