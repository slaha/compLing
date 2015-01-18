package cz.compling.model.denotation;

import cz.compling.model.Words;
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
public class Denotation {

	private final SpikesHolder spikesHolder;
	private final DenotationPoem denotationPoem;
	private final DenotationMath denotationMath;

	public Denotation(Poem poem, Words words) {
		denotationPoem = new DenotationPoem(poem, words);
		spikesHolder = new SpikesHolder();
		denotationMath = new DenotationMath();
	}

	public int createNewSpike() {
		return spikesHolder.createNewSpike();
	}

	public int addSpike(Spike spike) {
		return spikesHolder.addSpike(spike);
	}

	public int removeSpike(int number) {
		return spikesHolder.removeSpike(number);
	}

	public Spike getSpike(int number) {
		return spikesHolder.getSpike(number);
	}

	public boolean containsSpike(int number) {
		return spikesHolder.containsSpike(number);
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

	public Collection<Spike> getSpikes() {
		return Arrays.asList(spikesHolder.getSpikes());
	}

	public void addElementTo(int denotationWordNumber) {
		final DenotationWord word = getWord(denotationWordNumber);
		word.addElement();
		forEachValue(getIncrementaror(1), word.getNumber());
	}

	public void addElementTo(int denotationWordNumber, int elementNumber) {
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

	public double computeTopikalnost(Spike spike, double cardinalNumber) {
		return ((double)spike.getWords().size()) / cardinalNumber;
	}

	public List<DenotationWord> getAllWords() {
		return new ArrayList<DenotationWord>(denotationPoem.getAllWords().valueCollection());
	}

	private double getMaxDenotationElement() {

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
		double n = spikesHolder.getSpikes().length;
		double l = getMaxDenotationElement();
		double numerator = 1d - (n / l);
		double denominator = 1d - (1d / l);
		return numerator / denominator;
	}

	private long[] calcSumAndN() {
		final Spike[] spikes = spikesHolder.getSpikes();
		TIntIntMap map = new TIntIntHashMap();
		for (Spike spike : spikes) {
			map.adjustOrPutValue(spike.size(), 1, 1);
		}
		long sum = 0;
		long n = 0;
		for (int key : map.keys()) {
			sum += Math.pow(key, 2) * map.get(key);
			n += key * map.get(key);
		}
		return new long[]{sum, n};
	}

	public double getTextCentralization() {
		long[] values = calcSumAndN();
		return getTextCentralization(values[0], values[1]);
	}

	public double getTextCentralization(long sum, long n) {
		return ((double)sum) / Math.pow(n, 2);
	}

	public double getMacIntosh() {
		long[] values = calcSumAndN();

		double numerator = 1d - Math.sqrt(getTextCentralization(values[0], values[1]));
		double denominator = 1d - (1d / values[1]);

		return numerator / denominator;
	}

	public double getDiffusionFor(int spikeNumber) {
		final Spike spike = spikesHolder.getSpike(spikeNumber);
		final List<DenotationWord> words = new ArrayList<DenotationWord>(spike.getWords());
		if (words.isEmpty()) {
			return 0;
		}
		int elementMin = Integer.MAX_VALUE;
		int elementMax = Integer.MIN_VALUE;
		for (int indexOf = 0; indexOf < words.size(); indexOf++) {
			final DenotationWord word = words.get(indexOf);
			if (word.isIgnored()  || word.getDenotationElements().isEmpty()) {
				continue;
			}
			for (DenotationElement element : word.getDenotationElements()) {
				if (element.getSpike().getNumber() != spikeNumber) {
					continue;
				}
				if (element.getNumber() < elementMin) {
					elementMin = element.getNumber();
				}
				if (element.getNumber() > elementMax) {
					elementMax = element.getNumber();
				}
			}
		}
		return ( ((double)elementMax) - ((double)elementMin) ) / ((double)spike.size());
	}

	public List<Coincidence> getCoincidenceFor(int spikeNumber) {
		List<Coincidence> coincidences = new ArrayList<Coincidence>();

		final GuiPoemAsSpikeNumbers poem = getPoemAsSpikeNumbers();
		final int N = poem.getVersesCount();
		final int M = poem.getVersesCountWith(spikeNumber);

		final Spike baseSpike = getSpike(spikeNumber);

		final List<Spike> spikes = new ArrayList<Spike>(getSpikes());
		for (Spike anotherSpike : spikes) {
			if (anotherSpike.getNumber() != spikeNumber) {
				final int n = poem.getVersesCountWith(anotherSpike.getNumber());
				final int x = poem.getVersesCountWithBoth(spikeNumber, anotherSpike.getNumber());
				if (n > 0 && x > 0) {
					double coincidenceResult = denotationMath.computeCoincidence(N, M, n, x);
					coincidences.add(new Coincidence(baseSpike, anotherSpike, x, coincidenceResult));
				}
			}
		}
		return coincidences;
	}

	public GuiPoemAsSpikeNumbers getPoemAsSpikeNumbers() {
		return new GuiPoemAsSpikeNumbers(getAllWords());
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
