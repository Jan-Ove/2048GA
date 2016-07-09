import java.math.RoundingMode;
import java.util.Random;

import com.google.common.math.IntMath;

public final class FullStateGA implements GeneticAlgorithm {

	private static final Random RANDOM = new Random();
	private int[] chromosome;
	private double fitness;
	private int shift;

	/**
	 * Creates a new instance of this GA
	 * 
	 * @param fields
	 *            number of positions in the game field
	 * @param states
	 *            number of possible values (values must range from 0 to
	 *            states[exclusive])
	 */
	public FullStateGA(int fields, int states) {
		this.shift = IntMath.log2(states, RoundingMode.CEILING);
		chromosome = new int[IntMath.pow(states, fields) / 16];
		for (int i = 0; i < chromosome.length; i++) {
			chromosome[i] = RANDOM.nextInt();
		}
	}

	/**
	 * PRIVATE: For internal usage + unit tests!
	 * 
	 * @param initialChromosome
	 */
	FullStateGA(int[] initialChromosome, int shift) {
		this.chromosome = initialChromosome;
		this.shift = shift;
	}

	FullStateGA makeEmptyGA() {
		return new FullStateGA(new int[chromosome.length], shift);
	}

	@Override
	public int getMoveDirection(int[] field) {
		int hash = 0;
		for (int i = 0; i < field.length; i++) {
			hash |= field[i] << (i * shift);
		}
		return (chromosome[hash / 16] >> (hash & 0B110)) & 0B11;
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
		final int changes = (int) (probability * chromosome.length) / 16;
		for (int i = 0; i < changes; i++) {
			chromosome[RANDOM.nextInt(chromosome.length)] = RANDOM.nextInt();
		}
	}

	/**
	 * Creates a new GA from itself and the given GA
	 * 
	 * @param other
	 *            second parent GA
	 * @return child GA
	 */
	public FullStateGA crossover(FullStateGA parent, FullStateGA child) {
		if (parent.chromosome.length != chromosome.length || child.chromosome.length != chromosome.length) {
			throw new IllegalArgumentException("chromosome's lengths do not match!");
		}
		final int idx = RANDOM.nextInt(chromosome.length);
		System.arraycopy(parent.chromosome, idx, child.chromosome, idx, chromosome.length - idx);
		System.arraycopy(chromosome, 0, child.chromosome, 0, idx);
		return child;
	}

}
