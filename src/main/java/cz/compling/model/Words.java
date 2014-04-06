package cz.compling.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 6.4.14 12:15</dd>
 * </dl>
 */
public class Words implements Iterable<String> {

	private final List<String> words;

	public Words() {
		words = new ArrayList<String>();
	}

	@Override
	public Iterator<String> iterator() {
		return words.iterator();
	}

	public void add(String word) {
		words.add(word);
	}
}