package cz.compling;

import cz.compling.analysis.analysator.AggregationAnalyser;
import cz.compling.analysis.analysator.CharacterAnalyser;
import cz.compling.analysis.analysator.WordFrequencyAnalyser;
import cz.compling.analysis.analysator.impl.AggregationAnalyserImpl;
import cz.compling.analysis.analysator.impl.CharacterAnalyserImpl;
import cz.compling.analysis.analysator.impl.WordFrequencyAnalyserImpl;
import cz.compling.poem.Poem;
import cz.compling.poem.PoemImpl;
import cz.compling.rules.Rule;
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

	/** Text as poem. Lazy loaded when it is required */
	private Poem poem;

	/** Character analyser. Can be null - the instance is created first time {@code CharacterAnalyser} is required */
	private CharacterAnalyser characterAnalyser;

	/** Word analyser. Can be null - the instance is created first time {@code WordFrequencyAnalyser} is required */
	private WordFrequencyAnalyser wordFrequencyAnalyser;

	/** Aggregation analyser. Can be null - the instance is created first time {@code AggregationAnalyser} is required */
	private AggregationAnalyser aggregationAnalyser;


	/**
	 * Instances are accessible only via getInstance method
	 */
	private CompLing(Text text) {
		this.text = text;
	}

	/**
	 * Returns an instance of {@code AggregationAnalyser} which can perform analysis of aggregation in the poem
	 */
	public synchronized AggregationAnalyser getAggregationAnalyser() {
		if (this.aggregationAnalyser == null) {
			if (this.poem == null) {
				this.poem = new PoemImpl(text);
			}
			this.aggregationAnalyser = new AggregationAnalyserImpl(this.poem);
		}
		return this.aggregationAnalyser;
	}

	/**
	 * Returns an instance of {@code CharacterAnalyser} which can perform detailed analysis of characters it the text
	 */
	public synchronized CharacterAnalyser getCharacterAnalyser() {
		if (this.characterAnalyser == null) {
			this.characterAnalyser = new CharacterAnalyserImpl(this.text);
		}
		return this.characterAnalyser;
	}

	/**
	 * Returns an instance of {@code WordFrequencyAnalyser} which can perform detailed analysis of words it the text
	 */
	public synchronized WordFrequencyAnalyser getWordFrequencyAnalyser() {
		if (this.wordFrequencyAnalyser == null) {
			this.wordFrequencyAnalyser = new WordFrequencyAnalyserImpl(this.text);
		}
		return this.wordFrequencyAnalyser;
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

	public void registerRule(Rule rule) {
		text.registerRule(rule);
	}
}
