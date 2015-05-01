package cz.compling;

import cz.compling.analysis.analysator.CharacterAnalyser;
import cz.compling.analysis.analysator.CharacterAnalyserImpl;
import cz.compling.analysis.analysator.WordAnalyser;
import cz.compling.analysis.analysator.WordAnalyserImpl;
import cz.compling.analysis.analysator.frequency.character.ICharacterFrequency;
import cz.compling.analysis.analysator.frequency.words.IWordFrequency;
import cz.compling.analysis.analysator.frequency.words.IWords;
import cz.compling.analysis.analysator.poems.*;
import cz.compling.analysis.analysator.poems.aggregation.IAggregation;
import cz.compling.analysis.analysator.poems.alliteration.IAlliteration;
import cz.compling.analysis.analysator.poems.assonance.IAssonance;
import cz.compling.analysis.analysator.poems.denotation.IDenotation;
import cz.compling.analysis.analysator.poems.verses.IVerses;
import cz.compling.text.Text;
import cz.compling.text.TextImpl;
import cz.compling.text.TextModificationRule;
import cz.compling.text.poem.Poem;
import cz.compling.text.poem.PoemImpl;

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
	private final Text text;

	private GeneralAnalysis generalAnalysis;
	private PoemAnalysis poemAnalysis;

	/**
	 * Instances are accessible only via getInstance method
	 */
	private CompLing(Text text) {
		this.text = text;
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

	public void registerRule(TextModificationRule rule) {
		text.registerRule(rule);
	}

	public PoemAnalysis poemAnalysis() {
		if (this.poemAnalysis == null) {
			this.poemAnalysis = new PoemAnalysis();
		}
		return poemAnalysis;
	}

	public GeneralAnalysis generalAnalysis() {
		if (this.generalAnalysis == null) {
			this.generalAnalysis = new GeneralAnalysis();
		}
		return generalAnalysis;
	}

	/**
	 *
	 */
	public class GeneralAnalysis {


		/** Character analyser. Can be null - the instance is created first time {@code CharacterAnalyser} is required */
		private CharacterAnalyser characterAnalyser;

		/** Word analyser. Can be null - the instance is created first time {@code WordFrequencyAnalyser} is required */
		private WordAnalyser wordAnalyser;

		/**
		 * Returns an instance of {@link CharacterAnalyser} which can perform detailed analysis of characters it the text
		 */
		public synchronized ICharacterFrequency characterFrequency() {
			if (this.characterAnalyser == null) {
				this.characterAnalyser = new CharacterAnalyserImpl(text);
			}
			return this.characterAnalyser.getCharacterFrequency();
		}

		/**
		 * Returns an instance of {@code WordFrequencyAnalyser} which can perform detailed analysis of words it the text
		 */
		public synchronized IWordFrequency wordFrequency() {
				if (this.wordAnalyser == null) {
				this.wordAnalyser = new WordAnalyserImpl(text);
			}
			return this.wordAnalyser.getWordFrequency();
		}


		public synchronized IWords getWords() {
			if (this.wordAnalyser == null) {
				this.wordAnalyser = new WordAnalyserImpl(text);
			}
			return wordAnalyser.getWords();
		}
	}

	/**
	 *
	 */
	public class PoemAnalysis {

		private final Poem poem;

		/** Aggregation analyser. Can be null - the instance is created first time {@code AggregationAnalyser} is required */
		private AggregationAnalyser aggregationAnalyser;
		private AlliterationAnalyser alliterationAnalyser;
		private AssonanceAnalyser assonanceAnalyser;
		private DenotationAnalyser denotationAnalyser;
		private VerseAnalyser verseAnalyser;

		public PoemAnalysis() {
			this.poem = new PoemImpl(text);
		}

		public synchronized IVerses verses() {
			if (this.verseAnalyser == null) {
				this.verseAnalyser = new VersesAnalyserImpl(this.getPoem());
			}
			return this.verseAnalyser.getVerses();
		}

		/**
		 * Returns an instance of {@code AggregationAnalyser} which can perform analysis of aggregation in the poem
		 */
		public synchronized IAggregation aggregation() {
			if (this.aggregationAnalyser == null) {
				this.aggregationAnalyser = new AggregationAnalyserImpl(this.getPoem());
			}
			return this.aggregationAnalyser.getAggregation();
		}



		public synchronized IAlliteration alliteration() {
			if (this.alliterationAnalyser == null) {
				this.alliterationAnalyser = new AlliterationAnalyserImpl(this.getPoem());
			}
			return this.alliterationAnalyser.getAlliteration();

		}


		public synchronized IAssonance assonance(String[] vocals) {
			if (this.assonanceAnalyser == null) {
				this.assonanceAnalyser = new AssonanceAnalyserImpl(getPoem());
			}
			return assonanceAnalyser.getAssonance(vocals);
		}

		public synchronized IDenotation denotationAnalysis() {
			if (this.denotationAnalyser == null) {
				this.denotationAnalyser = new DenotationAnalyserImpl(getPoem());
			}
			return denotationAnalyser.getDenotation();
		}

		/** Text as poem. */
		public Poem getPoem() {
			return poem;
		}
	}
}
