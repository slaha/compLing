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
class HrebsHolder {
	/**
	 * Key: number of hreb
	 * value: hreb
	 */
	private final TIntObjectMap<Hreb> hrebs;

	/** no. of current hreb (used for creating new hreb) */
	private int currentHreb;

	HrebsHolder() {
		hrebs = new TIntObjectHashMap<Hreb>();
}

	int createNewHreb() {
		Hreb hreb = new Hreb(++currentHreb);
		return addHreb(hreb);
	}

	int removeHreb(final int number) {
		Hreb hreb = hrebs.remove(number);
		if (hreb == null) {
			throw new HrebNotFoundException("Hreb with number " + number + " does not exist");
		}
		currentHreb--;
		if (currentHreb != number) {
			final TIntObjectMap<Hreb> newHrebs = new TIntObjectHashMap<Hreb>();
			hrebs.forEachEntry(new TIntObjectProcedure<Hreb>() {
				@Override
				public boolean execute(int key, Hreb hreb) {
					if (key > number) {
						final Hreb s = hrebs.remove(key);
						s.setNumber(s.getNumber() - 1);
						newHrebs.put(s.getNumber(), hreb);
					}
					return true;
				}
			});
			System.out.println(newHrebs);
			hrebs.putAll(newHrebs);
		}
		return hreb.onRemove();
	}

	/**
	 * Checks if there is at least one Hreb
	 */
	boolean hasHrebs() {
		return !hrebs.isEmpty();
	}

	/**
	 * @return all Hrebs as sorted array (by Hreb's number)
	 */
	Hreb[] getHrebs() {
		Hreb[] values = hrebs.values(new Hreb[hrebs.size()]);
		Arrays.sort(values, new Comparator<Hreb>() {
			@Override
			public int compare(Hreb o1, Hreb o2) {
				return o1.getNumber() - o2.getNumber();
			}
		});
		return values;
	}

	public Hreb getHreb(int number) {
		Hreb s = hrebs.get(number);
		if (s == null) {
			throw new HrebNotFoundException("Hreb with number " + number + " does not exist");
		}

		return s;
	}

	public boolean containsHreb(int number) {
		return hrebs.containsKey(number);
	}

	int addHreb(Hreb hreb) {
		hrebs.put(hreb.getNumber(), hreb);
		if (hreb.getNumber() > currentHreb) {
			currentHreb = hreb.getNumber();
		}
		return hreb.getNumber();
	}
}
