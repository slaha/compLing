package cz.compling.model.denotation;

public class Coincidence {

	public final Spike baseSpike;
	public final Spike anotherSpike;
	public final int coincidenceCount;
	public final double probability;

	public Coincidence(Spike baseSpike, Spike anotherSpike, int coincidenceCount, double probability) {
		this.baseSpike = baseSpike;
		this.anotherSpike = anotherSpike;
		this.coincidenceCount = coincidenceCount;
		this.probability = probability;
	}
}
