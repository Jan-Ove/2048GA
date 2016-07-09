
public interface FitnessFunction {

	/**
	 * Calculates the score of the given field
	 * 
	 * @param field
	 *            game field
	 * @param move
	 *            number of moves made to create this field
	 * @return score for this specific field
	 */
	double evaluateFitness(int[] field, int move);

}
