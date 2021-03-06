package cz.compling.analysis.analysator.poems;

import cz.compling.analysis.analysator.poems.aggregation.AggregationImpl;
import cz.compling.analysis.analysator.poems.aggregation.IAggregation;
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
public class AggregationAnalyserImpl implements AggregationAnalyser {

	private final Poem poem;

	public AggregationAnalyserImpl(Poem poem) {
		this.poem = poem;
	}

	@Override
	public IAggregation getAggregation() {
		return new AggregationImpl(poem);
	}
}
