package cz.compling.model;

import java.util.ArrayList;
import java.util.List;

public class AggregationMath {
	private final Aggregation[] aggregation;
	private final LeastSquares leastSquares;

	public AggregationMath(Aggregation... aggregation) {
		this.aggregation = aggregation;
		this.leastSquares = new LeastSquares();
	}

	private double computeSimilarity(Aggregation agg, int shift) {
		final Aggregation.LineAggregation aggregation = agg.getAggregationFor(shift);

		double aNominator = Math.pow(aggregation.getSingleSetsIntersectionSize(), 2);
		double aDenominator = aggregation.getSingleSet1Size() * aggregation.getSingleSet2Size();
		double a = aNominator / aDenominator;

		double bNominator = Math.pow(aggregation.getDoubleSetsIntersectionSize(), 2);
		double bDenominator = aggregation.getDoubleSet1Size() * aggregation.getDoubleSet2Size();
		double b = bNominator / bDenominator;

		double sum = a + b;
		return 100 * sum;
	}

	public double computeAvgSimilarity(int shift) {
		double sum = 0;
		for (Aggregation a : aggregation) {
			sum += computeSimilarity(a, shift);
		}
		float size = aggregation.length;
		return sum / size;
	}

	public double computeApproximatedSimilarity(int n, int shift) {
		return leastSquares.compute(n).getExpected(shift);
	}

	public double computeCoefficientD(int n) {
		return leastSquares.computeD(n);
	}

	private class LeastSquares {

		private double A;
		private double B;
		private final List<Double> averagesSi;
		private final List<Double> expectedSi;
		private int lastN;
		private double D = -1;

		private LeastSquares() {
			averagesSi = new ArrayList<Double>();
			expectedSi = new ArrayList<Double>();
		}

		private LeastSquares compute(int n) {

			if (lastN != n) {
				averagesSi.clear();
				expectedSi.clear();
				D = -1;

				for (int i = 1; i <= n; i++) {
					averagesSi.add(computeAvgSimilarity(i));
				}

				double lnLi = computeLnLi(n);
				double lnLi2 = computeLnLi2(n);
				double lnSi = computeLnSi();
				double lnLiLnSi = computeLnLiLnSi(n);
				double lnLiPow2 = Math.pow(lnLi, 2);
				double B = ((n * lnLiLnSi) - (lnLi * lnSi)) / ((n * lnLi2) - (lnLiPow2));
				double A = ((lnLi2 * lnSi) - (lnLi * lnLiLnSi)) / ((n * lnLi2) - (lnLiPow2));
				this.A = Math.exp(A);
				this.B = -B;

				computeExpected(n);

				lastN = n;
			}

			return this;
		}

		private void computeExpected(int n) {
			for (int i = 1; i <= n; i++) {
				expectedSi.add(A * Math.pow(i, (-B)));
			}
		}

		private double computeD(int n) {
			if (averagesSi.isEmpty()) {
				compute(n);
			}
			if (D < 0) {
				double nominator = 0;
				double denominator = 0;
				double sumSi = 0;

				for (int i = 0; i < n; i++) {
					double avgSi = averagesSi.get(i);
					double expSi = expectedSi.get(i);
					double d = Math.pow((avgSi - expSi), 2);
					nominator += d;
					sumSi += avgSi;
					denominator += Math.pow(avgSi, 2);
				}
				sumSi = Math.pow(sumSi, 2);
				sumSi /= averagesSi.size();
				denominator -= sumSi;
				this.D = 1 - (nominator / denominator);
			}
			return D;
		}

		private double computeLnLi(int n) {
			double sum = 0;
			for (int i  = 1; i <= n; i++) {
				sum += Math.log(i);
			}
			return sum;
		}

		private double computeLnLi2(int n) {
			double sum = 0;
			for (int i = 1; i <= n; i++) {
				double log = Math.log(i);
				sum += Math.pow(log, 2);
			}
			return sum;
		}

		private double computeLnSi() {
			double sum = 0;
			for (double si : averagesSi) {
				sum += Math.log(si);
			}
			return sum;
		}

		private double computeLnLiLnSi(int n) {
			double sum = 0;
			for (int i = 1; i <= n; i++) {
				double part = Math.log(i);
				part *= Math.log(averagesSi.get(i - 1));
				sum += part;
			}
			return sum;
		}

		public double getExpected(int shift) {
			return expectedSi.get(shift - 1);
		}
	}
}
