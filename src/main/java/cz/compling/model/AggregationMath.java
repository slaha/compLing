package cz.compling.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AggregationMath {

	private final int maximalShift;
	private final Aggregations[] aggregations;
	private final LeastSquares leastSquares;

	public AggregationMath(int maximalShift, Aggregations... aggregations) {
		this.maximalShift = maximalShift;
		this.aggregations = aggregations;
		this.leastSquares = new LeastSquares();
	}

	private double computeSimilarity(Aggregations.Aggregation.LineAggregation aggregation, int shift) {

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
		int size = 0;
		for (Aggregations aggs : aggregations) {
			//..aggregations for each text
			for (Aggregations.Aggregation.LineAggregation a : aggs.getAggregationsForShift(shift, maximalShift)) {
				sum += computeSimilarity(a, shift);
				size++;
			}
		}
		return (sum / (double) size);
	}

	public double computeApproximatedSimilarity(int n, int shift) {
		return leastSquares.compute(n).getExpected(shift);
	}

	public double computeCoefficientD(int n) {
		return leastSquares.computeD(n);
	}

	public double getApproxA(int n) {
		return leastSquares.getApproxA(n);
	}

	public double getApproxB(int n) {
		return leastSquares.getApproxB(n);
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

				double avgS = computeAvgS(n);
				for (int i = 0; i < n; i++) {
					double avgSi = averagesSi.get(i);
					double expSi = expectedSi.get(i);
					double nn = Math.pow((avgSi - expSi), 2);
					nominator += nn;

					double dd = Math.pow((avgS - avgSi), 2);
					denominator += dd;
				}
				this.D = 1 - (nominator / denominator);
			}
			return D;
		}

		double getApproxA(int n) {
			if (lastN != n) {
				compute(n);
			}
			return A;
		}

		double getApproxB(int n) {
			if (lastN != n) {
				compute(n);
			}
			return B;
		}

		private double computeAvgS(int n) {
			double s = 0;
			for (int i = 0; i < n; i++) {
				s += averagesSi.get(i);
			}
			return s / (double) n;
		}

		private double computeLnLi(int n) {
			double sum = 0;
			for (int i = 1; i <= n; i++) {
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

	public static void main(String[] args) {
		final String base = "přece stále žal mne soužil pro lenoru žal a trud".replaceAll(" ", "");
		final char[] baseline = base.toCharArray();
		final String shift = "děl jsem pane nebo paní promiňte jsem uleknut".replaceAll(" ", "");
		final char[] shifted = shift.toCharArray();

		final String[] baseline2 = new String[baseline.length - 1];
		for (int i = 0; i < baseline2.length; i++) {
			baseline2[i] = "" + baseline[i] + baseline[i + 1];
		}
		final String[] shifted2 = new String[shifted.length - 1];
		for (int i = 0; i < shifted2.length; i++) {
			shifted2[i] = "" + shifted[i] + shifted[i + 1];
		}

		System.out.println("first single character's set size: " + baseline.length + "\n" +
			"second single character's set size: " + shifted.length);

		Arrays.sort(baseline);
		Arrays.sort(shifted);

		int size = 0, i1 = 0, i2 = 0;
		try {
			while (true) {
				char c1 = baseline[i1];
				char c2 = shifted[i2];

				if (c1 == c2) {
					i1++;
					i2++;
					size++;
				} else if (c1 < c2) {
					i1++;
				} else {
					i2++;
				}
			}
		} catch (ArrayIndexOutOfBoundsException ignored) {
		}

		System.out.println("size of single character intersection: " + size);

		System.out.println("double char baseline size: " + baseline2.length + "\n" +
			"double char shifted size: " + shifted2.length);

		Arrays.sort(baseline2);
		Arrays.sort(shifted2);

		size = 0;
		i1 = 0;
		i2 = 0;
		try {
			while (true) {
				String c1 = baseline2[i1];
				String c2 = shifted2[i2];

				int cmp = c1.compareTo(c2);

				if (cmp == 0) {
					i1++;
					i2++;
					size++;
				} else if (cmp < 0) {
					i1++;
				} else {
					i2++;
				}
			}
		} catch (ArrayIndexOutOfBoundsException ignored) {
		}

		System.out.println("size of double character intersection: " + size);


	}

	private static final List<Double> averagesSi;
	private static final List<Double> expectedSi;

	static {
		averagesSi = Arrays.asList(36.06, 34.60, 34.44, 34.77, 33.78, 33.84, 34.01, 33.76, 32.21, 32.01);
		expectedSi = Arrays.asList(35.87, 35.06, 34.60, 34.27, 34.02, 33.82, 33.65, 33.50, 33.37, 33.26);
	}
}