package cz.compling.analysis.analysator.frequency;

import cz.compling.utils.Reference;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 16.3.14 12:25</dd>
 * </dl>
 */
public interface CharacterFrequencyRule {
	boolean modify(String plainText, Reference<String> putToMap, Reference<Integer> position);
}
