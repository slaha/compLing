package cz.compling;

import cz.compling.analysis.analysator.frequency.impl.CharacterFrequencyImplTest;
import cz.compling.analysis.analysator.frequency.impl.WordFrequencyImplTest;
import cz.compling.analysis.analysator.impl.CharacterAnalyserImplTest;
import cz.compling.analysis.analysator.impl.WordAnalyserImplTest;
import cz.compling.analysis.analysator.poems.impl.AggregationsImplTest;
import cz.compling.analysis.analysator.poems.impl.AlliterationImplTest;
import cz.compling.analysis.analysator.poems.impl.AssonanceImplTest;
import cz.compling.utils.TrooveUtilsListsTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 18:37</dd>
 * </dl>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	TrooveUtilsListsTest.class,

	CharacterAnalyserImplTest.class,
	CharacterFrequencyImplTest.class,
	WordAnalyserImplTest.class,
	WordFrequencyImplTest.class,
	AggregationsImplTest.class,
	AlliterationImplTest.class,
	AssonanceImplTest.class
})
public class CompLingTest extends AbstTest {


	@Test(expected = IllegalArgumentException.class)
	public void getCompLingInstanceNull() {

		CompLing.getInstance(null);
	}

	@Test
	public void getCompLingInstance() {

		CompLing compLing = CompLing.getInstance("");

		Assert.assertTrue(compLing != null);

	}

	@Test
	public void getCharacterAnalyser() {

		Object characterAnalyser1 = getCompLing().generalAnalysis().characterFrequency();
		Object characterAnalyser2 = getCompLing().generalAnalysis().characterFrequency();

		Assert.assertEquals(characterAnalyser1, characterAnalyser2);
	}
}
