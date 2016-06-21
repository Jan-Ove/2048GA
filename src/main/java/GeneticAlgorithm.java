/**
 * All GAs must implement this interface
 */
public interface GeneticAlgorithm<U, V> {
	/**
	 * Calculates the move direction for a given field state based on the
	 * internal chromosome
	 * 
	 * @param field
	 *            of valid size
	 * @return 0=up, 1=right, 2=down, 3=left
	 */
	int getMoveDirection(int[] field);

	/**
	 * Mutates the internal chromosome according to the given probability
	 * 
	 * @param probability
	 */
	void mutate(U value);

	/**
	 * Creates a new GA as a crossover between itself and another GA
	 * 
	 * @param other
	 *            parent GA
	 * @return child GA
	 */
	GeneticAlgorithm<U, V> crossover(V other);

	void setFitness(double fitness);

	double getFitness();
}
