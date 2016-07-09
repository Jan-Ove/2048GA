import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CMD based Simulation runner of genetic algorithm playing 2048
 */
public final class MainSimulation {
	/**
	 * Entry point for the simulation
	 * 
	 * @param args
	 *            command line arguemnts
	 */
	public static void main(String[] args) {
		// configurable via command line:
		double mutationRate = 0.01;
		int numberOfGAs = 32;
		int rounds = 200;
		int numberOfMoves = 40;

		switch (args.length) {
		case 4:
			numberOfMoves = Integer.parseInt(args[3]);
		case 3:
			rounds = Integer.parseInt(args[2]);
		case 2:
			numberOfGAs = Integer.parseInt(args[1]);
		case 1:
			mutationRate = Double.parseDouble(args[0]);
		default:
			break;
		}

		// do not touch
		int cores = Runtime.getRuntime().availableProcessors();
		ExecutorService pool = Executors.newFixedThreadPool(cores);
		Game[] games = new Game[cores];

		for (int i = 0; i < cores; i++) {
			games[i] = new OptimizedGame(3, 3, 8);
		}

		System.out.println("Initialising.. this will take some time.");
		Simulation sim = new Simulation(numberOfMoves, numberOfGAs, mutationRate, games,
				new FullStateGenerationController(pool, cores), new FullStateFitnessFunction(), pool,
				new Simulation.SpawnGA() {
					public GeneticAlgorithm spawn() {
						return new FullStateGA(9, 8);
					}
				});
		System.out.println("Startup complete: Running simulation now!");
		double totalMaximum = 0.0;
		for (int i = 0; i < rounds; i++) {
			SimulationRoundResult currentRound = sim.runSimulation();
			totalMaximum = currentRound.maxFitness > totalMaximum ? currentRound.maxFitness : totalMaximum;
			System.out.println(currentRound.toString());
		}
		System.out.println("Finished, highest all-time maximum: " + totalMaximum);
		pool.shutdown();
	}

}
