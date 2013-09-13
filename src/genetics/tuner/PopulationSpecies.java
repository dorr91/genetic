package genetics.tuner;

import genetics.Species;
import genetics.Population;

/* 
 * a species whose members are populations of another species;
 * to be used for tuning the parameters for the other species.
 */
class PopulationSpecies extends Species<PopulationSpecies> {
	private Population pop;

	public PopulationSpecies(Population p) {
		pop = p;
	}

	public double score() {
		// TODO: also use time-to-completion if it finds optimal?
		return pop.best().score();
	}

	public PopulationSpecies mate(PopulationSpecies other, 
			double mutationRate) {
		//TODO implement
		throw new RuntimeException("functionality not implemented.");
	}
}
