package cz.compling.model.denotation;

import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

public class MinimumCoincidenceResult
	extends MutableTriple<ReachabilityGraphComponent, Spike, Double>
{

	public MinimumCoincidenceResult(ReachabilityGraphComponent component, Spike spike, double maxValue) {
		super(component, spike, maxValue);

	}
	public int compareTo(Triple<ReachabilityGraphComponent, Spike, Double> o) {
		return getRight().compareTo(o.getRight());
	}

	public void setProbability(double probability) {
		this.setRight(probability);
	}

	public void setSpike(Spike spike) {
		this.setMiddle(spike);
	}

	public Spike getSpike() {
		return getMiddle();
	}

	public double getProbability() {
		return getRight();
	}
}
