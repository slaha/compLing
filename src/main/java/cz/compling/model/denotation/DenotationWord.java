package cz.compling.model.denotation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 1.5.14 18:18</dd>
 * </dl>
 */
public class DenotationWord {

	private final int wordNumber;
	private final int verseNumber;
	private final int stropheNumber;

	private final String word;
	/** List contains joined words. If it is empty,this word is not joined with any another word */
	private final List<DenotationWord> joinedWords;
	private final List<DenotationElement> denotationElements;

	private boolean ignored;
	/**
	 * true if another word joins this word
	 */
	private boolean joined;

	public DenotationWord(String word, int wordNumber, int verseNumber, int stropheNumber) {
		this.wordNumber = wordNumber;
		this.verseNumber = verseNumber;
		this.stropheNumber = stropheNumber;
		this.word = word;
		joinedWords = new ArrayList<DenotationWord>(0);
		denotationElements = new ArrayList<DenotationElement>(1);
		denotationElements.add(new DenotationElement(this, wordNumber));
	}

	int joinWith(DenotationWord wordToJoin) {
		final int maxIndex;
		if (joinedWords.isEmpty()) {
			maxIndex = wordNumber;
		} else {
			maxIndex = joinedWords.get(joinedWords.size() - 1).getNumber();
		}
		int removedDenotationElements;
		if (maxIndex == (wordToJoin.getNumber() - 1)) {

			this.joinedWords.add(wordToJoin);
			removedDenotationElements = wordToJoin.getDenotationElements().size();
			this.joinedWords.addAll(wordToJoin.getJoinedWords());
			wordToJoin.denotationElements.clear();
			wordToJoin.joinedWords.clear();
			wordToJoin.joined = true;
		} else {
			throw new IllegalArgumentException("Cannot join " + this + " with " + wordToJoin + " because this word has max number " + maxIndex + " and wordToJoin " + wordToJoin.getNumber());
		}
		return removedDenotationElements;
	}

	void split(DenotationWord wordToSplit) {
		if (joinedWords.isEmpty()) {
			return;
		}
		final int lastWordIndex = joinedWords.size() - 1;
		if (joinedWords.get(lastWordIndex) != wordToSplit) {
			return;
		}
		final DenotationWord splitWord = joinedWords.remove(lastWordIndex);
		splitWord.addElement(new DenotationElement(splitWord, getLastElement().getNumber() + 1));
		splitWord.joined = false;
	}

	@Override
	public String toString() {
		return word;
	}

	public String getWord() {
		return word;
	}

	public boolean isIgnored() {
		return ignored;
	}

	void setIgnored(boolean ignored) {
		this.ignored = ignored;
	}

	public int getNumber() {
		return wordNumber;
	}

	public int getStropheNumber() {
		return stropheNumber;
	}

	public int getVerseNumber() {
		return verseNumber;
	}

	public List<DenotationElement> getDenotationElements() {
		return Collections.unmodifiableList(denotationElements);
	}

	public List<DenotationWord> getWords() {
		List<DenotationWord> list = new ArrayList<DenotationWord>(1 + joinedWords.size());
		list.add(this);
		list.addAll(joinedWords);
		return Collections.unmodifiableList(list);
	}

	public boolean isJoined() {
		return joined;
	}

	public boolean isInSpike() {
		for (DenotationElement denotationElement : denotationElements) {
			if (denotationElement.isInSpike()) {
				return true;
			}
		}
		return false;
	}

	public boolean areAllSpikesAssigned() {
		for (DenotationElement denotationElement : denotationElements) {
			if (!denotationElement.isInSpike()) {
				return false;
			}
		}
		return true;
	}

	public boolean isInSpike(Spike spike) {
		for (DenotationElement denotationElement : denotationElements) {
			if (denotationElement.getSpike() == spike) {
				return true;
			}
		}
		return false;
	}

	public DenotationElement getElementInSpike(Spike spike) {
		for (DenotationElement denotationElement : denotationElements) {
			if (denotationElement.getSpike() == spike) {
				return denotationElement;
			}
		}
		throw new IllegalStateException("Denotation word" + this + " is not in spike " + spike);
	}

	public boolean hasFreeElement() {
		for (DenotationElement element : denotationElements) {
			if (!element.isInSpike()) {
				return true;
			}
		}
		return false;
	}

	public List<Spike> getSpikes() {
		List<Spike> spikes = new ArrayList<Spike>();
		for (DenotationElement element : denotationElements) {
			if (element.isInSpike()) {
				spikes.add(element.getSpike());
			}
		}
		return Collections.unmodifiableList(spikes);
	}

	public List<DenotationWord> getJoinedWords() {
		return joinedWords;
	}

	public DenotationElement getFreeElement() {
		for (DenotationElement element : denotationElements) {
			if (!element.isInSpike()) {
				return element;
			}
		}
		throw new IllegalStateException("Word " + this + " (number " + getNumber() + ") has no free element");
	}

	private DenotationElement getLastElement() {
		return denotationElements.get(denotationElements.size() - 1);
	}

	void addElement() {
		if (denotationElements.isEmpty()) {
			throw new IllegalStateException("cannot add element to word " + this + " (nmbr. " + getNumber() + "). Word does not have any element now");
		}
		final int elementNumber = getLastElement().getNumber() + 1;
		addElement(new DenotationElement(this, elementNumber));
	}

	void addElement(DenotationElement element) {
		denotationElements.add(element);
	}

	void removeElement(DenotationElement element) {
		element.onRemoveFromSpike(element.getSpike());
		denotationElements.remove(denotationElements.size() - 1);
	}

	void incrementNumbers(int increment) {
		if (isIgnored() || isJoined()) {
			return;
		}
		for (DenotationElement element : denotationElements) {
			element.increment(increment);
		}

	}

	public DenotationElement getFreeElement(int elementNumber) {
		for (DenotationElement element : denotationElements) {
			if (element.getNumber() == elementNumber) {
				if (!element.isInSpike()) {
					return element;
				}
			}
		}
		throw new IllegalStateException("Word " + this + " (number " + getNumber() + ") has no free element with number " + elementNumber);
	}

	void onRemoveFromSpike(Spike spike) {
		getElementForSpike(spike).onRemoveFromSpike(spike);
	}

	void onAddToSpike(Spike spike, String input) {
		getFreeElement().onAddToSpike(spike, input);
	}

	void onAddToSpike(Spike spike, String input, int elementNumber) {
		getFreeElement(elementNumber).onAddToSpike(spike, input);
	}


	private DenotationElement getElementForSpike(Spike spike) {
		for (DenotationElement element : denotationElements) {
			if (element.getSpike() == spike) {
				return element;
			}
		}
		throw new IllegalArgumentException("Cannot found element for spike "  + spike + ". Word " + this + " has only elements " + denotationElements);
	}
}
