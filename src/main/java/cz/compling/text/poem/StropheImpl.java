package cz.compling.text.poem;

import java.util.Collection;
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
 * <dd> 3.5.14 14:44</dd>
 * </dl>
 */
public class StropheImpl implements Strophe {


	private final List<Verse> verses;
	private final int number;

	public StropheImpl(int number, List<Verse> verses) {
		this.verses = verses;
		this.number = number;
	}

	@Override
	public Collection<Verse> getVerses() {
		Collections.sort(verses);
		return Collections.unmodifiableList(verses);
	}

	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public int compareTo(Strophe o) {
		return this.getNumber() - o.getNumber();
	}
}
