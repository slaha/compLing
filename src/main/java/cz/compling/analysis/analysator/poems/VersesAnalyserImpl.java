package cz.compling.analysis.analysator.poems;

import cz.compling.analysis.analysator.poems.verses.IVerses;
import cz.compling.analysis.analysator.poems.verses.VersesImpl;
import cz.compling.text.poem.Poem;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 19.3.14 10:50</dd>
 * </dl>
 */
public class VersesAnalyserImpl implements VerseAnalyser {
	private final Poem poem;

	public VersesAnalyserImpl(Poem poem) {
		this.poem = poem;
	}

	@Override
	public IVerses getVerses() {
		return new VersesImpl(poem);
	}
}
