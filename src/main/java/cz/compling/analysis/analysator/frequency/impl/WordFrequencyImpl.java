package cz.compling.analysis.analysator.frequency.impl;

import cz.compling.analysis.analysator.frequency.WordFrequency;
import cz.compling.rules.TextModificationRule;
import cz.compling.rules.WordModificationRule;
import cz.compling.text.Text;
import cz.compling.utils.Reference;
import cz.compling.utils.TrooveUtils;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.javatuples.Pair;

import java.util.List;

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

	/** Text to analyse */
	private final Text text;

	/** Frequency of words (word is key, frequency is value) */
	private final TObjectIntMap<String> frequency;

	private final TIntIntHashMap wordsByLength;

	/** How many words is in the text */
	private int countOfWords;

	public WordFrequencyImpl(Text text) {
		this.text = text;
		frequency = new TObjectIntHashMap<String>();
		wordsByLength = new TIntIntHashMap();
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
					sb.append(Character.isLetter(c) ? c : ' ');
				}
				return sb.toString().trim();
			}
		};
		final String onlyAlphabetText = text.applyRule(removeNonAlphabetCharacters);

		//..Split by space(s). Used String.split because javadoc to StringTokenizer says "its use is discouraged in new code"
		String[] words = onlyAlphabetText.split("\\s+");
		this.countOfWords = words.length;

		final Reference<String> word = new Reference<String>();
		final Reference<Integer> length = new Reference<Integer>();
		for (String nextWord : words) {
			word.value = nextWord;
			length.value = nextWord.length();
			applyRules(word, length);
			frequency.adjustOrPutValue(word.value, 1, 1);
			wordsByLength.adjustOrPutValue(length.value, 1, 1);
		}	
	}

	private void applyRules(Reference<String> word, Reference<Integer> length) {

		for (WordModificationRule rule : text.getWordModificationRules()) {
			if (rule.modify(word, length)) {
				return;
			}
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
	public int getFrequencyFor(int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("Param length must be positive, was " + length);
		}

		return wordsByLength.get(length);
	}

	@Override
	public List<Pair<String, Integer>> getAllWordsByFrequency(TrooveUtils.SortOrder order) {

		return new TrooveUtils.Lists<String, Pair<String, Integer>>()
			.toList(frequency)
			.sort(order)
			.getList();
	}

	@Override
	public List<Pair<Integer, Integer>> getAllWordsLengthsByFrequency(TrooveUtils.SortOrder order) {

		return new TrooveUtils.Lists<Integer, Pair<Integer, Integer>>()
			.toList(wordsByLength)
			.sort(order)
			.getList();
	}
}
