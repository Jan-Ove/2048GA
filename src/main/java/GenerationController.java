import java.util.List;

public interface GenerationController<U, V> {
	/**
	 * Breeds a new generation from the old one
	 * 
	 * @param oldGen
	 * @param mutationRate
	 * @return the new generation
	 */
	List<GeneticAlgorithm<U, V>> breedNewGeneration(List<GeneticAlgorithm<U, V>> oldGen, double mutationRate);
}
