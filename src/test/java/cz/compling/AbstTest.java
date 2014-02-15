package cz.compling;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

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
public class AbstTest extends TestCase {

	protected CompLing compLing;

	private static File dir = new File(".");
	private static File file = new File(dir, "compling/src/test/java/cz/compling/testPoem.txt");

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		compLing = CompLing.getInstance(FileUtils.readFileToString(file));
	}

}
