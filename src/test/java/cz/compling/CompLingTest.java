package cz.compling;

import cz.compling.analysis.analysator.CharacterAnalyser;
import cz.compling.analysis.analysator.frequency.impl.CharacterFrequencyImplTest;
import cz.compling.analysis.analysator.impl.CharacterAnalyserImplTest;
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
	CharacterAnalyserImplTest.class,
	CharacterFrequencyImplTest.class
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

		CharacterAnalyser characterAnalyser1 = getCompLing().getCharacterAnalyser();
		CharacterAnalyser characterAnalyser2 = getCompLing().getCharacterAnalyser();

		Assert.assertEquals(characterAnalyser1, characterAnalyser2);
	}
}
