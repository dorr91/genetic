package genetics.example;

import java.util.Random;
import genetics.Species;

public class StringSpecies extends Species<StringSpecies> {
	/* Class fields */
	private static String target = "Hello World";
	public static final String alphabet = ""
		+ " abcdefghijklmnopqrstuvwxyz"
		//+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		+ "1234567890";
		//+ "`~!@#$%^&*()-_=+"
		//+ ".?,:;'`\"/<>\\\r\n\t";

	/* Class methods */
	public static StringSpecies random() {
		Random r = new Random();
		int length = r.nextInt(25) + 1; //nonzero length
		StringBuilder builder = new StringBuilder(length);
		for(int i=0; i<length; i++) {
			builder.append(alphabet.charAt(r.nextInt(alphabet.length())));
		}
		
		return new StringSpecies(builder.toString());
	}

	public static void setTarget(String newTarget) {
		target = newTarget;
	}
	public static String getTarget() {
		return target;
	}


	/* Instance fields */
	private String s;

	/* Constructors */
	public StringSpecies(String s) {
		this.s = s;
	}
	public StringSpecies(String s, double rate) {
		this.s = s;
		mutationRate = rate;
	}
	
	/* Instance methods */
	public String getString() { return s; }
	
	@Override
        public double score() {
                double score = 0;

                for(int i=0; i<s.length() && i<target.length(); i++) {
                        char x = s.charAt(i), y = target.charAt(i);
                        double diff = alphabet.indexOf(x) - alphabet.indexOf(y);
                        score -= Math.pow(diff,2);
                }

                score -= alphabet.length() * Math.abs(target.length() - s.length());

                return score;
        }


	@Override
	public StringSpecies mate(StringSpecies o) {
		StringSpecies child = null;
		StringBuilder builder = new StringBuilder();
		
		Random r = new Random();
		
		//take average length of the two and maybe shift it
		String s2 = o.getString();
		int length = (int)(
					Math.floor((double)s.length() / 2) + 
					Math.ceil((double)s2.length() / 2)
				);
		//shenanigans so we don't consistently round up or down,
		//forcing the length up or down over time
		
		Double shift = r.nextGaussian();
		length += Math.signum(shift) * Math.floor(Math.abs(shift)); //round to 0
		
		//construct the string, randomly taking chars from one or the other
		for(int i=0; i<length && i<s.length() && i<s2.length(); i++) {
			char next;
			if(r.nextBoolean())
				next = s.charAt(i);
			else
				next = s2.charAt(i);
			
			//with low probability, permute this element

			double mut = r.nextDouble();
			int range = 0;
			/* for some largest k >= 1, mut < mutationRate^k.
			 * we will use this k as a range in the alphabet in which we can mutate,
			 * centered at the character we already chose,
			 * so that with higher likelihood we mutate closer within the alphabet
			 * and with lower likelihood we mutate farther */
			while(mut < Math.pow(mutationRate, range)
					&& range < alphabet.length()) {
				range++;
			}
			int pos = alphabet.indexOf(next);
			int low = pos - range, high = pos + range;
			if (low < 0) low = 0;
			if (high >= alphabet.length()) high = alphabet.length() - 1;
			int mutationIndex = r.nextBoolean() ? low : high;
			//int mutationIndex = r.nextInt(alphabet.length());
			
			next = alphabet.charAt(mutationIndex);

			builder.append(next);
		}
		
		//finish the string with whatever's left, or random alphabetic chars if nothing.
		while(builder.length() < length) {
			if(builder.length() >= s.length() && builder.length() >= s2.length()) {
				//append random character from our alphabet
				char next = alphabet.charAt(r.nextInt(alphabet.length()));
				builder.append(next);
			} else if(builder.length() >= s.length()) {
				builder.append(s2.charAt(builder.length()));
			} else {
				builder.append(s.charAt(builder.length()));
			}
		}
		
		child = new StringSpecies(builder.toString());
		return child;
	}
	
	public String toString() {
		return s;
	}

}
