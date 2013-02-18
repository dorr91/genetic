import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public abstract class Population<Individual> {
	Individual best = null;
	int generationCounter;
	int generationSize, survivorsPerGeneration;
	PriorityQueue<Individual> currentGeneration;
	
	public Population(int generationSize, int survivorsPerGeneration) {
		this.generationSize = generationSize;
		this.survivorsPerGeneration = survivorsPerGeneration;
		generationCounter = 0;
	}
	
	abstract double score(Individual member);
	
	abstract Individual mate(Individual mem1, Individual mem2);
	
	List<Individual> getSurvivors() {
		//copy the current generation so we can freely modify it
		PriorityQueue<Individual> current = new PriorityQueue<Individual>(
				currentGeneration.size(), new IndividualComparator());
		current.addAll(currentGeneration);
		
		List<Individual> top = new ArrayList<Individual>();
		
		//pop the best of this generation into a new list.
		for(int i=0; i<survivorsPerGeneration; i++) {
			top.add(current.poll());
		}
		
		//the returned list is sorted!
		return top;
	}
	
	void generate() {
		List<Individual> survivors = getSurvivors();
		
		PriorityQueue<Individual> newGeneration = new PriorityQueue<Individual>(
				generationSize, new IndividualComparator());
		//must make an executive decision: can an individual mate with itself?
		//Think I'm gonna go with no.
		//Reasoning: I can't mate with myself, and by God, if I can't do it,
		//	my code can't either.
		
		for(int i=0; i<generationSize; i++) {
			int x1, x2;
			//want to choose the best with a higher likelihood than the worst
			//of the survivors
			
			//TODO: algorithm to choose x and y:
			// for a = rand():
			// 	normed(j) < a < normed(j+1) => x = j+1
			//	where normed(j) = j / max(scores(survivor : survivors))
			Individual child = mate(survivors.get(x), survivors.get(y));
			newGeneration.add(child);
		}
	}
	
	//a comparator for the Individual class, sorting via the score() function
	private class IndividualComparator implements Comparator<Individual> {
		public int compare(Individual mem1, Individual mem2) {
			//done in this strange manner to avoid casting,
			//since we don't want to accidentally cast a difference of < 1 to 0
			
			double score1 = score(mem1), score2 = score(mem2);
			if(score1 == score2) return 0;
			
			//higher score = better
			//so mem1 comes before mem2 iff score(mem1) > score(mem2) 
			//ie if score(mem2) - score(mem1) < 0
			return (score1 > score2) ? 1 : -1;
		}
	}
}
