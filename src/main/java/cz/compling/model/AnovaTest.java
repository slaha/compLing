package cz.compling.model;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.summary.Sum;

public class AnovaTest {

	public static final int Ni = 0;
	public static final int SUM_Xi = 1;
	public static final int AVG_Xi = 2;
	public static final int SUM_Xi_SQUARE = 3;
	public static final int VARIANCE = 4;
	public static final int VARIANCE_LN = 5;

	public static final int[] SUM_COLUMNS = new int[] { SUM_Xi, SUM_Xi_SQUARE, VARIANCE_LN };
	public static final int[] AVG_COLUMNS = new int[] { AVG_Xi };

	///////////////////////////
	private final TestData td;

	private final double[][] anovaData;
	private final int lastValueRowIndex;
	private final int sumRowIndex;
	private final int avgRowIndex;

	private final double sa;
	private final double st;
	private final double se;

	private final double dfSa;
	private final double dfSe;

	private final double varSa;
	private final double varSe;

	private final double f;

	public AnovaTest(TestData td) {
		this.td = td;

		int dataRowsCount = td.getGroupsCount() + 2;
		anovaData = new double[dataRowsCount][6];
		avgRowIndex = dataRowsCount - 1;
		sumRowIndex = avgRowIndex - 1;
		lastValueRowIndex = sumRowIndex - 1;

		computeTableData();
		sa = computeSa();
		st = computeSt();
		se = computeSe();

		final int k = td.getGroupsCount();
		dfSa = k - 1;
		int N = td.getGroupSize();
		dfSe = (N - 1) * k;

		varSa = sa / dfSa;
		varSe = se / dfSe;

		f = varSa / varSe;
	}

	private double computeSa() {
		final int k = td.getGroupsCount();

		final double averageXi = anovaData[avgRowIndex][AVG_Xi];

		double sum = 0;
		for (int row = 0; row < k; row++) {
			int ni = td.getValuesCountFor(row);
			double value = anovaData[row][AVG_Xi];
			value = value - averageXi;
			value = Math.pow(value, 2);
			value = ni * value;
			sum += value;
		}
		return sum;
	}

	private double computeSt() {
		final double sumXij = anovaData[sumRowIndex][SUM_Xi_SQUARE];
		double sumX = anovaData[sumRowIndex][SUM_Xi];
		sumX = Math.pow(sumX, 2);

		int n = td.getValuesCount();

		return sumXij - sumX / n;
	}

	private double computeSe() {
		final int k = td.getGroupsCount();

		double sum = 0;
		for (int i = 0; i < k; i++) {
			int ni = td.getValuesCountFor(i);
			double xi = anovaData[i][AVG_Xi];
			for (int j = 0; j < ni; j++) {
				double value = td.getValue(i, j);
				value = value - xi;
				value = Math.pow(value, 2);
				sum += value;
			}
		}
		return sum;
	}

	private void computeTableData() {
		for (int r = 0; r <= lastValueRowIndex; r++) {
			for (int c = 0; c < anovaData[r].length; c++) {

				double value;
				switch (c) {
					case Ni:
						value = td.getValuesCountFor(r);
						break;
					case SUM_Xi:
						value = td.getSumFor(r);
						break;
					case AVG_Xi:
						value = td.getAvgFor(r);
						break;
					case SUM_Xi_SQUARE:
						value = td.getSumFor(r, 2);
						break;
					case VARIANCE:
						value = td.getVarianceFor(r);
						break;
					case VARIANCE_LN:
						double variance = anovaData[r][VARIANCE];
						value = Math.log(variance);
						break;
					default:
						throw new IllegalStateException("Unknown column number " + c);
				}
				anovaData[r][c] = value;
			}
		}

		Sum sum = new Sum();
		for (int sumColumn : SUM_COLUMNS) {
			sum.clear();
			for (int row = 0; row <= lastValueRowIndex; row++) {
				sum.increment(anovaData[row][sumColumn]);
			}
			anovaData[sumRowIndex][sumColumn] = sum.getResult();
		}
		sum = null;

		Mean mean = new Mean();
		for (int avgColumn : AVG_COLUMNS) {
			mean.clear();
			for (int row = 0; row  <= lastValueRowIndex; row++) {
				mean.increment(anovaData[row][avgColumn]);
			}
			anovaData[avgRowIndex][avgColumn] = mean.getResult();
		}
	}

	public double getSumSquareSa() {
		return sa;
	}

	public double getTotalSumSquareSt() {
		return st;
	}

	public double getSumSquareSe() {
		return se;
	}

	public double getSaDegreesOfFreedom() {
		return dfSa;
	}

	public double getSeDegreesOfFreedom() {
		return dfSe;
	}

	public double getSaVariance() {
		return varSa;
	}

	public double getSeVariance() {
		return varSe;
	}

	public double getF() {
		return f;
	}

	public double[][] getGroupedPartialResults() {
		double[][] data = new double[lastValueRowIndex + 1][6];
		for (int i = 0; i < data.length; i++) {
			System.arraycopy(anovaData[i], 0, data[i], 0, anovaData[i].length);
		}
		return data;
	}
}
