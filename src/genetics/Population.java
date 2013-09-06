package genetics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public abstract class Population<I extends Individual<I>> {
	I best = null;
	public Comparator<I> comparator = new IndividualComparator();
	int generationCounter;
	int generationSize, survivorsPerGeneration;
	PriorityQueue<I> currentGeneration;
	
	public Population(int generationSize, int survivorsPerGeneration) {
		this.generationSize = generationSize;
		this.survivorsPerGeneration = survivorsPerGeneration;
		generationCounter = 0;
		
		currentGeneration = new PriorityQueue<I>(generationSize, comparator);
	}

	//after some internal debate, the score function goes here
	//because generally the same pressures applies to the whole group
	//and not individual members
	public abstract double score(I member);
	
	//this can't go in the constructor, because the score function may require
	//state that can't be established yet in the extending class
	public void initialize(Collection<I> gen0) {
		currentGeneration.addAll(gen0);
	}
	
	public List<I> getSurvivors() {
		//copy the current generation so we can freely modify it
		PriorityQueue<I> current = new PriorityQueue<I>(
				currentGeneration.size(), comparator);
		current.addAll(currentGeneration);
		
		List<I> top = new ArrayList<I>();
		
		//pop the best of this generation into a new list.
		for(int i=0; i<survivorsPerGeneration; i++) {
			top.add(current.poll());
		}
		
		//the returned list is sorted!
		return top;
	}
	
	public I best() {
		return best;
	}
	public Collection<I> getCurrentGen() {
		//deep copy so the caller can't modify our internal state
		return new PriorityQueue<I>(currentGeneration);
	}
	
	public void generate() {
		List<I> survivors = getSurvivors();
		
		PriorityQueue<I> newGeneration = 
				new PriorityQueue<I>(generationSize, comparator);
		
		double[] normedScores = new double[survivors.size()];
		
		double min = Double.POSITIVE_INFINITY;
		for(int i=0; i<survivors.size(); i++) {
			normedScores[i] = score(survivors.get(i));
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
			I child = survivors.get(p).mate(survivors.get(q));
			newGeneration.add(child);
		}
		
		generationCounter++;
		currentGeneration = newGeneration;
		if(best == null || score(currentGeneration.peek()) >= score(best))
			best = currentGeneration.peek();
	}
	
	
	//a comparator for the Individual class, sorting via the score() function
	private class IndividualComparator implements Comparator<I> {
		public int compare(I mem1, I mem2) {
			double score1 = score(mem1), score2 = score(mem2);
			if(score1 == score2) return 0;
			
			//higher score = better
			//so mem1 comes before mem2 (ie is less than, for the purposes of a priority queue)
			//iff score(mem1) > score(mem2)
			return (score1 > score2) ? -1 : 1;
		}
	}
}
