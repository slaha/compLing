package cz.compling.model.denotation;

import cz.compling.analysis.analysator.poems.denotation.IDenotation;
import cz.compling.text.poem.Poem;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.procedure.TObjectProcedure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 1.5.14 17:42</dd>
 * </dl>
 */
public class Denotation implements CoincidenceProvider, IDenotation {

	private final HrebsHolder hrebsHolder;
	private final DenotationPoem denotationPoem;
	private final DenotationMath denotationMath;

	public Denotation(Poem poem) {
		denotationPoem = new DenotationPoem(poem);
		hrebsHolder = new HrebsHolder();
		denotationMath = new DenotationMath();
	}

	public int createNewHreb() {
		return hrebsHolder.createNewHreb();
	}

	public int addHreb(Hreb hreb) {
		return hrebsHolder.addHreb(hreb);
	}

	public int removeHreb(int number) {
		return hrebsHolder.removeHreb(number);
	}

	public Hreb getHreb(int number) {
		return hrebsHolder.getHreb(number);
	}

	public boolean containsHreb(int number) {
		return hrebsHolder.containsHreb(number);
	}

	public DenotationWord getWord(int number) {
		return denotationPoem.getWord(number);

	}

	public void clearAllWords() {
		denotationPoem.clearAllWords();
	}

	public void addNewWord(int number, DenotationWord word) {
		denotationPoem.putWord(number, word);
	}

	public int getCountOfWords() {
		return denotationPoem.getCountOfWords();
	}

	public Collection<Hreb> getHrebs() {
		return Arrays.asList(hrebsHolder.getHrebs());
	}

	public void addNewElementTo(int denotationWordNumber) {
		final DenotationWord word = getWord(denotationWordNumber);
		word.addElement();
		forEachValue(getIncrementaror(1), word.getNumber());
	}

	public void addNewElementTo(int denotationWordNumber, int elementNumber) {
		final DenotationWord word = getWord(denotationWordNumber);
		word.addElement(new DenotationElement(word, elementNumber));
		forEachValue(getIncrementaror(1), word.getNumber());
	}

	public DenotationElement duplicateElement(int denotationWordNumber, DenotationElement elementToDuplicate) {
		final DenotationWord word = getWord(denotationWordNumber);
		final DenotationElement duplicate = elementToDuplicate.duplicate();
		word.addElement(duplicate);
		forEachValue(getIncrementaror(1), word.getNumber());
		return duplicate;
	}

	public void removeElement(int denotationWordNumber, DenotationElement element) {
		final DenotationWord word = getWord(denotationWordNumber);
		word.removeElement(element);
		forEachValue(getIncrementaror(-1), word.getNumber());
	}
	public void joinWords(int wordNumber, int wordToJoinNumber) {
		final DenotationWord word = getWord(wordNumber);
		final DenotationWord wordToJoin = getWord(wordToJoinNumber);
		final int joined = word.joinWith(wordToJoin);
		forEachValue(getIncrementaror(-joined), word.getNumber());
	}

	public void split(int wordNumber, int wordToSplitNumber) {
		final DenotationWord word = getWord(wordNumber);
		final DenotationWord wordToSplit = getWord(wordToSplitNumber);
		word.split(wordToSplit);
		forEachValue(new ForEachRunner() {
			@Override
			public void run(DenotationWord word) {
				if (word.getNumber() > wordToSplit.getNumber()) {
					word.incrementNumbers(1);
				}
			}
		}, word.getNumber());
	}

	public void ignoreWord(int number, boolean ignored) {
		final DenotationWord word = getWord(number);
		word.setIgnored(ignored);
		forEachValue(getIncrementaror(ignored ? -1: 1), word.getNumber());
	}

	public double computeTopicality(Hreb hreb, double cardinalNumber) {
		return denotationMath.computeTopicality(hreb, cardinalNumber);
	}

	public List<DenotationWord> getAllWords() {
		return new ArrayList<DenotationWord>(denotationPoem.getAllWords().valueCollection());
	}

	private int getMaxDenotationElement() {

		int lastWordIndex = denotationPoem.getCountOfWords();
		DenotationWord lastWord = denotationPoem.getWord(lastWordIndex);
		while (lastWord.getDenotationElements().isEmpty() && lastWordIndex > 1) {
			lastWord = denotationPoem.getWord(--lastWordIndex);
		}

		final List<DenotationElement> elements = lastWord.getDenotationElements();
		int max = elements.get(0).getNumber();
		for (int i = 1; i < elements.size(); i++) {
			int number = elements.get(i).getNumber();
			if (number > max) {
				max = number;
			}
		}
		return max;
	}

