import java.util.List;

public interface GenerationController {
	/**
	 * Breeds a new generation from the old one
	 * @param oldGen
	 * @param mutationRate
	 * @return the new generation
	 */
	List<GeneticAlgorithm> breedNewGeneration(List<GeneticAlgorithm> oldGen, double mutationRate);
}
