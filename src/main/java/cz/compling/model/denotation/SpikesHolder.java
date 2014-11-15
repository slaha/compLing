package cz.compling.model.denotation;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.procedure.TIntObjectProcedure;

import java.util.Arrays;
import java.util.Comparator;

/**
*
* TODO
*
* <dl>
* <dt>Created by:</dt>
* <dd>slaha</dd>
* <dt>On:</dt>
* <dd> 1.5.14 17:46</dd>
* </dl>
*/
class SpikesHolder {
	/**
	 * Key: number of spike
	 * value: spike
	 */
	private final TIntObjectMap<Spike> spikes;

	/** no. of current spike (used for creating new spike) */
	private int currentSpike;

	SpikesHolder() {
		spikes = new TIntObjectHashMap<Spike>();
}

	int createNewSpike() {
		Spike spike = new Spike(++currentSpike);
		return addSpike(spike);
	}

	int removeSpike(final int number) {
		Spike spike = spikes.remove(number);
		if (spike == null) {
			throw new SpikeNotFoundException("Spike with number " + number + " does not exist");
		}
		currentSpike--;
		if (currentSpike != number) {
			final TIntObjectMap<Spike> newSpikes = new TIntObjectHashMap<Spike>();
			spikes.forEachEntry(new TIntObjectProcedure<Spike>() {
				@Override
				public boolean execute(int key, Spike spike) {
					if (key > number) {
						final Spike s = spikes.remove(key);
						s.setNumber(s.getNumber() - 1);
						newSpikes.put(s.getNumber(), spike);
					}
					return true;
				}
			});
			System.out.println(newSpikes);
			spikes.putAll(newSpikes);
		}
		return spike.onRemove();
	}

	/**
	 * Checks if there is at least one Spike
	 */
	boolean hasSpikes() {
		return !spikes.isEmpty();
	}

	/**
	 * @return all Spikes as sorted array (by Spike's number)
	 */
	Spike[] getSpikes() {
		Spike[] values = spikes.values(new Spike[spikes.size()]);
		Arrays.sort(values, new Comparator<Spike>() {
			@Override
			public int compare(Spike o1, Spike o2) {
				return o1.getNumber() - o2.getNumber();
			}
		});
		return values;
	}

	public Spike getSpike(int number) {
		Spike s = spikes.get(number);
		if (s == null) {
			throw new SpikeNotFoundException("Spike with number " + number + " does not exist");
		}

		return s;
	}

	public boolean containsSpike(int number) {
		return spikes.containsKey(number);
	}

	int addSpike(Spike spike) {
		spikes.put(spike.getNumber(), spike);
		if (spike.getNumber() > currentSpike) {
			currentSpike = spike.getNumber();
		}
		return spike.getNumber();
	}
}
