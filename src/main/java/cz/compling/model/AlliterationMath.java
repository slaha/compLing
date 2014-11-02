package cz.compling.model;

import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.Collection;

public class AlliterationMath {

	private final CharacterFrequency characterFrequency;

	public AlliterationMath(CharacterFrequency characterFrequency) {
		this.characterFrequency = characterFrequency;
	}

	public double computeKA(double alpha, double probability) {
		if (probability < 0 || alpha <= probability) {
			return 0;
		}
		return 100d * (alpha - probability);
	}

	public double computeProbability(Alliteration.LineAlliteration lineAlliteration) {
		if (!lineAlliteration.hasAnyAlliteration()) {
			throw new IllegalArgumentException("Cannot compute probability of alliteration. There is no alliteration for verse " + lineAlliteration.getNumberOfVerse());
		}
		final Collection<String> alliterationCharacters = lineAlliteration.getFirstCharactersWithAlliteration();

		double totalProbability = 0d;
		final int n = lineAlliteration.getCountOfWordsInVerse();
		final long nFactorial = CombinatoricsUtils.factorial(n);
		for (AlliterationCombinations.AlliterationCombination combination : getAllCombinations(lineAlliteration)) {

			final long kFactorial = computeK_Factorial(combination, alliterationCharacters, n);

			final double fraction = ((double)nFactorial) / ((double)kFactorial);

			final double probabilities = computeProbabilities(combination, alliterationCharacters, n);

			double probability = fraction * probabilities;

			totalProbability += probability;

		}
		return totalProbability;
	}

	private Iterable<AlliterationCombinations.AlliterationCombination> getAllCombinations(Alliteration.LineAlliteration lineAlliteration) {
		return new AlliterationCombinations(lineAlliteration);
	}

	private long computeK_Factorial(AlliterationCombinations.AlliterationCombination alliterationCombination, Collection<String> alliterationCharacters, int n) {
		long k = 1;
		int allAlliterations = 0;
		for (String character : alliterationCharacters) {
			int kx = alliterationCombination.getAlliterationFor(character);
			k *= CombinatoricsUtils.factorial(kx);
			allAlliterations += kx;
		}
		k *= CombinatoricsUtils.factorial(n - allAlliterations);
		return k;
	}

	private double computeProbabilities(AlliterationCombinations.AlliterationCombination combination, Collection<String> alliterationCharacters, int n) {
		double pMultiplied = 1d;
		double pSum = 0d;
		int kSum = 0;

		final int allCharactersCount = characterFrequency.getCharactersCount();

		for (String character : alliterationCharacters) {
			final int frequencyFor = characterFrequency.getFrequencyFor(character);

			final double probabilityX = ((double)frequencyFor) / ((double)allCharactersCount);
			pSum += probabilityX;

			int kx = combination.getAlliterationFor(character);
			kSum += kx;

			final double probabilityPowK = Math.pow(probabilityX, kx);
			pMultiplied *= probabilityPowK;

		}

		double base = 1d - pSum;
		double exponent = n - kSum;

		double pMinusMultiplied = Math.pow(base, exponent);

		return pMultiplied * pMinusMultiplied;
	}
}
