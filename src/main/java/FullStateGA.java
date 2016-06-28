import java.math.RoundingMode;
import java.util.Random;

import com.google.common.math.IntMath;

public final class FullStateGA implements GeneticAlgorithm {

	private static final Random RANDOM = new Random();
	private byte[] chromosome;
	private double fitness;
	private int states;
	private int shift;

	/**
	 * Creates a new instance of this GA
	 * 
	 * @param fields
	 *            number of positions in the game field
	 * @param states
	 *            number of possible values (values must range from 0 to states)
	 */
	public FullStateGA(int fields, int states) {
		this.states = states;
		this.shift = IntMath.log2(states, RoundingMode.CEILING);
		chromosome = new byte[IntMath.pow(states, fields)];
		for (int i = 0; i < chromosome.length; i++) {
			chromosome[i] = (byte) RANDOM.nextInt(states);
		}
	}

	/**
	 * PRIVATE: For internal usage + unit tests!
	 * 
	 * @param initialChromosome
	 */
	FullStateGA(byte[] initialChromosome, int states) {
		this.chromosome = initialChromosome;
		this.states = states;
		this.shift = IntMath.log2(states, RoundingMode.CEILING);
	}

	@Override
	public int getMoveDirection(int[] field) {
		int hash = 0;
		for (int i = 0; i < field.length; i++) {
			hash |= field[i] << (i * shift);
		}
		return chromosome[hash];
	}

	@Override
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	@Override
	public double getFitness() {
		return fitness;
	}

	/**
	 * Mutates the chromosome with the given probability
	 */
	public void mutate(double probability) {
		final int changes = (int) (probability * chromosome.length);
		for (int i = 0; i < changes; i++) {
			chromosome[RANDOM.nextInt(chromosome.length)] = (byte) RANDOM.nextInt(states);
		}
	}

	/**
	 * Creates a new GA from itself and the given GA
	 * @param other second parent GA
	 * @return child GA
	 */
	public FullStateGA crossover(FullStateGA other) {
		if (other.chromosome.length != chromosome.length) {
			throw new IllegalArgumentException("chromosome's lengths do not match!");
		}
		final byte[] newChromosome = chromosome.clone();
		final int idx = RANDOM.nextInt(chromosome.length);
		System.arraycopy(other.chromosome, idx, newChromosome, idx, chromosome.length - idx);
		return new FullStateGA(newChromosome, states);
	}

}
