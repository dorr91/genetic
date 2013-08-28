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
			char x = s.charAt(i), y = target.charAt(i);
			double diff = StringIndividual.alphabet.indexOf(x) - StringIndividual.alphabet.indexOf(y);
			score -= Math.abs(diff);
		}
		
		score -= StringIndividual.alphabet.length() * Math.abs(target.length() - s.length());
		
		return score;
	}

}
