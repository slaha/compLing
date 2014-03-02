package cz.compling.model;

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

	final Sextet<Integer, Integer, Integer, Integer, Integer, Integer> data;
	public Aggregation(int intersectionA, int sizeA1, int sizeA2, int intersectionB, int sizeB1, int sizeB2) {
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

		Aggregation that = (Aggregation) o;

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
