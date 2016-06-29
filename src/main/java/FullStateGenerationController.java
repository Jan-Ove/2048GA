import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class FullStateGenerationController implements GenerationController {
	
	private final static Random RANDOM = new Random();

	@Override
	public List<GeneticAlgorithm> breedNewGeneration(List<GeneticAlgorithm> oldGen, double mutationRate) {
		final int size = oldGen.size();
		final List<GeneticAlgorithm> newGen = new ArrayList<>(size);
		final int raffleSize = (size*(size+1))/2;
		oldGen.sort((ga1, ga2) -> Double.compare(ga1.getFitness(), ga2.getFitness()));
		for(int i = 0; i < size; i++) {
			FullStateGA parent1 = (FullStateGA) oldGen.get(size - 1 - (RANDOM.nextInt(raffleSize)*2)/(size+1));
			FullStateGA parent2 = (FullStateGA) oldGen.get(size - 1 - (RANDOM.nextInt(raffleSize)*2)/(size+1));
			newGen.add(parent1.crossover(parent2));
		}
		return newGen;
	}

}
