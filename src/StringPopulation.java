import java.util.Collection;

import genetics.Population;


public class StringPopulation extends Population<StringIndividual> {
	String target;
	public StringPopulation(int generationSize, int survivorsPerGeneration, String target) {
		super(generationSize, survivorsPerGeneration);
		this.target = target;
	}

	@Override
	public double score(StringIndividual member) {
		if(member == null) throw new IllegalArgumentException();
		double score = 0;
		String s = member.getString();
		for(int i=0; i<s.length() && i<target.length(); i++) {
			if(s.charAt(i) != target.charAt(i))
				score -= 1;
		}
		
		score -= 2*Math.abs(target.length() - s.length());
		
		return score;
	}

}
