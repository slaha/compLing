package cz.compling.analysis.analysator.poems.impl;

import cz.compling.analysis.analysator.poems.IAssonance;
import cz.compling.poem.Poem;
import cz.compling.poem.PoemImpl;
import cz.compling.text.Text;
import cz.compling.text.TextImpl;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
 * <dd> 9.3.14 16:34</dd>
 * </dl>
 */
public class AssonanceImpl implements IAssonance {

	private final Poem poem;
	private final String[] baseVocals;

	private final TIntIntMap assonance;

	public AssonanceImpl(Poem poem, String[] vocals) {
		this.poem = poem;
		this.baseVocals = vocals;

		//..sort from longest to shortest. If lengths are the same compare alphabetically
		Arrays.sort(baseVocals, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int diff = o2.length() - o1.length();
				if (diff == 0) {
					diff = o1.compareTo(o2);
				}
				return diff;
			}
		});
		assonance = new TIntIntHashMap();
		compute();
	}

	private void compute() {
		final String[] vocals = createVocals();

		final int steps = vocals.length / 2 + 1;

		for (int k = 1; k < steps; k++) {
			assonance.put(k, assonanceFor(vocals, k));
		}
	}

	private int assonanceFor(final String[] vocals, final int step) {
		int assonance = 0;

		for (int i = 0; i < vocals.length - step; i++) {
			if (vocals[i].equals(vocals[i + step])) {
				assonance++;
			}
		}
		
		return assonance;
	}

	private String[] createVocals() {
		List<String> vocals = new ArrayList<String>();

		//..using string builder because we will create a lot of substrings
		final StringBuilder poemBuilder = new StringBuilder(poem.getPlainText());
		int indexOfAny;
		boolean stop;
		do {
			indexOfAny = StringUtils.indexOfAny(poemBuilder.toString(), baseVocals);
			stop = indexOfAny < 0;
			if (stop) {
				//..nothing found. Break the cycle
				break;
			} else {
				//..make found occurrence the first thing from the string
				poemBuilder.delete(0, indexOfAny);
				final String poemString = poemBuilder.toString();
				for (String baseVocal : baseVocals) {
					if (StringUtils.startsWithIgnoreCase(poemString, baseVocal)) {
						//..this is the found vocal. Delete it from string to found next
						vocals.add(baseVocal);
						poemBuilder.delete(0, baseVocal.length());
						break;
					}
				}
			}

		} while (true);


		return vocals.toArray(new String[vocals.size()]);
	}

	public static void main(String[] args) {
		Text text = new TextImpl("Skakal pes přes ouves přes zelenou louku");
		Poem poem = new PoemImpl(text);
		IAssonance a = new AssonanceImpl(poem, new String[]{"y", "a", "o", "u", "i", "au", "e", "ou"});
	}

}
