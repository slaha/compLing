package cz.compling.model;

import org.apache.commons.math3.distribution.FDistribution;

import java.util.ArrayList;
import java.util.List;

public class ScheffeTest {


	private final double[][] values;
	private final List<Difference> differentPairs ;

	public ScheffeTest(TestData td, int k, int n, double alpha) {
		final int groupsCount = td.getGroupsCount() - 1;
		this.values = new double[groupsCount][groupsCount];
		this.differentPairs = new ArrayList<Difference>();

		for (int row = 0; row < values.length; row++) {
			for (int column = row; column < values[row].length; column++) {
				double v = td.getAvgFor(row) - td.getAvgFor(column + 1);
				v = Math.abs(v);
				values[row][column] = v;
				double criticalValue = computeCriticalValue(td, k, n, alpha, row, column);
				if (v > criticalValue) {
					differentPairs.add(new Difference(criticalValue, row, column));
				}
			}
		}
	}

	public double[][] getValues() {
		return values;
	}

	public List<Difference> getDifferentPairs() {
		return differentPairs;
	}

	private double computeCriticalValue(TestData td, int k, int n, double alpha, int row, int column) {

		final FDistribution fDistribution= new FDistribution(k - 1, n - k);
		double f = fDistribution.inverseCumulativeProbability(alpha);

		double ni = td.getValuesCountFor(row);
		double nj = td.getValuesCountFor(column);

		double fractionSum = (1d / ni) + (1d / nj);

		fractionSum = fractionSum * (k - 1);

		fractionSum = fractionSum * td.getAvgVariance();

		fractionSum = fractionSum * f;

		return Math.sqrt(fractionSum);
	}

	public double getValue(Difference d) {
		return values[d.getRowIndex()][d.getColumnIndex()];
	}

	public static class Difference  {


		private final double criticalValue;
		private final int rowIndex;
		private final int columnIndex;

		public Difference(Double criticalValue, Integer rowIndex, Integer columnIndex) {

			this.criticalValue = criticalValue;
			this.rowIndex = rowIndex;
			this.columnIndex = columnIndex;
		}

		public double getCriticalValue() {
			return criticalValue;
		}

		public int getRowIndex() {
			return rowIndex;
		}

		public int getColumnIndex() {
			return columnIndex;
		}

	}
}
