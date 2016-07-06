public interface GenerationController {
	/**
	 * Breeds a new generation from the old one
	 * 
	 * @param oldGen
	 * @param mutationRate
	 * @return the new generation
	 */
	GeneticAlgorithm[] breedNewGeneration(GeneticAlgorithm[] oldGen, double mutationRate);
}
