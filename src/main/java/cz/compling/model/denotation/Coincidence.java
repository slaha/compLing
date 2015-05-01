package cz.compling.model.denotation;

public class Coincidence {

	public final Hreb baseHreb;
	public final Hreb anotherHreb;
	public final int coincidenceCount;
	public final double probability;

	public Coincidence(Hreb baseHreb, Hreb anotherHreb, int coincidenceCount, double probability) {
		this.baseHreb = baseHreb;
		this.anotherHreb = anotherHreb;
		this.coincidenceCount = coincidenceCount;
		this.probability = probability;
	}
}
