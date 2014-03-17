package cz.compling.analysis.analysator.poems.alliteration;

import cz.compling.model.Alliteration;
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
public interface IAlliteration extends RuleHandler<AlliterationRule> {

	Alliteration getAlliteration();
}
