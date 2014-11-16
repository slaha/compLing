package cz.compling.analysis.analysator.poems.aggregation;

import cz.compling.model.Aggregations;
import cz.compling.rules.RuleHandler;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 2.3.14 17:59</dd>
 * </dl>
 */
public interface IAggregation extends RuleHandler<AggregationRule>{

	Aggregations getAggregations();
}
