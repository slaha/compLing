package cz.compling.analysis.analysator.poems;

import cz.compling.analysis.analysator.poems.denotation.DenotationImpl;
import cz.compling.analysis.analysator.poems.denotation.IDenotation;
import cz.compling.model.Words;
import cz.compling.text.poem.Poem;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 1.5.14 17:35</dd>
 * </dl>
 */
public class DenotationAnalyserImpl implements DenotationAnalyser {
	private final Poem poem;
	private final Words words;

	public DenotationAnalyserImpl(Poem poem, Words words) {
		this.poem = poem;

		this.words = words;
	}

	@Override
	public IDenotation getDenotation() {
		return new DenotationImpl(poem, words);
	}
}
