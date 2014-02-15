package cz.compling.rules;


import utils.Reference;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 15.2.14 9:30</dd>
 * </dl>
 */
public interface CharacterModificationRule extends Rule {

	boolean modify(String text, Reference<String> putToFrequency, Reference<Integer> position);
}
