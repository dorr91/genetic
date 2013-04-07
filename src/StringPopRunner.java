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
		
		while(sp.score(sp.best()) < 0) {
			System.out.println(sp.best() + "\t= " + sp.score(sp.best()));
			sp.generate();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}
}
