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

	private static File dir = new File(".");
	private static File file = new File(dir, "compling/src/test/java/cz/compling/testPoem.txt");

	@BeforeClass
	public static void setUp() throws Exception {
		compLing = CompLing.getInstance(FileUtils.readFileToString(file));
	}

	protected static CompLing getCompLing() {
		return compLing;
	}
}