	public double getTextCompactness() {
		double n = hrebsHolder.getHrebs().length;
		double l = getMaxDenotationElement();
		return denotationMath.computeTextCompactness(n, l);
	}

	private long[] calcSumAndN() {
		final Hreb[] hrebs = hrebsHolder.getHrebs();
		TIntIntMap map = new TIntIntHashMap();
		for (Hreb hreb : hrebs) {
			map.adjustOrPutValue(hreb.size(), 1, 1);
		}
		long sum = denotationMath.computeSumHiFi(map);
		long n = denotationMath.computeSumN(map);
		return new long[]{sum, n};
	}

	public double getTextCentralization() {
		long[] values = calcSumAndN();
		return denotationMath.computeTextCentralization(values[0], values[1]);
	}

	public double getMacIntosh() {
		long[] values = calcSumAndN();

		return denotationMath.computeMacIntosh(getTextCentralization(), values[1]);
	}

	public double getDiffusionFor(int hrebNumber) {
		final Hreb hreb = hrebsHolder.getHreb(hrebNumber);
		final List<DenotationWord> words = new ArrayList<DenotationWord>(hreb.getWords());
		return denotationMath.getDiffusionFor(words, hrebNumber, hreb.size());
	}

	@Override
	public List<Coincidence> getCoincidenceFor(int hrebNumber) {
		List<Coincidence> coincidences = new ArrayList<Coincidence>();

		final PoemAsHrebNumbers poem = getPoemAsHrebNumbers();
		final int N = poem.getVersesCount();
		final int M = poem.getVersesCountWith(hrebNumber);

		final Hreb baseHreb = getHreb(hrebNumber);

		final List<Hreb> hrebs = new ArrayList<Hreb>(getHrebs());
		for (Hreb anotherHreb : hrebs) {
			if (anotherHreb.getNumber() != hrebNumber) {
				final int n = poem.getVersesCountWith(anotherHreb.getNumber());
				final int x = poem.getVersesCountWithBoth(hrebNumber, anotherHreb.getNumber());
				if (n > 0 && x > 0) {
					double coincidenceResult = denotationMath.computeCoincidence(N, M, n, x);
					coincidences.add(new Coincidence(baseHreb, anotherHreb, x, coincidenceResult));
				}
			}
		}
		return coincidences;
	}

	public PoemAsHrebNumbers getPoemAsHrebNumbers() {
		return new PoemAsHrebNumbers(getAllWords());
	}

	public List<Coincidence> getDeterministicFor(int hrebNumber) {
		final List<Coincidence> coincidences = getCoincidenceFor(hrebNumber);
		final Hreb baseHreb = getHreb(hrebNumber);
		for (DenotationWord denotationWord : baseHreb.getWords()) {
			for (DenotationElement denotationElement : denotationWord.getDenotationElements()) {
				final Hreb hreb = denotationElement.getHreb();
				if (hreb != null && hreb.getNumber() != hrebNumber) {
					coincidences.add(new Determination(baseHreb, hreb));
				}
			}
		}
		return coincidences;
	}

	public double getNonContinuousIndex() {
		return denotationMath.computeNonContinuousIndex(getHrebs(), this);
	}

	public double getNonIsolationIndex() {
		return denotationMath.computeNonIsolationIndex(getHrebs(), this);
	}

	public double getReachabilityIndex() {
		ReachabilityComputer reachabilityComputer = new ReachabilityComputer(getHrebs(), this);
		reachabilityComputer.compute();
		return reachabilityComputer.getResult();
	}

	private interface ForEachRunner {
		void run(DenotationWord word);
	}

	private class ForEach implements TObjectProcedure<DenotationWord> {

		final ForEachRunner runnable;
		private final int number;

		private ForEach(ForEachRunner runnable, int number) {
			this.runnable = runnable;
			this.number = number;
		}

		@Override
		public boolean execute(DenotationWord denotationWord) {
			if (denotationWord.getNumber() > number && !denotationWord.isJoined() && !denotationWord.isIgnored()) {
				runnable.run(denotationWord);
			}
			return true;
		}
	}

	private ForEachRunner getIncrementaror(final int value) {
		return new ForEachRunner() {
			@Override
			public void run(DenotationWord word) {
				word.incrementNumbers(value);
			}
		};
	}

	private void forEachValue(ForEachRunner runner, int number) {
		ForEach forEach = new ForEach(runner, number);
		denotationPoem.getAllWords().forEachValue(forEach);
	}
}
