package cz.compling.model;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.summary.Sum;

import java.util.Arrays;

public class TestData {

	private final double[][] values;

	public TestData(double[][] values) {
		this.values = values;
	}

	public int getGroupsCount() {
		return values.length;
	}

	public int getValuesCount() {
		int count = 0;
		for (double[] row : values) {
			count += row.length;
		}
		return count;
	}

	public int getValuesCountFor(int i) {
		return values[i].length;
	}

	public double getVarianceFor(int i) {
		return new Variance().evaluate(values[i]);
	}

	public double getAvgVariance() {
		double[] variances = new double[values.length];
		for (int i = 0; i < variances.length; i++) {
			variances[i] = getVarianceFor(i);
		}

		return new Mean().evaluate(variances);
	}

	public double getSumFor(int c) {
		return getSumFor(c, 1);
	}

	public double getSumFor(int c, int exponent) {
		final double[] value = Arrays.copyOf(values[c], values[c].length);
		for (int i = 0; i < value.length; i++) {
			value[i] = Math.pow(value[i], exponent);
		}

		return new Sum().evaluate(value);
	}

	public double getAvgFor(int c) {
		return new Mean().evaluate(values[c]);
	}

	public double getValue(int row, int column) {
		return values[row][column];
	}

	public int getGroupSize() {
		return getValuesCountFor(0);
	}
}
