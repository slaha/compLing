package cz.compling.analysis.analysator.poems.aggregation;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 16.3.14 17:54</dd>
 * </dl>
 */
public interface AggregationRule {

	boolean matchesRule(String str);

	String compareTo(String str);

}
