package cz.compling.analysis.analysator.poems.aggregation;

import cz.compling.model.Aggregation;
import cz.compling.rules.BaseRuleHandler;
import cz.compling.rules.RuleHandler;
import cz.compling.rules.RuleObserver;
import cz.compling.text.poem.Poem;
import cz.compling.text.poem.PoemModificationRule;
import cz.compling.text.poem.Verse;

import java.util.Arrays;
import java.util.Collection;

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
public class AggregationImpl implements IAggregation {

	private final RuleHandler<AggregationRule> ruleHandler;

	private final Poem poem;

	private final Aggregation aggregation;

	private boolean dirty;

	public AggregationImpl(Poem poem) {
		this.poem = poem;
		ruleHandler = new BaseRuleHandler<AggregationRule>();
		aggregation = new Aggregation();
		compute();
		dirty = false;
	}

	private void compute() {
		Poem onlyLettersPoem = toOnlyLetters();
		Collection<Verse> verses = onlyLettersPoem.getVerses();
		final Verse[] lines = verses.toArray(new Verse[verses.size()]);

		if (lines.length < 2) {
			return;
		}

		final String[] line1single = createChunks(lines[0].toString(), 1);
		final String[] line1double = createChunks(lines[0].toString(), 2);
		applyRules(line1single);
		applyRules(line1double);
		Arrays.sort(line1single);
		Arrays.sort(line1double);

		final int sizeA1 = line1single.length;
		final int sizeB1 = line1double.length;

		int currentLine = 1;
		while (currentLine < lines.length) {
			final String line = lines[currentLine].toString();

			final String[] lineSingle = createChunks(line, 1);
			final String[] lineDouble = createChunks(line, 2);
			applyRules(lineSingle);
			applyRules(lineDouble);
			Arrays.sort(lineSingle);
			Arrays.sort(lineDouble);

			final int intersection1 = intersection(line1single, lineSingle);
			final int intersection2 = intersection(line1double, lineDouble);

			aggregation.put(
				currentLine,
				intersection1,
				sizeA1,
				lineSingle.length,
				intersection2,
				sizeB1,
				lineDouble.length);

			currentLine++;
		}
	}

	/**
	 * Computes intersection of two sorted arrays of strings
	 *
	 * @param line1 first array
	 * @param line2 second array
	 *
	 * @return number that indicates count of the same strings on the same indexes in the arrays
	 */
	private int intersection(String[] line1, String[] line2) {
		int matches = 0;
		int i1 = 0, i2 = 0;
		while (i1 < line1.length && i2 < line2.length) {
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

	private void applyRules(String[] line) {
		String s = "";
		for (int i = 0; i < line.length; i++) {
			String chunk = line[i];
			for (AggregationRule aggregationRule : getRegisteredRules()) {
				if (aggregationRule.matchesRule(chunk)) {
					chunk = aggregationRule.compareTo(chunk);
				} else {
					s += chunk;
				}
			}
			line[i] = chunk.toLowerCase();
		}

	}

	private String[] createChunks(String line, int size) {
		final int chunksSize = line.length() - (size - 1);
		final String[] chunks = new String[chunksSize];

		for (int index = 0; index < chunksSize; index++) {

			String chunk = line.substring(index, index + size);
			chunks[index] = chunk;
		}
		return chunks;
	}

	private Poem toOnlyLetters() {
		return poem.applyRule(new PoemModificationRule() {
			@Override
			public String modify(Poem poem) {
			StringBuilder sb = new StringBuilder();
			final String text = poem.getPlainText();
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

			return sb.toString();
			}
		});
	}


	@Override
	public Iterable<AggregationRule> getRegisteredRules() {
		return ruleHandler.getRegisteredRules();
	}

	@Override
	public void registerRule(AggregationRule rule) {
		ruleHandler.registerRule(rule);
		dirty = true;
	}

	@Override
	public boolean removeRule(AggregationRule rule) {
		if (ruleHandler.removeRule(rule)) {
			dirty = true;
			return true;
		}
		return false;
	}

	@Override
	public void registerRuleObserver(RuleObserver<AggregationRule> observer) {
		ruleHandler.registerRuleObserver(observer);
	}

	@Override
	public boolean unregisterRuleObserver(RuleObserver<AggregationRule> observer) {
		return ruleHandler.unregisterRuleObserver(observer);
	}

	@Override
	public Iterable<RuleObserver<AggregationRule>> getRegisteredObserves() {
		return ruleHandler.getRegisteredObserves();
	}

	@Override
	public Aggregation getAggregation() {
		if (dirty) {
			aggregation.clear();
			compute();
		}
		return aggregation;
	}
}