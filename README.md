genetic is a library for creating, applying, and tuning genetic algorithms.
It consists of a package, genetics, containing a few classes, along with some unpackaged example code.

Code structure:
package genetics
	class Individual:
		represents an individual of a given species.
		contains the information necessary to reproduce using the method
			mate(Individual other)
	class Population:
		represents a group of Individuals
		implements the fitness function
		keeps track of each generation
		determines which individuals should mate 
			(and calls their mate function)
	class Tuner:
		Tests different combinations of parameters in order to improve
			genetic algorithm performance
		(Should use a genetic algorithm? I like it...)

class StringIndividual:
	represents a string
class StringPopulation: (necessary?)
	represents a population of strings
class StringPopRunner:
	creates a string population, specifies a target string, and evolves 
		the population until the target string is generated.
