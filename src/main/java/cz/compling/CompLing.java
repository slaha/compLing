package cz.compling;

import cz.compling.analysis.analysator.CharacterAnalyser;
import cz.compling.analysis.analysator.impl.CharacterAnalyserImpl;
import cz.compling.text.Text;
import cz.compling.text.TextImpl;

/**
 *
 * <p>This class is the main class of CompLing - the Computational linguistics library</p>
 *
 * <p>It provides access to all functions that this library supports</p>
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd>14.2.14 18:24</dd>
 * </dl>
 */
public class CompLing {

	/** Text to analyse */
	private Text text;

	/** Character analyser. Can be null - the instance is created first time CharacterAnalyser is required */
	private CharacterAnalyser characterAnalyser;

	/**
	 * Instances are accessible only via getInstance method
	 */
	private CompLing(Text text) {
		this.text = text;
	}

	/**
	 * Returns an instance of CharacterAnalyser which can perform detailed analysis of characters it the text
	 */
	public synchronized CharacterAnalyser getCharacterAnalyser() {
		if (this.characterAnalyser == null) {
			this.characterAnalyser = new CharacterAnalyserImpl(this.text);
		}
		return this.characterAnalyser;
	}

	/**
	 * Creates new instance of CompLing library.
	 * @param text text to analyse. Cannot be null
	 *
	 * @throws java.lang.IllegalArgumentException if {@code text} is null
	 */
	public static CompLing getInstance(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Argument text cannot be null");
		}

		return new CompLing(new TextImpl(text));
	}
}
