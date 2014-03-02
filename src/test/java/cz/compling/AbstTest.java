package cz.compling;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;

import java.io.File;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 20:09</dd>
 * </dl>
 */
public class AbstTest {

	private static CompLing compLing;

	private static final File DIR = new File("compling/src/test/java/cz/compling");
	public static final File FILE = new File(DIR, "testPoem.txt");


	@BeforeClass
	public static void setUp() throws Exception {
		compLing = CompLing.getInstance(FileUtils.readFileToString(FILE));
	}

	protected static CompLing getCompLing() {
		return compLing;
	}
}
