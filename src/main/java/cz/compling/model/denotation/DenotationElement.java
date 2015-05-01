package cz.compling.model.denotation;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 3.5.14 11:04</dd>
 * </dl>
 */
public class DenotationElement {
	private final DenotationWord denotationWord;
	private int number;
	private Hreb hreb;

	/** Used if user has to provide part which does not belong to hreb = (lete)l */
	private String text;

	public DenotationElement(DenotationWord denotationWord, int number) {
		this(denotationWord, number, null);
	}

	public DenotationElement(DenotationWord denotationWord, int number, String alias) {
		this.denotationWord = denotationWord;
		this.number = number;
		this.text = alias;
	}

	public boolean isInHreb() {
		return hreb != null;
	}

	public Hreb getHreb() {
		return hreb;
	}

	@Override
	public String toString() {
		return String.valueOf(number);
	}

	void onAddToHreb(Hreb hreb, String input) {
		this.hreb = hreb;
		this.text = input;
	}

	void onRemoveFromHreb(Hreb hreb) {
		if (hreb == this.hreb) {
			this.hreb = null;
			this.text = null;
		} else {
			throw new IllegalArgumentException("Illegal call to onRemoveFromHreb. Current hreb" + this.hreb + ", arg. hreb " + hreb);
		}
	}

	public void onAddToHreb(Hreb hreb) {
		onAddToHreb(hreb, this.text);
	}

	public int getNumber() {
		return number;
	}

	DenotationElement duplicate() {
		return new DenotationElement(this.denotationWord, this.number, this.text);
	}

	public void increment(int increment) {
		this.number += increment;
	}

	public String getText() {
		return text;
	}
}