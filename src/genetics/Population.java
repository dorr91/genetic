package genetics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class Population<S extends Species<S>> {
	S best = null;
	private Comparator<S> comparator = new SpeciesComparator();

	int generationSize, survivorsPerGeneration;
	long generationCounter;
	double mutationRate;

	PriorityQueue<S> currentGeneration;
	
	/* Constructor */
	public Population(Collection<S> gen0, int survivorsPerGeneration, 
			double mutationRate) {
		generationSize = gen0.size();
		this.survivorsPerGeneration = survivorsPerGeneration;
		generationCounter = 0;

		currentGeneration = new PriorityQueue<S>(generationSize, comparator);
		currentGeneration.addAll(gen0);
		
		this.mutationRate = mutationRate;
	}


	/* Accessors */
	public List<S> getSurvivors() {
		//copy the current generation so we can freely modify it
		PriorityQueue<S> current = new PriorityQueue<S>(
				currentGeneration.size(), comparator);
		current.addAll(currentGeneration);
		
		List<S> top = new ArrayList<S>();
		
		//pop the best of this generation into a new list.
		for(int i=0; i<survivorsPerGeneration; i++) {
			top.add(current.poll());
		}
		
		//the returned list is sorted!
		return top;
	}
	
	public S best() {
		return best;
	}
	public Collection<S> getCurrentGen() {
		//deep copy so the caller can't modify our internal state
		return new PriorityQueue<S>(currentGeneration);
	}
	public double getMutationRate() { return mutationRate; }
	public void setMutationRate(double newRate) { mutationRate = newRate; }
	
	/* Methods with side-effects (ie methods that do something) */
	public void generate() {
		List<S> survivors = getSurvivors();
		
		PriorityQueue<S> newGeneration = 
				new PriorityQueue<S>(generationSize, comparator);
		// artificially add the best so far so we don't lose progress
		if (best != null) newGeneration.add(best);
		
		double[] normedScores = new double[survivors.size()];
		
		double min = Double.POSITIVE_INFINITY;
		for(int i=0; i<survivors.size(); i++) {
			normedScores[i] = survivors.get(i).score();
			if(normedScores[i] < min) 
				min = normedScores[i];
		}
		
		if(min < 0) {
			for(int i=0; i<normedScores.length; i++) {
				normedScores[i] += -min + 1; //shift values so they're all >0
				//so normalizing doesn't invert negative values
			}
		}
		
		double sum = 0;
		for(int i=0; i<normedScores.length; i++) sum += normedScores[i];
		//want to choose the best with a higher likelihood than the worst
		//of the survivors
		
		//Each member is chosen as a parent with likelihood proportional
		//to its contribution to the gene pool
		for(int i=0; i<normedScores.length; i++) {
			Random r = new Random();
			double x = r.nextDouble() * sum;
			double y = r.nextDouble() * sum;
			double accum = 0;
			
			int p=-1, q=-1; //indices of the parents we choose for this child
			for(int j=0; accum < x || accum < y; j++) { 
				//we won't run off the end of the array because the scores
				//sum to 1, and both x and y are <1.
				accum += normedScores[j];
				if(p == -1 && accum >= x) p=j; //update only the first time
				if(q == -1 && accum >= y) q=j;
			}
			
			//we chose parents j and k
			S child = survivors.get(p).mate(survivors.get(q), mutationRate);
			newGeneration.add(child);
		}
		
		generationCounter++;
		currentGeneration = newGeneration;
		if(best == null || currentGeneration.peek().score() >= best.score())
			best = currentGeneration.peek();
	}
	
	
	/* Helper class */
	private class SpeciesComparator implements Comparator<S> {
		//a comparator for the Species class, sorting via the score() function
		public int compare(S mem1, S mem2) {
			double score1 = mem1.score(), score2 = mem2.score();
			if(score1 == score2) return 0;
			
			//higher score = better
			//so mem1 comes before mem2 (ie is less than, for the purposes of a priority queue)
			//iff score(mem1) > score(mem2)
			return (score1 > score2) ? -1 : 1;
		}
	}
}
