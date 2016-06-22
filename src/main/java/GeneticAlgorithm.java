/**
 * All GAs must implement this interface
 */
public interface GeneticAlgorithm {
	/**
	 * Calculates the move direction for a given field state based on the
	 * internal chromosome
	 * 
	 * @param field
	 *            of valid size
	 * @return 0=up, 1=right, 2=down, 3=left
	 */
	int getMoveDirection(int[] field);

	void setFitness(double fitness);

	double getFitness();
}
