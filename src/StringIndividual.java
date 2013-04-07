import java.util.Random;

import genetics.Individual;


public class StringIndividual extends Individual<StringIndividual> {
	public static final String alphabet = ""
			+ " abcdefghijklmnopqrstuvwxyz"
			//+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "1234567890";
			//+ "`~!@#$%^&*()-_=+"
			//+ ".?,:;'`\"/<>\\\r\n\t";
	/*
	 * Properties of a string:
	 * 	length
	 * 	character at each position
	 */
	private String s;
	
	public StringIndividual(String s) {
		this.s = s;
	}
	
	public String getString() { return s; }
	
	public static StringIndividual random() {
		Random r = new Random();
		int length = r.nextInt(25) + 1; //nonzero length
		StringBuilder builder = new StringBuilder(length);
		for(int i=0; i<length; i++) {
			builder.append(alphabet.charAt(r.nextInt(alphabet.length())));
		}
		
		return new StringIndividual(builder.toString());
	}

	@Override
	public StringIndividual mate(StringIndividual o) {
		StringIndividual child = null;
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
			
			//with low probability, randomly permute this element
			if(r.nextDouble() < 0.1) {
				next = alphabet.charAt(r.nextInt(alphabet.length()));
			}
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
		
		//TODO: mutate the string a little
		
		child = new StringIndividual(builder.toString());
		return child;
	}
	
	public String toString() {
		return s;
	}

}
