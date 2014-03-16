package cz.compling.text;

import java.util.Arrays;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 16.3.14 11:18</dd>
 * </dl>
 */
public class Line {

	private final String line;

	public Line(String line) {
		this.line = line;
	}

	public Iterable<String> getWords() {
		return Arrays.asList(line.split("\\s+"));
	}

	@Override
	public String toString() {
		return line;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o instanceof Line) {

			Line line1 = (Line) o;
			return this.line.equals(line1.line);

		}
		return false;
	}

	@Override
	public int hashCode() {
		return line.hashCode();
	}
}
