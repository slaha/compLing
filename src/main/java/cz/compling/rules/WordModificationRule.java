package cz.compling.rules;

import cz.compling.utils.Reference;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 15.2.14 9:13</dd>
 * </dl>
 */
public interface WordModificationRule extends Rule {

	boolean modify(Reference<String> word, Reference<Integer> length);
}
