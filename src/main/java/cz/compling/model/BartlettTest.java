package cz.compling.model;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.FDistribution;

public class BartlettTest {
	private final TestData testData;
	private final double b;

	public BartlettTest(TestData testData) {
		this.testData = testData;

		this.b = compute();
	}

	public double getB() {
		return b;
	}

	private double compute() {

		int k = testData.getGroupsCount();
		int n = testData.getValuesCount();

		double c = computeC(n, k);
		return computeB(c, n, k);
	}

	private double computeC(int n, int k) {

		double c = ( 1d / ( 3d * (k - 1d) ) );

		double sum = 0d;
		for (int i = 0; i < k; i++) {
			int ni = testData.getValuesCountFor(i);
			ni -= 1;
			sum += (1d / ((double)ni));
		}

		double fraction = 1d / (double)(n-k);

		sum = sum - fraction;

		return 1d +  c * sum;
	}

	private double computeB(double c, int n, int k) {

		double b = 1d / c;

		double sum = 0d;
		for (int i = 0; i < k; i++) {
			int ni = testData.getValuesCountFor(i);
			ni -= 1;
			double varValue = testData.getVarianceFor(i);
			varValue = varValue == 0 ? 0 : Math.log(varValue);
			sum += (ni * varValue);
		}

		double varValue = testData.getAvgVariance();
		varValue = Math.log(varValue);

		int nkDiff = n - k;

		return b * ( ( nkDiff * varValue) - sum );

	}

	public static void main(String[] args) {

		double[][] values = new double[][] {
			{ 0.187, 0.836,  0.704,  0.938, 0.124,  0.678, 0.512 },
			{ 0.449, 0.769,  0.301,  0.045, 0.846,  0.602, 0.373 },
			{ 0.628, 0.193,  0.810,  0.000, 0.855,  0.599, 0.382 },
			{ 0.243, 0.258, -0.276, -0.538, 0.041, -0.192, 0.025 },
			{ 0.134, 0.281,  0.529,  0.305, 0.459,  0.186, 0.411 }
		};


		TestData td = new TestData(values);

		BartlettTest bt = new BartlettTest(td);

		ChiSquaredDistribution chi = new ChiSquaredDistribution(td.getGroupsCount() - 1);
		double critVal = chi.inverseCumulativeProbability(0.95);

		if (bt.getB() < critVal) {
			System.out.println("Nulovou hypotézu o rovnosti rozptylů nezamítáme.");

			AnovaTest anovaTest = new AnovaTest(td);

			final double f = anovaTest.getF();
			final FDistribution fDistribution= new FDistribution(4, 30);
			double criticalValue = fDistribution.inverseCumulativeProbability(0.99);

			if (f > criticalValue) {
				ScheffeTest sch = new ScheffeTest(td, 5, 35, 0.99);

				System.out.println("Významně se od sebe liší tyto dvojice:");
				for (ScheffeTest.Difference d : sch.getDifferentPairs()) {
					System.out.println("\tDvojice [" + d.getRowIndex() + ";" + d.getColumnIndex() + "], hodnota " + sch.getValue(d) + ", kritická hodnota " + d.getCriticalValue());
				}

			}

		} else {
			System.out.println("Nulovou hypotézu o rovnosti rozptylů můeme zamítnout.");
		}
	}
}
