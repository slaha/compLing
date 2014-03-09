package cz.compling.poem;

import java.util.Arrays;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 8.3.14 7:13</dd>
 * </dl>
 */
public class Verse {

	private final String verse;

	public Verse(String verse) {
		this.verse = verse;
	}

	public Iterable<String> getWords() {
		return Arrays.asList(verse.split("\\s+"));
	}

	@Override
	public String toString() {
		return verse;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o instanceof Verse) {

			Verse verse1 = (Verse) o;
			return this.verse.equals(verse1.verse);

		}
		return false;
	}

	@Override
	public int hashCode() {
		return verse.hashCode();
	}
}
