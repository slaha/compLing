package cz.compling.analysis.analysator;

import cz.compling.analysis.analysator.frequency.words.IWordFrequency;
import cz.compling.analysis.analysator.frequency.words.WordFrequencyImpl;
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
public class WordFrequencyAnalyserImpl implements WordFrequencyAnalyser {

	private final Text text;

	public WordFrequencyAnalyserImpl(Text text) {
		this.text = text;
	}

	@Override
	public IWordFrequency getWordFrequency() {
		return new WordFrequencyImpl(text);
	}
}
