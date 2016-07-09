import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

/**
 * This class runs a Simulation of 2048 using the given game, controller,
 * fitness function and genetic algorithm
 *
 */
public final class Simulation {
	private final Game[] games;
	private GeneticAlgorithm[] algorithms;
	private final GenerationController genControl;
	private int numberOfMoves;
	private int numberOfGAs;
	private final int cores;
	private double mutRate;
	private Runnable[] runnables;
	private ExecutorService pool;
	private CyclicBarrier barrier;

	private SimulationRoundResult currentRound = new SimulationRoundResult(0.0, 0.0, 0.0);
	private FitnessFunction ff;
	private long seed = 1337L;

	public Simulation(int numberOfMoves, int numberOfGAs, double mutationRate, Game[] games,
			GenerationController genControl, FitnessFunction fitnessFunction, ExecutorService pool, SpawnGA spawner) {
		cores = games.length;
		this.numberOfMoves = numberOfMoves;
		this.pool = pool;
		barrier = new CyclicBarrier(cores + 1);
		this.games = games;
		this.mutRate = mutationRate;
		this.genControl = genControl;
		this.numberOfGAs = numberOfGAs;
		this.ff = fitnessFunction;
		algorithms = new GeneticAlgorithm[numberOfGAs];
		for (int i = 0; i < this.numberOfGAs; i++) {
			algorithms[i] = spawner.spawn();
		}
		runnables = new Runnable[cores];
		for (int i = 0; i < cores; i++) {
			final int local = i;
			runnables[i] = () -> runInParallel(local, (local * numberOfGAs) / cores,
					((local + 1) * numberOfGAs) / cores);
		}

	}

	/**
	 * runs a single round in parallel
	 * 
	 * @return the result for this round
	 */
	public SimulationRoundResult runSimulation() {
		for (Runnable r : runnables) {
			pool.execute(r);
		}
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		makeStatistics();
		algorithms = genControl.breedNewGeneration(algorithms, mutRate);
		// play 16 different games in a round robin fashion
		seed += Long.MIN_VALUE >>> 3;

		return currentRound;

	}

	private void runInParallel(int nr, int lwr, int upr) {
		Game game = games[nr];
		for (int j = lwr; j < upr; j++) {
			game.reset(seed);
			int i = 0;
			GeneticAlgorithm ga = algorithms[j];
			for (; i < numberOfMoves; i++) {
				int direction = ga.getMoveDirection(game.getField());
				if (direction == 0) {
					if (!game.up()) {
						break;
					}
				} else if (direction == 1) {
					if (!game.right()) {
						break;
					}
				} else if (direction == 2) {
					if (!game.down()) {
						break;
					}
				} else if (direction == 3) {
					if (!game.left()) {
						break;
					}
				} else {
					throw new IllegalStateException();
				}
				if (game.isFinished()) {
					break;
				}
			}
			ga.setFitness(calculateFitness(game, i));
		}
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	private double calculateFitness(Game game, int moves) {
		return ff.evaluateFitness(game.getField(), moves);
	}

	private void makeStatistics() {
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		double sum = 0;
		for (GeneticAlgorithm ga : algorithms) {
			sum += ga.getFitness();
			if (ga.getFitness() > max) {
				max = ga.getFitness();
			}
			if (ga.getFitness() < min) {
				min = ga.getFitness();
			}
		}
		currentRound.maxFitness = max;
		currentRound.minFitness = min;
		currentRound.avgFitness = sum / algorithms.length;
	}

	public double getMutRate() {
		return mutRate;
	}

	public void setMutRate(double mutRate) {
		this.mutRate = mutRate;
	}

	public static interface SpawnGA {
		GeneticAlgorithm spawn();
	}
}
