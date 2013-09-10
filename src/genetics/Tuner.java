package genetics;

import java.util.Collection;

public class Tuner<S extends Species<S>> {
	public void testConstantMutationRate(Collection<S> g0, int survivorsPerGen) {
		/* TODO: multithread 
		 * 	(problem: species-level properties, like mutation rate and target)  */
		int duration = 60; //time to let each setup run, in seconds

		Population<S> pop = new Population<S>(g0, survivorsPerGen);

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
			System.out.println("\tmutation rate: " + S.getMutationRate());
			System.out.println("\t" + g0.size() + " individuals"
				+ " per generation, with " + survivorsPerGen + 
				" survivors");
		} else {
			System.out.println("Found optimal solution " + pop.best() +
				" after " + (elapsed/1000) + " seconds.");
		}
	}
}
