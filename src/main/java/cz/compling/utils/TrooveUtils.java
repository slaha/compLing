package cz.compling.utils;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TObjectIntMap;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 1.3.14 12:25</dd>
 * </dl>
 */
public class TrooveUtils {


	public enum SortOrder {
		ASCENDING,
		DESCENDING;
	}

	public static class Lists<K extends Comparable<K>, PAIR extends Pair<K, Integer>> {

		private final List<PAIR> list;

		public Lists() {
			this.list = new ArrayList<PAIR>();
		}

		public void clear() {
			list.clear();
		}

		/**
		 * Converts frequency table to list
		 */
		public Lists<K, PAIR> toList(TObjectIntMap<K> map) {

			for (K key : map.keySet()) {
				list.add(
					(PAIR) new Pair<K, Integer>(key, map.get(key))
				);
			}

			return this;
		}

		public Lists<K, PAIR> toList(TIntIntMap map) {

			for (Object key : map.keys()) {
				list.add(
					//..these casts are really ugly but I do not know how to solve it without them
					(PAIR) new Pair<K, Integer>((K)key, map.get((Integer)key))
				);
			}

			return this;
		}

		public Lists<K, PAIR> sort(SortOrder order) {
			if (order == null) {
				return this;
			}
			Comparator<PAIR> comparator = createComparator(order);
			Collections.sort(list, comparator);

			return this;
		}

		public List<PAIR> getList() {
			return new ArrayList<PAIR>(list);
		}

		/**
		 * Creates comparator for sorting list from {@code toList()} method.
		 * If frequency of two characters is the same it alphabet order is used (
		 *
		 * @param order order of sorting
		 *
		 * @return comparator for sorting in desired order
		 */
		private Comparator<PAIR> createComparator(final SortOrder order) {

			return new Comparator<PAIR>() {
				@Override
				public int compare(PAIR o1, PAIR o2) {
					int diff =  o2.getValue1() - o1.getValue1();

					if (diff == 0) {
						//..frequency is the same. Compare characters
						diff = o2.getValue0().compareTo(o1.getValue0());
					}

					return order == SortOrder.DESCENDING ? diff : -diff;
				}
			};
		}
	}
}
