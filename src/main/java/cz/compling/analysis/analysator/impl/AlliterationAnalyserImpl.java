package cz.compling.analysis.analysator.impl;

import cz.compling.analysis.analysator.AlliterationAnalyser;
import cz.compling.analysis.analysator.poems.alliteration.AlliterationImpl;
import cz.compling.analysis.analysator.poems.alliteration.IAlliteration;
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
public class AlliterationAnalyserImpl implements AlliterationAnalyser {

	private final Poem poem;

	public AlliterationAnalyserImpl(Poem poem) {
		this.poem = poem;
	}

	@Override
	public IAlliteration getAlliteration() {
		return new AlliterationImpl(poem);
	}
}
