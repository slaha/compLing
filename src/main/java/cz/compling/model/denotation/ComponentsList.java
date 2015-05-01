package cz.compling.model.denotation;

import java.util.*;

class ComponentsList extends ArrayList<ReachabilityGraphComponent> {
	private final CoincidenceProvider coincidenceProvider;

	public ComponentsList(Collection<Hreb> hrebs, CoincidenceProvider coincidenceProvider) {
		super(hrebs.size());
		this.coincidenceProvider = coincidenceProvider;
		for (Hreb hreb : hrebs) {
			add(new ReachabilityGraphComponent(hreb));
		}
	}

	public MinimumCoincidenceResult findMinProbabilityComponent() {
		List<MinimumCoincidenceResult> minProbabilities = new ArrayList<MinimumCoincidenceResult>();
		for (ReachabilityGraphComponent component : this) {
			for (Hreb hreb : component.hrebs) {
				MinimumCoincidenceResult min = new MinimumCoincidenceResult(component, null, Double.MAX_VALUE);
				final int number = hreb.getNumber();
				for (Coincidence coincidence : coincidenceProvider.getCoincidenceFor(number)) {

					double probability = coincidence.probability;
					final Hreb anotherHreb = coincidence.anotherHreb;
					if (probability < min.getRight() && !component.connectedWith(anotherHreb)) {
						min.setHreb(anotherHreb);
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

	public ReachabilityGraphComponent findComponentFor(Hreb hreb) {
		for (ReachabilityGraphComponent component : this) {
			if (component.hrebs.contains(hreb)) {
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
