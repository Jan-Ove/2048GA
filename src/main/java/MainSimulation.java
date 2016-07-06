import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class MainSimulation {

	public static void main(String[] args) {
		// configurable:
		int numberOfGAs = 128;
		int numberOfMoves = 40;
		int rounds = 200;
		double mutationRate = 0.01;
		
		// do not touch
		int cores = Runtime.getRuntime().availableProcessors();
		ExecutorService pool = Executors.newFixedThreadPool(cores);
		Game[] games = new Game[cores];
		for (int i = 0; i < cores; i++) {
			games[i] = new OptimizedGame(3, 3, 8);
		}

		Simulation sim = new Simulation(numberOfMoves, numberOfGAs, mutationRate, games, new FullStateGenerationController(pool, cores),
				new FullStateFitnessFunction(), pool, new Simulation.SpawnGA() {
					public GeneticAlgorithm spawn() {
						return new FullStateGA(9, 8);
					}
				});
		double totalMaximum = 0.0;
		for (int i = 0; i < rounds; i++) {
			SimulationRoundResult currentRound = sim.runSimulation();
			totalMaximum = currentRound.maxFitness > totalMaximum ? currentRound.maxFitness : totalMaximum;
			System.out.println(currentRound.toString());
		}
		System.out.println("Highest all-time maximum: " + totalMaximum);
		pool.shutdown();
	}

}
