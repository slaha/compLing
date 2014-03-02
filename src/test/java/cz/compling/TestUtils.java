package cz.compling;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 1.3.14 18:58</dd>
 * </dl>
 */
public class TestUtils {

	public static boolean isLinux() {

		try {
			final String OS_NAME = System.getProperty("os.name");
			if (OS_NAME != null) {
				return OS_NAME.toLowerCase().contains("linux");
			}
		} catch (Exception ex) {
			//..ignore ex
		}
		return false;
	}

	public static int fileSize(File file, int defaultLength) {
		String command = String.format("wc -m %s", file.getAbsolutePath());

		try {
			String exec = exec(command);
			return Integer.parseInt(exec.split(" ")[0]);
		} catch (Exception e) {
			//..something wrong. Ignore it
		}
		return defaultLength;
	}

	private static String exec(String command) throws IOException {
		Process process = Runtime.getRuntime().exec(command);
		return IOUtils.toString(process.getInputStream(), Charset.defaultCharset());
	}
}
