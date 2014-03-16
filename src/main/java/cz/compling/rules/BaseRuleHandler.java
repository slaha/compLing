package cz.compling.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 16.3.14 11:39</dd>
 * </dl>
 */
public class BaseRuleHandler<T> implements RuleHandler<T> {

	protected List<T> rules = new ArrayList<T>();
	protected List<RuleObserver<T>> observers = new ArrayList<RuleObserver<T>>();

	private static final int ADDED = 0;
	private static final int REMOVED = 1;

	@Override
	public Iterable<T> getRegisteredRules() {
		return Collections.unmodifiableList(rules);
	}

	@Override
	public void registerRule(T rule) {
		rules.add(rule);
		notify(rule, ADDED);
	}

	private void notify(T rule, int action) {
		final Iterable<T> rls = getRegisteredRules();
		switch (action) {
			case ADDED:
				for (RuleObserver<T> observer : observers) {
					observer.onRuleAdded(rule, rls);
				}
				break;
			case REMOVED:
				for (RuleObserver<T> observer : observers) {
					observer.onRuleRemoved(rule, rls);
				}
				break;
		}
	}

	@Override
	public boolean removeRule(T rule) {
		if (rules.remove(rule)) {
			notify(rule, REMOVED);
			return true;
		}
		return false;
	}

	@Override
	public void registerRuleObserver(RuleObserver<T> observer) {
		observers.add(observer);

	}

	@Override
	public boolean unregisterRuleObserver(RuleObserver<T> observer) {
		return observers.remove(observer);
	}

	@Override
	public Iterable<RuleObserver<T>> getRegisteredObserves() {
		return observers;
	}
}
