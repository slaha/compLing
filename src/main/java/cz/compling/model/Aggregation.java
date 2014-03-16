package cz.compling.model;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.javatuples.Sextet;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 2.3.14 18:18</dd>
 * </dl>
 */
public class Aggregation {

	private final TIntObjectMap<LineAggregation> aggregation;

	public Aggregation() {
		this.aggregation = new TIntObjectHashMap<LineAggregation>();
	}


	public void clear() {
		aggregation.clear();
	}

	public void put(int currentLine, int intersection1, int sizeA1, int length, int intersection2, int sizeB1, int length1) {
		aggregation.put(currentLine,
						new LineAggregation(
							intersection1,
							sizeA1,
							length,
							intersection2,
							sizeB1,
							length1
						)
		);
	}

	public LineAggregation getAggregationFor(int distance)  {
		if (distance < 1 || distance > getMaxDistance()) {
			String msg = String.format("Param distance cannot be less than 1 or be bigger than %d. Was %d", getMaxDistance(), distance);
			throw new IllegalArgumentException(msg);
		}

		return aggregation.get(distance);
	}

	public int getMaxDistance() {
		return aggregation.size();
	}

	public static class LineAggregation {
		final Sextet<Integer, Integer, Integer, Integer, Integer, Integer> data;

		public LineAggregation(int intersectionA, int sizeA1, int sizeA2, int intersectionB, int sizeB1, int sizeB2) {
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
			return "Aggregation {" +
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
