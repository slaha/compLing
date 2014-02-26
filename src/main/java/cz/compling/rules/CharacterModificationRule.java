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

	/**
	 *
	 * Rule
	 *
	 * @param text text which is analysed
	 * @param putToFrequency reference (out parameter). Holds string that should be saved in frequency table.
	 *                          If its value is null the string is ignored but position is shifted.
	 *                          Modify only in case this method returns true
	 * @param position  reference (out parameter). Incoming value of analysed position in {@code text}.
	 *                          Modify only in case this method returns true
	 * @return true if matches; false otherwise
	 */
	boolean modify(String text, Reference<String> putToFrequency, Reference<Integer> position);
}
