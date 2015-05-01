package cz.compling.model.denotation;

import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

public class MinimumCoincidenceResult
	extends MutableTriple<ReachabilityGraphComponent, Hreb, Double>
{

	public MinimumCoincidenceResult(ReachabilityGraphComponent component, Hreb hreb, double maxValue) {
		super(component, hreb, maxValue);

	}
	public int compareTo(Triple<ReachabilityGraphComponent, Hreb, Double> o) {
		return getRight().compareTo(o.getRight());
	}

	public void setProbability(double probability) {
		this.setRight(probability);
	}

	public void setHreb(Hreb hreb) {
		this.setMiddle(hreb);
	}

	public Hreb getHreb() {
		return getMiddle();
	}

	public double getProbability() {
		return getRight();
	}
}
