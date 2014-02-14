package cz.compling.text;

/**
 *
 * Basic implementation of Text interface
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 14.2.14 18:31</dd>
 * </dl>
 */
public class TextImpl implements Text {

	/** Text to analyse. Should be encoded in utf-8 */
	private final String plainText;

	/**
	 * Create new instance.
	 *
	 * @param text text to analyse. Cannot be null
	 *
	 * @throws java.lang.IllegalArgumentException if text is null
	 */
	public TextImpl(String text) {
		if (text == null) {
			throw new IllegalArgumentException("text cannot be null");
		}
		this.plainText = text;

	}

	@Override
	public String getPlainText() {
		return plainText;
	}
}
