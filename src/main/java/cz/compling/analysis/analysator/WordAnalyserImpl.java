package cz.compling.analysis.analysator;

import cz.compling.analysis.analysator.frequency.words.IWordFrequency;
import cz.compling.analysis.analysator.frequency.words.IWords;
import cz.compling.analysis.analysator.frequency.words.WordFrequencyImpl;
import cz.compling.analysis.analysator.frequency.words.WordsImpl;
import cz.compling.text.Text;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 1.3.14 11:25</dd>
 * </dl>
 */
public class WordAnalyserImpl implements WordAnalyser {

	private final Text text;

	public WordAnalyserImpl(Text text) {
		this.text = text;
	}

	@Override
	public IWordFrequency getWordFrequency() {
		return new WordFrequencyImpl(text, getWords());
	}

	@Override
	public IWords getWords() {
		return new WordsImpl(text);
	}
}
