package cz.compling.analysis.analysator.poems;

import cz.compling.analysis.analysator.poems.denotation.IDenotation;
import cz.compling.model.denotation.Denotation;
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

	public DenotationAnalyserImpl(Poem poem) {
		this.poem = poem;
	}

	@Override
	public IDenotation getDenotation() {
		return new Denotation(poem);
	}
}
