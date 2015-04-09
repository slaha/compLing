package cz.compling.model.denotation;

import java.util.HashSet;
import java.util.Set;

class ReachabilityGraphComponent {

	Set<Spike> spikes;
	public ReachabilityGraphComponent(Spike spike) {
		spikes = new HashSet<Spike>();
		spikes.add(spike);
	}

	public void connect(ReachabilityGraphComponent another) {
		spikes.addAll(another.spikes);
	}

	public boolean connectedWith(Spike anotherSpike) {
		return spikes.contains(anotherSpike);
	}

	@Override
	public String toString() {
		return "Component{" +
			"spikes=" + spikes +
			'}';
	}
}
