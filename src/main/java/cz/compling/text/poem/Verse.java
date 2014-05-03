package cz.compling.text.poem;

import cz.compling.text.Line;

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
public class Verse extends Line {


	public Verse(int number, String verse) {
		super(number, verse);
	}

	public Verse(Line verse) {
		this(verse.getNumber(), verse.toString());
	}
}
