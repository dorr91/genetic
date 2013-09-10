import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collection;

import genetics.Population;

public class StringRunner {
	public static void main(String[] args) {
		int indivsPerGen = 100;
		int survivorsPerGen = 10;

		String target = "hello world";
		double rate = 0.01;
		if (args.length == 0) {
			Scanner cmdline = new Scanner(System.in);
			System.out.print("target string: ");
			target = cmdline.nextLine();

			System.out.print("mutation rate: ");
			rate = cmdline.nextDouble();
		} else {
			target = args[0];
			if (args.length > 1) {
				rate = Double.parseDouble(args[1]);
			}
		}
		
		StringSpecies.setTarget(target);
		StringSpecies.setMutationRate(rate);

		Collection<StringSpecies> g0 = new ArrayList<StringSpecies>();
		while(g0.size() < 100) g0.add(StringSpecies.random());
		Population<StringSpecies> pop = 
			new Population<StringSpecies>(g0, survivorsPerGen);
		
		StringSpecies.setMutationRate(0.01);
		pop.generate();
		String prev = "";
		long i;
		for(i=0; pop.best().score() < 0; i++) {
			if (pop.best().getString().compareTo(prev) != 0) {
				System.out.println(pop.best() + "\t= " + pop.best().score()
					+ " (" + i + " generations, " + i*indivsPerGen + " individuals generated)");
			}
			prev = pop.best().getString();
			double best = -pop.best().score();
			//double newMutationRate = StringSpecies.getMutationRate() * (1 - (1.0 / best));
			//if (newMutationRate < 0.1) newMutationRate = 0.1;
			pop.generate();
		}
		System.out.println("After " + i + " generations, discovered " +
					"optimal string was " + pop.best());
	}
}
