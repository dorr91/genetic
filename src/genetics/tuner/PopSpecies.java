package genetics.tuner;

import genetics.Species;

/* 
 * a species whose members are populations of another species;
 * to be used for tuning the parameters for the other species.
 */
class PopSpecies extends Species<PopSpecies> {
	private Population pop;

	public PopSpecies(Population p) {
		pop = p;
	}

	public double score() {
		return pop.best().score();
	}

	public PopSpecies mate(PopSpecies other) {
		//TODO
		throw new Exception("functionality not implemented.");
	}
}
