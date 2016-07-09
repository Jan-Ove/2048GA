
public interface Game {

	/**
	 * 
	 * @return the underlying field of this game
	 */
	int[] getField();

	/**
	 * Make a downward move.
	 * 
	 * @return true if field has moved
	 */
	boolean down();

	/**
	 * Make a upward move.
	 * 
	 * @return true if field has moved
	 */
	boolean up();

	/**
	 * Make a move to the left.
	 * 
	 * @return true if field has moved
	 */
	boolean left();

	/**
	 * Make a move to the right
	 * 
	 * @return true if field has moved
	 */
	boolean right();

	/**
	 * Resets the game to its initial state
	 * 
	 * @param seed
	 *            to use for random number generators
	 */
	void reset(long seed);

	/**
	 * 
	 * @return true if highest field value has been reached or no further
	 *         movements are possible
	 */
	boolean isFinished();

}