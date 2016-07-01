
public final class SimulationRoundResult {
	public double maxFitness;
	public double minFitness;
	public double avgFitness;

	public SimulationRoundResult(double maxFitness, double minFitness, double avgFitness) {
		this.maxFitness = maxFitness;
		this.minFitness = minFitness;
		this.avgFitness = avgFitness;
	}
	
	@Override
	public String toString() {
		return String.format("Fitness (max, min, avg): %s %s %s ", maxFitness, minFitness, avgFitness);
	}
}
