package cz.compling.analysis.analysator.poems;

import cz.compling.analysis.analysator.poems.assonance.AssonanceImpl;
import cz.compling.analysis.analysator.poems.assonance.IAssonance;
import cz.compling.text.poem.Poem;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 3.3.14 8:10</dd>
 * </dl>
 */
public class AssonanceAnalyserImpl implements AssonanceAnalyser {

	private final Poem poem;

	public AssonanceAnalyserImpl(Poem poem) {
		this.poem = poem;
	}

	@Override
	public IAssonance getAssonance(String[] vocals) {
		return new AssonanceImpl(poem, vocals);
	}
}
