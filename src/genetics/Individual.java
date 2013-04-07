package genetics;

public abstract class Individual<I extends Individual<?>> {
	
	public abstract I mate(I o);
	
}
