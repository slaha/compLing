package cz.compling.model.denotation;

import cz.compling.model.Alliteration;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.*;

class AlliterationCombinations implements Iterable<AlliterationCombinations.AlliterationCombination> {
	final Set<AlliterationCombination> combinations = new LinkedHashSet<AlliterationCombination>();
	final int MAX;
	private final String[] alliterationWords;

	public AlliterationCombinations(Alliteration.LineAlliteration lineAlliteration) {
		MAX = lineAlliteration.getCountOfWordsInVerse();
		final Collection<String> charactersWithAlliteration = lineAlliteration.getFirstCharactersWithAlliteration();

		alliterationWords = new String[charactersWithAlliteration.size()];
		final int[] alliterations = new int[charactersWithAlliteration.size() + 1];

		int index = 0;
		int sum = 0;
		List<String> ccx = new ArrayList<String>(charactersWithAlliteration);
		Collections.sort(ccx);
		for (String s : ccx) {
			final int alliteration = lineAlliteration.getAlliterationFor(s);
			alliterationWords[index] = s;
			alliterations[index] = alliteration;
			sum += alliteration;
			index++;
		}
		alliterations[index] = MAX - sum;


		createCombinations(alliterations);
	}

	private void createCombinations(final int[] alliterations) {
		if (alliterations == null) {
			return;
		}

		//..creates not-combined values
		final int n = alliterations[alliterations.length - 1];
		for (int i = 0; i < alliterations.length - 1; i++) {

			int[] copy = copy(alliterations);
			while (copy[copy.length - 1] >= 0) {
				AlliterationCombination c = new AlliterationCombination(alliterationWords, copy);
				combinations.add(c);
				copy[i]++;
				copy[copy.length - 1]--;
			}
		}

		int[] numbersToChange = new int[alliterations.length - 1];
		Set<ArrayCombination> combinations = new HashSet<ArrayCombination>();
		createCombinedCombinations(combinations, 0, numbersToChange, n);

		for (ArrayCombination c : combinations) {
			int[] copy = copy(alliterations);
			int sum = 0;
			for (int i = 0; i < c.arr.length; i++) {
				copy[i] += c.arr[i];
				sum += copy[i];
			}
			copy[copy.length - 1] = MAX - sum;
			this.combinations.add(new AlliterationCombination(alliterationWords, copy));
		}
	}

	private int[] copy(int[] array) {
		int[] copy = new int[array.length];
		System.arraycopy(array, 0, copy, 0, copy.length);
		return copy;
	}

	/**
	 * This method should create all alliteration combinations which are required for computing probability
	 */
	private void createCombinedCombinations(Set<ArrayCombination> l, int index, int[] numbersToChange, final int n) {
		if (n == 0 || index >= numbersToChange.length) {
			return;
		}

		int[] local = copy(numbersToChange);
		local[index]++;
		if (n - 1 < 0) {
			return;
		}
		l.add(new ArrayCombination(local)); //..new combination

		int i = index + 1;
		while (i < local.length) {
			createCombinedCombinations(l, i++, local, n - 1); //..create combination based on 'local' -- increment next value
		}

		int nn = n - 1;
		while (nn > 0) {
			createCombinedCombinations(l, index, local, nn--); //..create combination by incrementing the same value again
		}

		while (index < local.length) {
			createCombinedCombinations(l, ++index, numbersToChange, n); //..move to next value and start again
		}
	}

	@Override
	public Iterator<AlliterationCombination> iterator() {
		return combinations.iterator();
	}

	@Override
	public String toString() {
		String s = "";
		int row = 1;
		for (AlliterationCombination combination : combinations) {
			s += row++ + ". " + combination.toString() + "\n";
		}
		return s;
	}

	static class AlliterationCombination {

		final TObjectIntMap<String> map;
		final int remainder;

		public AlliterationCombination(String[] alliterationWords, int[] kx) {
			map = new TObjectIntHashMap<String>(alliterationWords.length);

			int i = 0;
			for (;i < alliterationWords.length; i++) {
				String alliterationWord = alliterationWords[i];
				map.put(alliterationWord, kx[i]);
			}
			remainder = kx[kx.length - 1];
		}

		public int getAlliterationFor(String character) {
			return map.get(character);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj.getClass() == getClass()) {
				AlliterationCombination another = (AlliterationCombination)obj;
				return map.equals(another.map);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return map.hashCode();
		}

		@Override
		public String toString() {

			final String[] keys = map.keys(new String[map.size()]);
			Arrays.sort(keys);
			String s = "";
			for (String key : keys) {
				s += key+"="+map.get(key)+", ";
			}

			return '[' + s + remainder + ']';
		}
	}

	private static class ArrayCombination {

		private final int[] arr;

		private ArrayCombination(int[] arr) {
			this.arr = arr;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			ArrayCombination that = (ArrayCombination) o;

			return (Arrays.equals(arr, that.arr));
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(arr);
		}

		@Override
		public String toString() {
			return Arrays.toString(arr);
		}
	}
}
