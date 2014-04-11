package cz.compling.text;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

	public List<String> getWords() {
		return getWords(true);
	}
	public List<String> getWords(boolean onlyAlpha) {
		List<String> words = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		String word;
		for (String chunk : line.trim().split("\\s+")) {
			if (onlyAlpha) {
				removeNonAlpha(chunk, sb);
				if (StringUtils.isNotBlank((word = sb.toString()))) {
					words.add(word);
				}
			} else {
				words.add(chunk);
			}
		}

		return words;
	}

	private void removeNonAlpha(String chunk, StringBuilder builder) {
		builder.setLength(0);
		for (char c : chunk.toCharArray()) {
			if (Character.isLetter(c)) {
				builder.append(c);
			}
		}
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

	public char[] getCharacters() {
		return line.trim().toCharArray();
	}
}