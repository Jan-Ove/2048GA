
public final class SimulationRoundResult {
	public int maxFitness;
	public int minFitness;
	public int avgFitness;

	public SimulationRoundResult(int maxFitness, int minFitness, int avgFitness) {
		this.maxFitness = maxFitness;
		this.minFitness = minFitness;
		this.avgFitness = avgFitness;
	}
}
