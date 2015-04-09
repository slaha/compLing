package cz.compling.model.denotation;

import java.util.Collection;

class ReachabilityComputer {

	private final ComponentsList components;
	private double index;
	private boolean computed;

	public ReachabilityComputer(Collection<Spike> spikes, CoincidenceProvider coincidenceProvider) {
		components = new ComponentsList(spikes, coincidenceProvider);
	}

	public void compute() {

		double index;
		do {
			MinimumCoincidenceResult c = components.findMinProbabilityComponent();
			ReachabilityGraphComponent another = components.findComponentFor(c.getSpike());
			c.getLeft().connect(another);
			components.remove(another);

			index = c.getProbability();
		} while (components.size() > 1);

		this.index = index;
		this.computed = true;
	}

	public double getResult() {
		if (!computed) {
			throw new IllegalStateException("Not computed yet. Call compute()");
		}
		return index;
	}
}
