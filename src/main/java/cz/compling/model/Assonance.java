package cz.compling.model;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 17.3.14 7:17</dd>
 * </dl>
 */
public class Assonance {

	private final TIntIntMap assonance;

	private int maxStep;

	public Assonance() {
		assonance = new TIntIntHashMap();
	}

	public void put(int step, int i) {
		assonance.put(step, i);
		maxStep = Math.max(maxStep, step);
	}

	public int getAssonanceFor(int step) {
		if (step < 1 || step > getMaxStep()) {

			String msg = String.format("Param step must be in interval <1;%d>. Was %d", getMaxStep(), step);
			throw new IllegalArgumentException(msg);
		}

		return assonance.get(step);
	}

	public int getMaxStep() {
		return maxStep;
	}
}
