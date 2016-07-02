
public final class MainSimulation {

	public static void main(String[] args) {
		int numberOfGAs = 32;
		int rounds = 100;
		double mutationRate = 0.00005;

		Simulation sim = new Simulation(numberOfGAs, mutationRate, new OptimizedGame(3, 3, 8),
				new FullStateGenerationController(), new FullStateFitnessFunction(), new Simulation.SpawnGA() {

					@Override
					public GeneticAlgorithm spawn() {
						return new FullStateGA(9, 8);
					}
				});
		for (int i = 0; i < rounds; i++) {
			SimulationRoundResult currentRound = sim.runSimulation();
			System.out.println(currentRound.toString());
		}
	}

}
