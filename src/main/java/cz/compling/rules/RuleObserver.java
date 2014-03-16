package cz.compling.rules;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 16.3.14 12:53</dd>
 * </dl>
 */
public interface RuleObserver<T> {

	void onRuleAdded(T addedRule, Iterable<T> rules);

	void onRuleRemoved(T removedRule, Iterable<T> rules);
}
