package cz.compling.model.denotation;

import cz.compling.model.Words;
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
	private final Poem poem;
	private final TIntObjectMap<DenotationWord> words;
	private final int countOfWords;

	DenotationPoem(Poem poem, Words words) {
		this.poem = poem;
		this.words = new TIntObjectHashMap<DenotationWord>(words.getCountOfWords());

		int currentWordNumber = 0;
		int stropheNumber = 0;
		for (Strophe strophe : poem.getStrophes()) {
			stropheNumber++;
			int verseNumber = 0;
			for (Verse verse : strophe.getVerses()) {
				verseNumber++;
				for (String word: verse.getWords()) {
					currentWordNumber++;
					this.words.put(
						currentWordNumber,
						new DenotationWord(word, currentWordNumber, verseNumber, stropheNumber)
					);
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
}
