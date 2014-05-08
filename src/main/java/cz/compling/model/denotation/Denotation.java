package cz.compling.model.denotation;

import cz.compling.model.Words;
import cz.compling.text.poem.Poem;
import gnu.trove.procedure.TObjectProcedure;

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
 * <dd> 1.5.14 17:42</dd>
 * </dl>
 */
public class Denotation {

	private final SpikesHolder spikesHolder;
	private final DenotationPoem denotationPoem;

	public Denotation(Poem poem, Words words) {
		denotationPoem = new DenotationPoem(poem, words);
		spikesHolder = new SpikesHolder();
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

	public void duplicateElement(int denotationWordNumber, DenotationElement elementToDuplicate) {
		final DenotationWord word = getWord(denotationWordNumber);
		word.addElement(elementToDuplicate.duplicate());
		forEachValue(getIncrementaror(1), word.getNumber());
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
