package genetics;

/* S extends Species<?> is used to allow reference to the subclass that is 
	extending Species, for example as a parameter or return type */
public abstract class Species<S extends Species<?>> {
	protected static double mutationRate;

	public abstract double score();

	public abstract S mate(S o, double mutationRate);
}
