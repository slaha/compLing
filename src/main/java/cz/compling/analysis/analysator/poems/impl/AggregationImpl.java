package cz.compling.analysis.analysator.poems.impl;

import cz.compling.model.Aggregation;
import cz.compling.rules.TextModificationRule;
import cz.compling.text.Text;
import cz.compling.text.TextImpl;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.text.Normalizer;
import java.util.Arrays;

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
public class AggregationImpl implements cz.compling.analysis.analysator.poems.Aggregation {

	private final Text poem;

	private final TIntObjectMap<Aggregation> aggregation;

	public AggregationImpl(Text poem) {
		this.poem = poem;
		aggregation = new TIntObjectHashMap<Aggregation>();
		compute();
	}

	private void compute() {
		final String onlyCharactersPoem = toOnlyLowercaseLetters();
		final String[] lines = onlyCharactersPoem.split("\n");

		if (lines.length < 2) {
			return;
		}

		final String[] line1single = createChunks(lines[0], 1);
		final String[] line1double = createChunks(lines[0], 2);
		Arrays.sort(line1single);
		Arrays.sort(line1double);

		final int sizeA1 = line1single.length;
		final int sizeB1 = line1double.length;

		int currentLine = 1;
		while (currentLine < lines.length) {
			final String line = lines[currentLine];


			final String[] lineSingle = createChunks(line, 1);
			final String[] lineDouble = createChunks(line, 2);

			Arrays.sort(lineSingle);
			Arrays.sort(lineDouble);

			final int intersection1 = intersection(line1single, lineSingle);
			final int intersection2 = intersection(line1double, lineDouble);

			Aggregation aggreg = new Aggregation(

				intersection1,
				sizeA1,
				lineSingle.length,
				intersection2,
				sizeB1,
				lineDouble.length

			);

			aggregation.put(currentLine, aggreg);

			currentLine++;
		}
	}

	private int intersection(String[] line1, String[] line2) {
		final int maxIndex = Math.min(line1.length, line2.length);

		int matches = 0;
		int i1 = 0, i2 = 0;
		while (i1 < maxIndex && i2 < maxIndex) {
			final int cmp = line1[i1].compareTo(line2[i2]);
			if (cmp == 0) {
				matches++;
				i1++;
				i2++;
			} else if (cmp < 0) {
				i1++;
			} else {
				i2++;
			}
		}
		return matches;
	}

	private String[] createChunks(String line, int size) {
		final String[] chunks = new String[line.length() - (size - 1)];

		for (int index = 0; index < line.length() - (size - 1); index++) {

			String chunk = line.substring(index, index + size);
			chunks[index] = chunk;
		}
		return chunks;
	}

	private String toOnlyLowercaseLetters() {
		return poem.applyRule(new TextModificationRule() {
			@Override
			public String modify(String text) {
				StringBuilder sb = new StringBuilder();
				char last = 0;
				for (char c : text.toCharArray()) {
					if (Character.isLetter(c)) {
						//..we need to be lowercase
						sb.append(Character.toLowerCase(c));
					} else if (c == '\n' && last != '\n') {
						//..put line separator but not more than one
						sb.append(c);
					}
					last = c;
				}
				//diacritics out
				String decomposed = java.text.Normalizer.normalize(sb.toString(), Normalizer.Form.NFD);
                return  decomposed.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
			}
		});
	}

	public static void main(String[] args) {
		AggregationImpl aggregation1 = new AggregationImpl(new TextImpl("Usnul tu na zemi\npokryté sazemi"));
		System.out.println(aggregation1.aggregation);
	}
}
