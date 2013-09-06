package genetics;

public abstract class Individual<I extends Individual<?>> {
	protected static double mutationRate;

	public static double getMutationRate() { return mutationRate; }
        public static void setMutationRate(double newRate) { 
                mutationRate = newRate; 
        }

	public abstract I mate(I o);
	
}
