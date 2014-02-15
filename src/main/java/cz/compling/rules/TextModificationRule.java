package cz.compling.rules;

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
public interface TextModificationRule extends Rule {

	String modify(String text);
}
