package cz.compling.model.denotation;

import org.apache.commons.math3.util.CombinatoricsUtils;

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

	public double computeRelativeConnectionRate(int spikesCount, int componentsCount) {
		if (spikesCount <= 1) {
			throw new IllegalArgumentException("spikesCount cannot be <= 1");
		}
		if (componentsCount <= 0) {
			throw new IllegalArgumentException("componentsCount cannot be <= 0");
		}
		double nominator = spikesCount - componentsCount;
		double denominator = spikesCount - 1d;
		return nominator / denominator;
	}

	public double computeRelativeCyclomaticNumber(int spikesCount, int componentsCount, int edgesCount) {
		if (spikesCount <= 2) {
			throw new IllegalArgumentException("spikesCount cannot be <= 2");
		}
		double nominator = 2d * ( edgesCount - spikesCount + componentsCount );
		double denominator = (spikesCount - 1d) * (spikesCount - 2d);
		return nominator / denominator;
	}

	public double computeConnotativeConcentration(int spikesCount, int edgesCount) {
		if (spikesCount <= 1) {
			throw new IllegalArgumentException("spikesCount cannot be <= 1");
		}
		double nominator = 2d * edgesCount;
		double denominator = spikesCount * (spikesCount - 1d);
		return nominator / denominator;
	}
}
