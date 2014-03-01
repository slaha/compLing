package cz.compling.analysis.analysator.frequency.impl;

import cz.compling.analysis.analysator.frequency.WordFrequency;
import cz.compling.rules.TextModificationRule;
import cz.compling.text.Text;
import cz.compling.text.TextImpl;
import cz.compling.utils.TrooveUtils;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.javatuples.Pair;

import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 1.3.14 11:19</dd>
 * </dl>
 */
public class WordFrequencyImpl implements WordFrequency {

	private static final String SPLIT_BY_SPACE_S_REGEX = "( )+";

	/** Text to analyse */
	private final Text text;

	private final TObjectIntMap<String> frequency;

	private int countOfWords;

	public WordFrequencyImpl(Text text) {
//		this.text = text;
		this.text = new TextImpl("Ahoj,    jak se    máte????\nJá   se   mááám  dobřeeee   jak\n\n\n");
		frequency = new TObjectIntHashMap<String>();
		compute();
	}

	private void compute() {
		//..first we have to remove all non-alphabet characters.
		//..All other characters are replaced by space
		TextModificationRule removeNonAlphabetCharacters = new TextModificationRule() {
			@Override
			public String modify(String text) {
				StringBuilder sb = new StringBuilder(text.length());
				for (char c : text.toCharArray()) {
					if (c == ' ' || Character.isLetter(c)) {
						sb.append(c);
					} else {
						sb.append(' ');
					}
				}
				return sb.toString().trim();
			}
		};
		final String onlyAlphabetText = text.applyRule(removeNonAlphabetCharacters);

		//..Maybe String.split("( )+").length is faster I don't know
		StringTokenizer tokenizer = new StringTokenizer(onlyAlphabetText, " ", false);
		this.countOfWords = tokenizer.countTokens();

		String nextWord;
		while (tokenizer.hasMoreTokens()) {
			nextWord =  tokenizer.nextToken();

			frequency.adjustOrPutValue(nextWord, 1, 1);
		}	
	}

	@Override
	public int getCountOfWords() {
		return countOfWords;
	}

	@Override
	public int getFrequencyFor(String word) {
		if (word == null || word.trim().isEmpty()) {
			throw new IllegalArgumentException("Param word cannot be NULL or empty or only whitespaces");
		}
		return frequency.get(word);
	}

	@Override
	public List<Pair<String, Integer>> getAllByFrequency(TrooveUtils.SortOrder order) {

		return new TrooveUtils.Lists<String, Pair<String, Integer>>()
			.toList(frequency)
			.sort(order)
			.getList();
	}

	public static void main(String[] args) {
		WordFrequency wf = new WordFrequencyImpl(null);
		System.out.println("Počet slov " + wf.getCountOfWords());
		System.out.println("Počet slov 'jak' " + wf.getFrequencyFor("jak"));
		System.out.println("Dle frekvence: \n" + wf.getAllByFrequency(TrooveUtils.SortOrder.DESCENDING));
	}
}
