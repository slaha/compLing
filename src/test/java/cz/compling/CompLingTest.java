package cz.compling;

import cz.compling.analysis.analysator.CharacterAnalyser;

/**
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 18:37</dd>
 * </dl>
 */
public class CompLingTest extends AbstTest {

	public void testGetCompLingInstanceNull() {

		try {
			CompLing.getInstance(null);
			fail("No IllegalArgumentException when creating CompLing instance wit null parameter");
		} catch (IllegalArgumentException ex) {

		}

	}

	public void testGetCompLingInstance() {

		CompLing compLing = CompLing.getInstance("");

		assertTrue(compLing != null);

	}

	public void testGetCharacterAnalyser() {

		CharacterAnalyser characterAnalyser1 = compLing.getCharacterAnalyser();
		CharacterAnalyser characterAnalyser2 = compLing.getCharacterAnalyser();

		assertEquals(characterAnalyser1, characterAnalyser2);
	}
}
