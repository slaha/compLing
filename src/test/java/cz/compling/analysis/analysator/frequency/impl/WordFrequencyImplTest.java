package cz.compling.analysis.analysator.frequency.impl;

import cz.compling.analysis.analysator.frequency.words.IWordFrequency;
import cz.compling.analysis.analysator.frequency.words.WordFrequencyRule;
import cz.compling.analysis.analysator.impl.WordFrequencyAnalyserImplTest;
import cz.compling.utils.Reference;
import cz.compling.utils.TrooveUtils;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 1.3.14 20:07</dd>
 * </dl>
 */
public class WordFrequencyImplTest extends WordFrequencyAnalyserImplTest {

	private static IWordFrequency frequencyAnalyser;

	@BeforeClass
	public static void setUp() throws Exception {
		WordFrequencyAnalyserImplTest.setUp();

		frequencyAnalyser = getAnalyser().getWordFrequency();
	}
	@Test
	public void testGetCountOfWords() throws Exception {
		int countOfWords = frequencyAnalyser.getWordFrequency().getCountOfWords();


		Assert.assertEquals(909, countOfWords);
	}

	@Test
	public void testGetFrequencyFor() throws Exception {
		int havranFreq = frequencyAnalyser.getWordFrequency().getFrequencyFor("havran");

		Assert.assertEquals(9, havranFreq);
	}

	@Test
	public void testGetAllByFrequency() throws Exception {
		List<Pair<String,Integer>> allByFrequency = frequencyAnalyser.getWordFrequency().getAllWordsByFrequency(TrooveUtils.SortOrder.DESCENDING);

		int last = -1;
		String lastWord = null;
		for (Pair<String, Integer> pair : allByFrequency) {
			if (lastWord != null) {
				//..check sort by frequency
				if (last < pair.getValue1()) {
					Assert.fail(String.format("Wrong ordering by frequency. Last value is %d (for '%s'), current is %d (for '%s').", last, lastWord, pair.getValue1(), pair.getValue0()));
				}
				//..when frequency is the same check compare on words (strings)
				if (last == pair.getValue1() && lastWord.compareTo(pair.getValue0()) < 0) {
					Assert.fail(String.format("Wrong ordering by words. Last value is %s (freq %d), current is %s (freq %d).", lastWord, last, pair.getValue0(), pair.getValue1()));
				}
			}
			last = pair.getValue1();
			lastWord = pair.getValue0();
		}
	}

	@Test
	public void testGetAllWordsLengthsByFrequency() throws Exception {
		List<Pair<Integer, Integer>> allWordsLengthsByFrequency = frequencyAnalyser.getWordFrequency().getAllWordsLengthsByFrequency(TrooveUtils.SortOrder.DESCENDING);
		Pair<Integer, Integer> pair = allWordsLengthsByFrequency.get(0);

//		[ „‚\n][a-zA-ZěščřžýáíéĚŠČŘŽÝÁÍÉďťňůú]{5}[ \n!?,:;.] and s/ /  /g
		Assert.assertEquals(new Pair<Integer, Integer>(5, 189), pair);

		System.out.println(allWordsLengthsByFrequency);
		WordFrequencyRule chRule = new WordFrequencyRule() {

			@Override
			public boolean modify(Reference<String> word, Reference<Integer> length) {
				String wordReplaced = StringUtils.replaceEach(
					word.value,
					new String[]{"ch", "Ch", "cH", "CH"},
					new String[]{"c", "c", "c", "c"});
				return  ((length.value = wordReplaced.length()) != word.value.length());
			}
		};

		frequencyAnalyser.registerRule(chRule);
		IWordFrequency wordFrequency = getCompLing().getWordFrequencyAnalyser().getWordFrequency();
		List<Pair<Integer, Integer>> allWordsByFrequency = wordFrequency.getWordFrequency().getAllWordsLengthsByFrequency(TrooveUtils.SortOrder.DESCENDING);
		System.out.println(allWordsByFrequency);

	}

	@Test
	public void testGetFrequencyForAll() throws Exception {

		Assert.assertEquals(189, frequencyAnalyser.getWordFrequency().getFrequencyFor(5));

	}
}
