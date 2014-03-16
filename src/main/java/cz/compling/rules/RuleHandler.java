package cz.compling.rules;

/**
 *
 * Interface with methods to handle rules
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 16.3.14 11:13</dd>
 * </dl>
 */
public interface RuleHandler<T> {

	/**
	 * Returns all registered rules
	 *
	 * @return rules
	 */
	Iterable<T> getRegisteredRules();


	/**
	 * Registers new {@code rule}
	 * @param rule rule to register
	 */
	void registerRule(T rule);

	/**
	 * Removes registered rule
	 *
	 * @param rule rule to remove
	 *
	 * @return true if rule was removed, false otherwise
	 */
	boolean removeRule(T rule);

	void registerRuleObserver(RuleObserver<T> observer);

	boolean unregisterRuleObserver(RuleObserver<T> observer);

	Iterable<RuleObserver<T>> getRegisteredObserves();
}
