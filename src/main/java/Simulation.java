import java.util.ArrayList;
import java.util.List;

public final class Simulation {
	private final Game game;
	private List<GeneticAlgorithm> algorithms;
	private final GenerationController genControl;
	private int numberOfMoves = 100;
	private int numberOfGAs = 4;
	private double mutRate = 0.0001;

	private SimulationRoundResult currentRound = new SimulationRoundResult(0.0, 0.0, 0.0);
	private FitnessFunction ff;

	public Simulation(int numberOfGAs, double mutationRate, Game game, GenerationController genControl,
			FitnessFunction fitnessFunction, SpawnGA spawner) {
		this.game = game;
		this.mutRate = mutationRate;
		this.genControl = genControl;
		this.numberOfGAs = numberOfGAs;
		this.ff = fitnessFunction;
		algorithms = new ArrayList<>(numberOfGAs);
		for (int i = 0; i < this.numberOfGAs; i++) {
			algorithms.add(spawner.spawn());
		}
	}

	public SimulationRoundResult runSimulation() {
		for (GeneticAlgorithm ga : algorithms) {
			game.reset();
			int i = 0;
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
			ga.setFitness(calculateFitness(i));
		}
		makeStatistics();
		algorithms = genControl.breedNewGeneration(algorithms, mutRate);
		return currentRound;

	}

	private double calculateFitness(int moves) {
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
		currentRound.avgFitness = sum / algorithms.size();
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
