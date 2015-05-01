package cz.compling.model.denotation;

import gnu.trove.map.TIntIntMap;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.ArrayList;
import java.util.Collection;
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
 * <dd> 17.5.14 17:15</dd>
 * </dl>
 */
public class DenotationMath {
	public double computeCoincidence(int N, int M, int n, int x) {
		final int max = Math.min(M, n);

		double sum = 0d;
		final long denominator = CombinatoricsUtils.binomialCoefficient(N, n);

		for (int i = x; i <= max; i++) {
			long numerator1 = CombinatoricsUtils.binomialCoefficient(M, i);
			long numerator2 = CombinatoricsUtils.binomialCoefficient(N - M, n - i);

			long numerator = numerator1 * numerator2;

			double result = ((double)numerator) / ((double)denominator);
			sum += result;
		}

		return sum;
	}

	public double computeRelativeConnectionRate(int hrebsCount, int componentsCount) {
		if (hrebsCount <= 1) {
			throw new IllegalArgumentException("hrebsCount cannot be <= 1");
		}
		if (componentsCount <= 0) {
			throw new IllegalArgumentException("componentsCount cannot be <= 0");
		}
		double nominator = hrebsCount - componentsCount;
		double denominator = hrebsCount - 1d;
		return nominator / denominator;
	}

	public double computeRelativeCyclomaticNumber(int hrebsCount, int componentsCount, int edgesCount) {
		if (hrebsCount <= 2) {
			throw new IllegalArgumentException("hrebsCount cannot be <= 2");
		}
		double nominator = 2d * ( edgesCount - hrebsCount + componentsCount );
		double denominator = (hrebsCount - 1d) * (hrebsCount - 2d);
		return nominator / denominator;
	}

	public double computeConnotativeConcentration(int hrebsCount, int edgesCount) {
		if (hrebsCount <= 1) {
			throw new IllegalArgumentException("hrebsCount cannot be <= 1");
		}
		double nominator = 2d * edgesCount;
		double denominator = hrebsCount * (hrebsCount - 1d);
		return nominator / denominator;
	}

	public double computeTopicality(Hreb hreb, double cardinalNumber) {
		return ((double)hreb.getWords().size()) / cardinalNumber;
	}

	public double computeTextCompactness(double n, double l) {
		double numerator = 1d - (n / l);
		double denominator = 1d - (1d / l);
		return numerator / denominator;
	}

	/**
	 * @param map Map where key is size of hreb (Hi) and key is count of hrebs with this size (fi)
	 */
	long computeSumHiFi(TIntIntMap map) {
		long sum = 0;
		for (int key : map.keys()) {
			sum += Math.pow(key, 2) * map.get(key);
		}
		return sum;
	}

	/**
	 * @param map Map where key is size of hreb (Hi) and key is count of hrebs with this size (fi)
	 */
	long computeSumN(TIntIntMap map) {
		long n = 0;
		for (int key : map.keys()) {
			n += key * map.get(key);
		}
		return n;
	}

	public double computeTextCentralization(long sumHiFi, long n) {
		return ((double)sumHiFi) / Math.pow(n, 2);
	}

	public double computeMacIntosh(double textCentralization, double n) {
		double numerator = 1d - Math.sqrt(textCentralization);
		double denominator = 1d - (1d / n);

		return numerator / denominator;
	}

	public double getDiffusionFor(List<DenotationWord> words, int hrebNumber, double hrebSize) {
		if (words.isEmpty()) {
			return 0;
		}
		int elementMin = Integer.MAX_VALUE;
		int elementMax = Integer.MIN_VALUE;
		for (int indexOf = 0; indexOf < words.size(); indexOf++) {
			final DenotationWord word = words.get(indexOf);
			if (word.isIgnored()  || word.getDenotationElements().isEmpty()) {
				continue;
			}
			for (DenotationElement element : word.getDenotationElements()) {
				if (element.getHreb().getNumber() != hrebNumber) {
					continue;
				}
				if (element.getNumber() < elementMin) {
					elementMin = element.getNumber();
				}
				if (element.getNumber() > elementMax) {
					elementMax = element.getNumber();
				}
			}
		}
		return ( ((double)elementMax) - ((double)elementMin) ) / hrebSize;
	}

	public double computeNonContinuousIndex(Collection<Hreb> hrebs, CoincidenceProvider coincidenceProvider) {
		double min = -1;
		for (Hreb hreb : hrebs) {
			int hrebNumber = hreb.getNumber();
			for (Coincidence coincidence : coincidenceProvider.getCoincidenceFor(hrebNumber)) {
				final double probability = coincidence.probability;
				if (probability <= 0) {
					continue;
				}
				if (min == -1 || probability < min) {
					min = probability;
				}
			}
		}
		return min;
	}

	public double computeNonIsolationIndex(Collection<Hreb> hrebs, CoincidenceProvider coincidenceProvider) {
		List<Double> minProbabilities = new ArrayList<Double>(hrebs.size());

		for (Hreb hreb : hrebs) {
			double min = Double.MAX_VALUE;
			int hrebNumber = hreb.getNumber();
			for (Coincidence coincidence : coincidenceProvider.getCoincidenceFor(hrebNumber)) {
				final double probability = coincidence.probability;
				if (probability < min) {
					min = probability;
				}
			}
			if (min != Double.MAX_VALUE) {
				minProbabilities.add(min);
			} else {
				return Double.POSITIVE_INFINITY; //..no coincidence with another hreb → not possible to connect the hreb
			}
		}

		if (minProbabilities.isEmpty()) {
			return Double.POSITIVE_INFINITY;
		}
		return Collections.max(minProbabilities);
	}
}
