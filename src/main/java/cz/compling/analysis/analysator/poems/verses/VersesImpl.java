package cz.compling.analysis.analysator.poems.verses;

import cz.compling.model.Verses;
import cz.compling.text.poem.Poem;
import cz.compling.text.poem.Verse;

import java.util.List;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 19.3.14 10:51</dd>
 * </dl>
 */
public class VersesImpl implements IVerses{

	private final Poem poem;
	private final Verses verses;

	public VersesImpl(Poem poem) {
		this.poem = poem;
		this.verses = new Verses();

		compute();
	}

	private void compute() {
		List<Verse> poemVerses = poem.getVerses();

		for (int verseNumber = 0; verseNumber < poemVerses.size(); verseNumber++) {
			Verse verse = poemVerses.get(verseNumber);
			int wordsCount = verse.getWords().size();
			int charsCount = verse.getCharacters().length;

			verses.put(verseNumber + 1, charsCount, wordsCount);
		}

	}


	@Override
	public Verses getVerses() {
		return verses;
	}
}
