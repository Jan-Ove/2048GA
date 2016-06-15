import java.util.ArrayList;
import java.util.List;

public final class Simulation {
	private final Game game;
	private List<GeneticAlgorithm> algorithms;
	private final GenerationController genControl;
	private int numberOfMoves = 100;
	private int numberOfGAs = 4;
	private double mutRate = 0.0001;
	private SimulationRoundResult currentRound;

	public Simulation(int fieldLength, int numberOfGAs, Class<GeneticAlgorithm> clazz, GenerationController genControl)
			throws InstantiationException, IllegalAccessException {
		game = new Game(fieldLength, fieldLength);
		this.genControl = genControl;
		this.numberOfGAs = numberOfGAs;
		algorithms = new ArrayList<>(numberOfGAs);
		for (int i = 0; i < numberOfGAs; i++) {
			algorithms.set(i, clazz.newInstance());
		}
	}

	public SimulationRoundResult runSimulation() {
		for (GeneticAlgorithm ga : algorithms) {
			game.reset();
			for (int i = 0; i < numberOfMoves; i++) {
				int direction = ga.getMoveDirection(game.getField());
				if (direction == 0)
					game.up();
				else if (direction == 1)
					game.right();
				else if (direction == 2)
					game.down();
				else if (direction == 3)
					game.left();
				else
					throw new IllegalStateException();
			}
			ga.setFitness(calculateFitness());
		}
		algorithms = genControl.breedNewGeneration(algorithms, mutRate);
		makeStatistics();
		return currentRound;
	}

	private int calculateFitness() {
		game.getField();
		// do sth
		return 0;
	}
	
	private void makeStatistics() {
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		int sum = 0;
		for(GeneticAlgorithm ga: algorithms) {
			sum += ga.getFitness();
			if(ga.getFitness() > max) {
				max = ga.getFitness();
			}
			if(ga.getFitness() < min) {
				min = ga.getFitness();
			}
		}
		currentRound.maxFitness = max;
		currentRound.minFitness = min;
		currentRound.avgFitness = sum / algorithms.size();
	}
}
