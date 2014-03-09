package cz.compling.analysis.analysator.poems;

import cz.compling.model.Alliteration;

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
public interface IAlliteration {
	Alliteration getAlliterationFor(int numberOfVerse);

	int getVerseCount();
}
