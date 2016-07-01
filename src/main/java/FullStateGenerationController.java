import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class FullStateGenerationController implements GenerationController {

	private final static Random RANDOM = new Random();

	@Override
	public List<GeneticAlgorithm> breedNewGeneration(List<GeneticAlgorithm> oldGen, double mutationRate) {
		if (oldGen == null || oldGen.isEmpty() || mutationRate < 0.0 || mutationRate > 1.0
				|| !(oldGen.get(0) instanceof FullStateGA)) {
			throw new IllegalArgumentException();
		}
		final int size = oldGen.size();
		final List<GeneticAlgorithm> newGen = new ArrayList<>(size);
		final int raffleSize = (size * (size + 1)) / 2;
		oldGen.sort((ga1, ga2) -> Double.compare(ga1.getFitness(), ga2.getFitness()));
		for (int i = 0; i < size; i++) {
			int idx1 = size - 1 - (RANDOM.nextInt(raffleSize) * 2) / (size + 1);
			int idx2 = size - 1 - (RANDOM.nextInt(raffleSize) * 2) / (size + 1);
			FullStateGA parent1 = (FullStateGA) oldGen.get(idx1);
			FullStateGA parent2 = (FullStateGA) oldGen.get(idx2);
			FullStateGA child = parent1.crossover(parent2);
			child.mutate(mutationRate);
			newGen.add(child);
		}
		return newGen;
	}

}
