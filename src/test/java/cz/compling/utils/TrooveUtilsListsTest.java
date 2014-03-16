package cz.compling.utils;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.procedure.TIntObjectProcedure;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * Troove utilities tests
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 16.3.14 15:54</dd>
 * </dl>
 */
public class TrooveUtilsListsTest {

	static TrooveUtils.Lists lists;
	static TIntIntMap intIntMap;
	static TObjectIntMap<String> objectIntMap;

	static final TIntObjectMap<String> VALUES = new TIntObjectHashMap<String>();
	static final int[] RANDOM_NUMBERS = new int[] {
		(int) (Math.random() * 1000),
		(int) (Math.random() * 1000),
		(int) (Math.random() * 1000),
		(int) (Math.random() * 1000),
		(int) (Math.random() * 1000),
		(int) (Math.random() * 1000),
		(int) (Math.random() * 1000),
		(int) (Math.random() * 1000),
		(int) (Math.random() * 1000),
		(int) (Math.random() * 1000)
	};

	static {
		//..some dummy data
		VALUES.put(50, "hi");
		VALUES.put(10, "james");
		VALUES.put(11, "car");
		VALUES.put(88, "word");
		VALUES.put(100, "world");
		VALUES.put(231, "brilliant");
		VALUES.put(88, "lots of people");
		VALUES.put(64, "this is a value");
		VALUES.put(196, "one two three");
		VALUES.put(248, "finish");
	}

	@Before
	public void setUp() throws Exception {
		lists = new TrooveUtils.Lists();
		intIntMap = new TIntIntHashMap();
		objectIntMap = new TObjectIntHashMap<String>();
		VALUES.forEachEntry(new TIntObjectProcedure<String>() {
			int index = 0;
			@Override
			public boolean execute(int i, String o) {
				intIntMap.put(i, RANDOM_NUMBERS[index]);
				objectIntMap.put(o, RANDOM_NUMBERS[index]);
				index++;
				return true;
			}
		});
	}

	@Test
	public void testToListIntInt() throws Exception {
		TrooveUtils.Lists intIntLists = lists.toList(intIntMap);

		//..put pairs to the lists with values as in objectIntMap
		List<Pair<Integer, Integer>> intIntDefaultList = new ArrayList<Pair<Integer, Integer>>();
		int index = 0;
		for (int key : VALUES.keys()) {
			intIntDefaultList.add(new Pair<Integer, Integer>(key, RANDOM_NUMBERS[index]));
			index++;
		}
		Assert.assertEquals(intIntDefaultList, intIntLists.getList());
	}

	@Test
	public void testToListObjectInt() throws Exception {
		TrooveUtils.Lists<String, Pair<String, Integer>> objectIntLists = lists.toList(objectIntMap);

		//..put pairs to the lists with values as in intIntMap
		List<Pair<String, Integer>> objectIntDefaultList = new ArrayList<Pair<String, Integer>>();
		int index = 0;
		for (String value : VALUES.valueCollection()) {
			objectIntDefaultList.add(new Pair<String, Integer>(value, RANDOM_NUMBERS[index]));
			index++;
		}
		List<Pair<String, Integer>> list = new ArrayList<Pair<String, Integer>>(objectIntLists.getList());
		list.removeAll(objectIntDefaultList);
		Assert.assertTrue(list.isEmpty());

		list = new ArrayList<Pair<String, Integer>>(objectIntLists.getList());
		objectIntDefaultList.removeAll(list);
		Assert.assertTrue(objectIntDefaultList.isEmpty());
	}

	@Test
	public void testSort() throws Exception {
		List<Pair<Integer, Integer>> list1 = lists.toList(intIntMap).sort(TrooveUtils.SortOrder.ASCENDING).getList();
		lists.clear();
		List<Pair<Integer, Integer>> list2 = lists.toList(intIntMap).sort(TrooveUtils.SortOrder.DESCENDING).getList();



		int lastValue = -1;
		for (Pair<Integer, Integer> pair : list1) {
			if (lastValue < 0) {
				lastValue = pair.getValue1();
			} else {
				int current = pair.getValue1();
				boolean bigger = current >= lastValue;
				Assert.assertTrue("Wrong order in list1 - current value: " + current + "; last value: " + lastValue, bigger);
				lastValue = current;
			}
		}

		lastValue = -1;
		for (Pair<Integer, Integer> pair : list2) {
			if (lastValue < 0) {
				lastValue = pair.getValue1();
			} else {
				int current = pair.getValue1();
				boolean bigger = current <= lastValue;
				Assert.assertTrue("Wrong order in list2 - current value: " + current + "; last value: " + lastValue, bigger);
				lastValue = current;
			}
		}

		Collections.reverse(list2);

		Assert.assertTrue(list1.equals(list2));
	}

	@Test
	public void testGetList() throws Exception {
		//..tested in other methods
	}
}
