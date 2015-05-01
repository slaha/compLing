package cz.compling.model.denotation;

import java.util.List;

interface CoincidenceProvider {
	List<Coincidence> getCoincidenceFor(int hrebNumber);
}
