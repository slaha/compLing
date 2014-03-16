package cz.compling.analysis.analysator.frequency.words;

import cz.compling.utils.Reference;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 16.3.14 11:36</dd>
 * </dl>
 */
public interface WordFrequencyRule {

	boolean modify(Reference<String> word, Reference<Integer> length);
}
