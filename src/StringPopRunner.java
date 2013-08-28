import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;


public class StringPopRunner {
	public static void main(String[] args) {
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		
		Collection<StringIndividual> g0 = new ArrayList<StringIndividual>();
		while(g0.size() < 100) g0.add(StringIndividual.random());
		StringPopulation sp = new StringPopulation(1000, 100, "hello world");
		sp.initialize(g0);
		
		sp.generate();
		
		StringIndividual prev = null;
		for(int i=0; sp.score(sp.best()) < 0; i++) {
			if (i % 100 == 0)
				System.out.println(sp.best() + "\t= " + sp.score(sp.best()));
			double best = -sp.score(sp.best());
			StringIndividual.setMutationRate(Math.sin(1.0 / best));
			sp.generate();
		}
	}
}
