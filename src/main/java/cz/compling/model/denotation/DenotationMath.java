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
}
