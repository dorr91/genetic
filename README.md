genetic is a library for creating, applying, and tuning genetic algorithms.
It consists of a package, genetics, containing a few classes, along with an example in genetics.example

Code structure:
package genetics
	class Species:
		abstract class; subclasses represent a species and 
			instances of the subclass are members of the species
		contains the fitness function as an instance method score()
		contains the instance method mate(Individual other) for 
			sexual reproduction
	class Population:
		represents a group of members of a species
		keeps track of each generation
		determines which individuals should mate 
			(and calls their mate function)
	class Tuner:
		Tests different combinations of parameters in order to improve
			genetic algorithm performance
		(Should use a genetic algorithm? I like it...)

genetics.example
	class StringSpecies
		represents a string
	class StringRunner:
		creates a population of strings, specifies a target string, 
			and evolves the population until the target string 
			is generated.

Running the example:
	java genetics.example.StringRunner

Usage:
	create a subclass of Species with the signature 
		Subclass extends Species<Subclass>
	override the methods score() and mate()
	create a new Population<Subclass>, passing in the initial generation
		and the number from each generation that should survive
	call generate(), check the best() individual encountered so far
	rinse and repeat
	
