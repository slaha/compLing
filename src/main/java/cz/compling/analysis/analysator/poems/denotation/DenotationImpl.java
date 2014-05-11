package cz.compling.analysis.analysator.poems.denotation;

import cz.compling.model.Words;
import cz.compling.model.denotation.Denotation;
import cz.compling.model.denotation.DenotationElement;
import cz.compling.model.denotation.DenotationWord;
import cz.compling.model.denotation.Spike;
import cz.compling.text.poem.Poem;

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
public class DenotationImpl implements IDenotation {

	private final Denotation denotation;

	public DenotationImpl(Poem poem, Words words) {
		this.denotation = new Denotation(poem, words);
	}

	@Override
	public int createNewSpike() {
		return denotation.createNewSpike();
	}

	@Override
	public int addSpike(Spike spike) {
		return denotation.addSpike(spike);

	}

	@Override
	public boolean containsSpike(int number) {
		return denotation.containsSpike(number);
	}

	@Override
	public int removeSpike(int number) {
		return denotation.removeSpike(number);
	}

	@Override
	public Spike getSpike(int number) {
		return denotation.getSpike(number);
	}

	@Override
	public int getCountOfWords() {
		return denotation.getCountOfWords();
	}

	@Override
	public DenotationWord getWord(int number) {
		return denotation.getWord(number);
	}

	@Override
	public Collection<Spike> getSpikes() {
		return denotation.getSpikes();
	}

	@Override
	public void addNewElementTo(int denotationWordNumber) {
		denotation.addElementTo(denotationWordNumber);
	}

	@Override
	public void addNewElementTo(int denotationWordNumber, int elementNumber) {
		denotation.addElementTo(denotationWordNumber, elementNumber);
	}

	@Override
	public void duplicateElement(int number, DenotationElement elementToDuplicate) {
		denotation.duplicateElement(number, elementToDuplicate);

	}

	@Override
	public void removeElement(int number, DenotationElement element) {
		denotation.removeElement(number, element);

	}

	@Override
	public void joinWords(int word, int wordToJoin) {
		denotation.joinWords(word, wordToJoin);

	}

	@Override
	public void split(int wordNumber, int wordToSplitNumber) {
		denotation.split(wordNumber, wordToSplitNumber);
	}

	@Override
	public void ignoreWord(int number, boolean ignored) {
		denotation.ignoreWord(number, ignored);
	}

	@Override
	public void clearAllWords() {
		denotation.clearAllWords();
	}

	@Override
	public void addNewWord(int number, DenotationWord word) {
		denotation.addNewWord(number, word);

	}

	@Override
	public double computeTopikalnost(Spike spike, double cardinalNumber) {
		return denotation.computeTopikalnost(spike, cardinalNumber);
	}

	@Override
	public double getTextCompactness() {
		return denotation.getTextCompactness();

	}

	@Override
	public double getTextCentralization() {
		return denotation.getTextCentralization();
	}

	@Override
	public double getMacIntosh() {
		return denotation.getMacIntosh();
	}

	@Override
	public double getDiffusionFor(int spike) {
		return denotation.getDiffusionFor(spike);

	}
}