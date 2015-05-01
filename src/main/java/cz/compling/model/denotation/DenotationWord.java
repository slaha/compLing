package cz.compling.model.denotation;

import org.apache.commons.lang3.text.StrBuilder;

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
		List<DenotationWord> list = new ArrayList<DenotationWord>(1 + joinedWords.size()) {
			@Override
			public String toString() {
				if (isEmpty()) {
					return "";
				}
				StrBuilder strBuilder = new StrBuilder();
				return strBuilder.appendWithSeparators(this, ",").toString();
 			}
		};
		list.add(this);
		list.addAll(joinedWords);
		return Collections.unmodifiableList(list);
	}

	public boolean isJoined() {
		return joined;
	}

	public boolean isInHreb() {
		for (DenotationElement denotationElement : denotationElements) {
			if (denotationElement.isInHreb()) {
				return true;
			}
		}
		return false;
	}

	public boolean areAllHrebsAssigned() {
		for (DenotationElement denotationElement : denotationElements) {
			if (!denotationElement.isInHreb()) {
				return false;
			}
		}
		return true;
	}

	public boolean isInHreb(Hreb hreb) {
		for (DenotationElement denotationElement : denotationElements) {
			if (denotationElement.getHreb() == hreb) {
				return true;
			}
		}
		return false;
	}

	public DenotationElement getElementInHreb(Hreb hreb) {
		for (DenotationElement denotationElement : denotationElements) {
			if (denotationElement.getHreb() == hreb) {
				return denotationElement;
			}
		}
		throw new IllegalStateException("Denotation word" + this + " is not in hreb " + hreb);
	}

	public boolean hasFreeElement() {
		for (DenotationElement element : denotationElements) {
			if (!element.isInHreb()) {
				return true;
			}
		}
		return false;
	}

	public List<Hreb> getHrebs() {
		List<Hreb> hrebs = new ArrayList<Hreb>();
		for (DenotationElement element : denotationElements) {
			if (element.isInHreb()) {
				hrebs.add(element.getHreb());
			}
		}
		return Collections.unmodifiableList(hrebs);
	}

	public List<DenotationWord> getJoinedWords() {
		return joinedWords;
	}

	public DenotationElement getFreeElement() {
		for (DenotationElement element : denotationElements) {
			if (!element.isInHreb()) {
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
		element.onRemoveFromHreb(element.getHreb());
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
				if (!element.isInHreb()) {
					return element;
				}
			}
		}
		throw new IllegalStateException("Word " + this + " (number " + getNumber() + ") has no free element with number " + elementNumber);
	}

	void onRemoveFromHreb(Hreb hreb) {
		getElementForHreb(hreb).onRemoveFromHreb(hreb);
	}

	void onAddToHreb(Hreb hreb, String input) {
		getFreeElement().onAddToHreb(hreb, input);
	}

	void onAddToHreb(Hreb hreb, String input, int elementNumber) {
		getFreeElement(elementNumber).onAddToHreb(hreb, input);
	}


	private DenotationElement getElementForHreb(Hreb hreb) {
		for (DenotationElement element : denotationElements) {
			if (element.getHreb() == hreb) {
				return element;
			}
		}
		throw new IllegalArgumentException("Cannot found element for hreb "  + hreb + ". Word " + this + " has only elements " + denotationElements);
	}
}
