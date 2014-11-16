package cz.compling.model;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.procedure.TIntObjectProcedure;
import org.javatuples.Sextet;

import java.util.ArrayList;
import java.util.List;

public class Aggregations {

	private final TIntObjectMap<Aggregation> aggregations;
	private boolean done;

	public Aggregations() {
		aggregations = new TIntObjectHashMap<Aggregation>();
	}

	public Aggregation getAggregationForBaseLine(int baseLine) {
		Aggregation aggregation = aggregations.get(baseLine);
		if (aggregation == null) {
			if (done) {
				return null;
			}
			aggregation = new Aggregation(baseLine);
			aggregations.put(baseLine, aggregation);
		}
		return aggregation;
	}

	public void clear() {
		aggregations.clear();
		done = false;
	}

	public void done(Aggregation aggregation) {
		if (aggregation.aggregation.isEmpty()) {
			aggregations.remove(aggregation.baseLine);
		}
	}

	public List<Aggregation.LineAggregation> getAggregationsForShift(final int shift, final int maximalShift) {
		final List<Aggregation.LineAggregation> aggList = new ArrayList<Aggregation.LineAggregation>();

		//..aggregations.get(1) → aggregation with the biggest getMaxDistance()
		final int maxBaseLine = aggregations.get(1).getMaxDistance() - maximalShift;
		aggregations.forEachEntry(new TIntObjectProcedure<Aggregation>() {
			@Override
			public boolean execute(int baseLine, Aggregation aggregation) {
				if (baseLine > maxBaseLine) {
					return true;
				}
				Aggregation.LineAggregation line = aggregation.getAggregationFor(shift);
				aggList.add(line);
				return true;
			}
		});

		return aggList;
	}

	public void done() {
		done = true;
	}

	public int getMaxDistance() {
		int max = -1;
		for (Aggregation a : aggregations.valueCollection()) {
			max = Math.max(max, a.getMaxDistance());
		}
		return max;
	}

	public static class Aggregation {

		private final TIntObjectMap<LineAggregation> aggregation;
		private final int baseLine;

		private Aggregation(int baseLine) {
			this.baseLine = baseLine;
			this.aggregation = new TIntObjectHashMap<LineAggregation>();
		}


		public void put(int currentLine, int intersection1, int sizeA1, int length, int intersection2, int sizeB1, int length1) {
			int shift = currentLine - baseLine + 1;
			aggregation.put(shift,
				new LineAggregation(
					shift,
					intersection1,
					sizeA1,
					length,
					intersection2,
					sizeB1,
					length1
				)
			);
		}

		public LineAggregation getAggregationFor(int distance) {
			if (distance < 1 || distance > getMaxDistance()) {
				String msg = String.format("Param distance cannot be less than 1 or be bigger than %d. Was %d", getMaxDistance(), distance);
				throw new IllegalArgumentException(msg);
			}

			return aggregation.get(distance);
		}

		public int getMaxDistance() {
			return aggregation.size();
		}

		@Override
		public String toString() {
			return "Aggregation from line " + baseLine;
		}

		public static class LineAggregation {
			final Sextet<Integer, Integer, Integer, Integer, Integer, Integer> data;
			private final int shift;

			public LineAggregation(int shift, int intersectionA, int sizeA1, int sizeA2, int intersectionB, int sizeB1, int sizeB2) {
				this.shift = shift;
				data =
					new Sextet<Integer, Integer, Integer, Integer, Integer, Integer>(
						intersectionA, sizeA1, sizeA2,
						intersectionB, sizeB1, sizeB2
					);
			}

			public int getSingleSetsIntersectionSize() {
				return data.getValue0();
			}

			public int getSingleSet1Size() {
				return data.getValue1();
			}

			public int getSingleSet2Size() {
				return data.getValue2();
			}

			public int getDoubleSetsIntersectionSize() {
				return data.getValue3();
			}

			public int getDoubleSet1Size() {
				return data.getValue4();
			}

			public int getDoubleSet2Size() {
				return data.getValue5();
			}

			@Override
			public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;

				LineAggregation that = (LineAggregation) o;

				return data.equals(that.data);

			}

			@Override
			public int hashCode() {
				return data.hashCode();
			}

			@Override
			public String toString() {
				return "Aggregation shifted by "+ shift + " line(s) {" +
					"size of single character intersection: " + getSingleSetsIntersectionSize() +
					"; first single character's set size: " + getSingleSet1Size() +
					"; second single character's set size: " + getSingleSet2Size() +
					"; size of double character intersection: " + getDoubleSetsIntersectionSize() +
					"; second double character's set size: " + getDoubleSet1Size() +
					"; second double character's set size: " + getDoubleSet2Size() +
					'}';
			}
		}
	}
}
