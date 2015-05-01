package cz.compling.model.denotation;

import java.util.HashSet;
import java.util.Set;

class ReachabilityGraphComponent {

	Set<Hreb> hrebs;
	public ReachabilityGraphComponent(Hreb hreb) {
		hrebs = new HashSet<Hreb>();
		hrebs.add(hreb);
	}

	public void connect(ReachabilityGraphComponent another) {
		hrebs.addAll(another.hrebs);
	}

	public boolean connectedWith(Hreb anotherHreb) {
		return hrebs.contains(anotherHreb);
	}

	@Override
	public String toString() {
		return "Component{" +
			"hrebs=" + hrebs +
			'}';
	}
}
