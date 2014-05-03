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
	private Spike spike;

	/** Used if user has to provide part which does not belong to spike = (lete)l */
	private String text;

	public DenotationElement(DenotationWord denotationWord, int number) {
		this(denotationWord, number, null);
	}

	public DenotationElement(DenotationWord denotationWord, int number, String alias) {
		this.denotationWord = denotationWord;
		this.number = number;
		this.text = alias;
	}

	public boolean isInSpike() {
		return spike != null;
	}

	public Spike getSpike() {
		return spike;
	}

	@Override
	public String toString() {
		return String.valueOf(number);
	}

	public void onAddToSpike(Spike spike, String input) {
		this.spike = spike;
		this.text = input;
	}

	public void onAddToSpike(Spike spike) {
		onAddToSpike(spike, null);
	}

	public int getNumber() {
		return number;
	}

	void setSpike(Spike spike) {
		this.spike = spike;
	}

	DenotationElement duplicate() {
		return new DenotationElement(this.denotationWord, this.number);
	}

	public void increment(int increment) {
		this.number += increment;
	}
}