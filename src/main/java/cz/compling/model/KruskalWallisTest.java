package cz.compling.model;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.summary.Sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KruskalWallisTest {

	final double[][] rankedValues;
	private final double q;
	private final int n;

	public KruskalWallisTest(TestData testData) {

		List<SortValue> values = new ArrayList<SortValue>();

		final int totalRows = testData.getGroupsCount();

		rankedValues = new double[totalRows][];

		for (int row = 0; row < totalRows; row++) {

			final int totalColumns = testData.getValuesCountFor(row);
			rankedValues[row] = new double[totalColumns + 3];

			for (int column = 0; column < totalColumns; column++) {
				double val = testData.getValue(row, column);
				values.add(new SortValue(row, column, val));
			}
		}

		Collections.sort(values);
		doRanks(values);
		doSums();
		this.n = computeN();
		this.q = computeQ();
	}

	public double[][] getRankedValues() {
		return rankedValues;
	}

	public double getQ() {
		return q;
	}

	public int getK() {
		return rankedValues.length - 1;
	}

	private double computeQ() {
		double fraction = 12d / (n * ( n + 1) );

		double q = 0;
		for (final double[] r : rankedValues) {
			double ti = r[r.length - 3];
			ti = Math.pow(ti, 2);

			int ni = (int) r[r.length - 2];

			double fr = ti / ni;

			q += fr;
		}

		double minus = 3 * ( n + 1);
		return fraction * q - minus;

	}

	private int computeN() {
		int n = 0;
		for (double[] r: rankedValues) {
			n += r[r.length - 2];
		}
		return n;
	}

	public int getN() {
		return n;
	}

	private void doSums() {
		Sum sum = new Sum();
		Mean mean = new Mean();
		for (double[] r : rankedValues) {
			double[] row = Arrays.copyOf(r, r.length - 3);
			r[r.length - 3] = sum.evaluate(row);
			r[r.length - 2] = row.length;
			r[r.length - 1] = mean.evaluate(row);
		}
	}

	private void doRanks(List<SortValue> values) {
		int index = 0;
		SortValue currentValue = null;

		int currentRank = 0;
		List<SortValue> sameRankValues = new ArrayList<SortValue>();
		while (index < values.size()) {
			currentRank++;
			final SortValue value = values.get(index++);
			if (currentValue != null) {
				int cmp = value.compareTo(currentValue);

				if (cmp != 0) {
					computeRank(sameRankValues, currentRank - 1);
					sameRankValues.clear();
				}
				sameRankValues.add(value);
			} else {
				sameRankValues.add(value);
			}

			currentValue = value;
		}

		computeRank(sameRankValues, currentRank); //..for last group
	}
	private void computeRank(List<SortValue> sameRankValues, int currentRank) {

		double rankSum = 0;
		for (int i = 0; i < sameRankValues.size(); i++) {
			rankSum += currentRank - i;

		}
		double rank = rankSum / ((double) sameRankValues.size());

		for (SortValue v : sameRankValues) {
			rankedValues[v.row][v.column] = rank;
		}

	}

	private class SortValue implements Comparable<SortValue> {

		final int row, column;

		final double value;

		public SortValue(int row, int column, double value) {
			this.row = row;
			this.column = column;
			this.value = value;
		}

		@Override
		public int compareTo(SortValue o) {
			return Double.compare(value, o.value);
		}
	}

	public static void main(String[] args) {

		double[][] values = new double[][] {
			{ 55, 54, 58, 61, 52, 60, 53, 65 },
			{ 52, 50, 51, 51, 49 },
			{ 47, 53, 49, 50, 46, 48, 50 } };
		TestData td = new TestData(values);
		KruskalWallisTest kw = new KruskalWallisTest(td);
	}
}
