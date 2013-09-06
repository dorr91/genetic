import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;


public class StringPopRunner {
	public static void main(String[] args) {
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		int indivsPerGen = 100;
		int survivorsPerGen = 10;
		
		Collection<StringIndividual> g0 = new ArrayList<StringIndividual>();
		while(g0.size() < 100) g0.add(StringIndividual.random());
		StringPopulation sp = new StringPopulation(indivsPerGen, survivorsPerGen, "hello world");
		sp.initialize(g0);
		
		StringIndividual.setMutationRate(0.03);
		sp.generate();
		double i;
		for(i=0; sp.score(sp.best()) < 0; i++) {
			if (i % 10000 == 0)
				System.out.println(sp.best() + "\t= " + sp.score(sp.best())
					+ " (" + i + " generations, " + i*indivsPerGen + " individuals generated)");
			double best = -sp.score(sp.best());
			//double newMutationRate = StringIndividual.getMutationRate() * (1 - (1.0 / best));
			//if (newMutationRate < 0.1) newMutationRate = 0.1;
			sp.generate();
		}
		System.out.println("After " + i + " generations, discovered " +
					"optimal string was " + sp.best());
	}
}
