package genetics;

import java.util.ArrayList;
import java.util.Collection;

public class Tuner<PopType extends Population<IndivType>, IndivType extends Individual> {
	public void test(String[] args) {
		/* TODO: multithread */
		int indivsPerGen = 100;
		int survivorsPerGen = 10;

		Collection<IndivType> g0 = new ArrayList<IndivType>();
		while (g0.size() < indivsPerGen) g0.add(IndivType.random());

		/* TODO: allow other params? ie target string for string population... */
		PopType population = new PopType(indivsPerGen, survivorsPerGen);
		population.initialize(g0);

		int i;
		for (i = 0; population.score(population.best) < 0; i++) {
			population.generate();
		}
	}
}
