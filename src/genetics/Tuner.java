package genetics;

import java.util.Collection;

public class Tuner<S extends Species<S>> {
	public void testConstantMutationRate(Collection<S> g0, int survivorsPerGen) {
		/* TODO: multithread 
		 * 	(problem: species-level properties, like mutation rate and target)  */
		int duration = 60; //time to let each setup run, in seconds

		// defaulting to mutation rate of 0.01 for no particular reason.
		// eventually will iterate through rates.
		// or find one by a genetic algorithm...
		double mutationRate = 0.01;
		Population<S> pop = new Population<S>(g0, survivorsPerGen, mutationRate);

		long i = 0;
		long startTime = System.currentTimeMillis();
		long elapsed = 0;
		/* run for 10 minutes or until it finds the optimal answer */
		while (elapsed < (duration*1000) && pop.best().score() < 0) {
			pop.generate();
			i++;
			elapsed = System.currentTimeMillis() - startTime;
		}

		double best = pop.best().score();
		if (best < 0) {
			System.out.println("Score " + best + " after " + duration + " seconds.");
			System.out.println("\tmutation rate: " + mutationRate);
			System.out.println("\t" + g0.size() + " individuals"
				+ " per generation, with " + survivorsPerGen + 
				" survivors");
		} else {
			System.out.println("Found optimal solution " + pop.best() +
				" after " + (elapsed/1000) + " seconds.");
		}
	}

}

class RoundStats<S extends Species<S>> {
	public Population<S> population;
	public double score; //best score
	public boolean finished; //found an optimal solution before time was up?
	public long runtime; //seconds

	public RoundStats(Population<S> pop) {
		population = pop;
		score = pop.best().score();
	}
}
