package cz.compling;

import cz.compling.analysis.analysator.CharacterAnalyser;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 18:37</dd>
 * </dl>
 */
public class CompLingTest extends TestCase {

	private static File dir = new File(".");
	public static File file = new File(dir, "compling/src/test/java/cz/compling/testPoem.txt");
	private static String text;

	@Override
	public void setUp() throws Exception {
		text = FileUtils.readFileToString(file);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCompLingInstanceNull() {

		CompLing.getInstance(null);

	}

	@Test
	public void testGetCompLingInstance() {

		CompLing compLing = CompLing.getInstance(text);

		Assert.assertTrue(compLing != null);

	}

	@Test
	public void testGetCharacterAnalysator() {
		CompLing compLing = CompLing.getInstance(text);

		CharacterAnalyser characterAnalyser1 = compLing.getCharacterAnalyser();
		CharacterAnalyser characterAnalyser2 = compLing.getCharacterAnalyser();

		Assert.assertEquals(characterAnalyser1, characterAnalyser2);
	}
}
