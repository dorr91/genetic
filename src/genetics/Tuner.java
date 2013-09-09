package genetics;

import java.util.ArrayList;
import java.util.Collection;

public class Tuner<PopType extends Population, IndivType extends Individual> {
	public void test(int indivsPerGen, int survivorsPerGen) {
		/* TODO: multithread */
		int duration = 2; //time to let each setup run, in minutes

		Collection<IndivType> g0 = new ArrayList<IndivType>();
		while (g0.size() < indivsPerGen) g0.add(IndivType.random());

		/* TODO: allow other params? ie target string for string population... */
		PopType population = new PopType(indivsPerGen, survivorsPerGen);
		population.initialize(g0);

		int i = 0;
		long startTime = System.currentTimeMillis();
		long elapsed = 0;
		/* run for 10 minutes or until it finds the optimal answer */
		while (elapsed < (duration*60*1000) && population.score(population.best) < 0) {
			population.generate();
			i++;
			elapsed = System.currentTimeMillis() - startTime;
		}

		double best = population.score(population.best());
		if (best < 0) {
			System.out.println("Score " + best + " after " + duration + " minutes.");
			System.out.println("\tmutation rate: " + IndivType.getMutationRate());
			System.out.println("\t" + indivsPerGen + " individuals"
				+ " per generation, with " + survivorsPerGen + 
				" survivors");
		} else {
			System.out.println("Found optimal solution " + population.best() +
				" after " + (elapsed/1000) + " seconds.");
		}
	}
}
