package cz.compling.model.denotation;

import java.util.*;

class ComponentsList extends ArrayList<ReachabilityGraphComponent> {
	private final CoincidenceProvider coincidenceProvider;

	public ComponentsList(Collection<Spike> spikes, CoincidenceProvider coincidenceProvider) {
		super(spikes.size());
		this.coincidenceProvider = coincidenceProvider;
		for (Spike spike : spikes) {
			add(new ReachabilityGraphComponent(spike));
		}
	}

	public MinimumCoincidenceResult findMinProbabilityComponent() {
		List<MinimumCoincidenceResult> minProbabilities = new ArrayList<MinimumCoincidenceResult>();
		for (ReachabilityGraphComponent component : this) {
			for (Spike spike : component.spikes) {
				MinimumCoincidenceResult min = new MinimumCoincidenceResult(component, null, Double.MAX_VALUE);
				final int number = spike.getNumber();
				for (Coincidence coincidence : coincidenceProvider.getCoincidenceFor(number)) {

					double probability = coincidence.probability;
					final Spike anotherSpike = coincidence.anotherSpike;
					if (probability < min.getRight() && !component.connectedWith(anotherSpike)) {
						min.setSpike(anotherSpike);
						min.setProbability(probability);
					}
				}
				if (min.getRight() != Double.MAX_VALUE) {
					minProbabilities.add(min);
				}
			}
		}
		if (minProbabilities.isEmpty()) {
			throw new NoSuchElementException();
		}
		return Collections.min(minProbabilities);
	}

	public ReachabilityGraphComponent findComponentFor(Spike hreb) {
		for (ReachabilityGraphComponent component : this) {
			if (component.spikes.contains(hreb)) {
				return component;
			}
		}
		throw new IllegalStateException("No component for hreb '" + hreb + "' found");
	}

	@Override
	public String toString() {
		return "ComponentsList{size=" + size() + '}';
	}
}
