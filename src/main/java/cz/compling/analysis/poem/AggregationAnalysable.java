package cz.compling.analysis.poem;

import cz.compling.analysis.analysator.poems.aggregation.IAggregation;

/**
 *
 * This interface contains methods which should be performed in character analysis
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 19:18</dd>
 * </dl>
 */
public interface AggregationAnalysable {

	/**
	 * Returns aggregation of text
	 *
	 * @return aggregation in text
	 */
	IAggregation getAggregation();

}
