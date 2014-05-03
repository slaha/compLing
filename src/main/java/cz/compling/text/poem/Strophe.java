package cz.compling.text.poem;

import java.util.Collection;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 3.5.14 14:43</dd>
 * </dl>
 */
public interface Strophe extends Comparable<Strophe> {
	Collection<Verse> getVerses();

	int getNumber();
}
