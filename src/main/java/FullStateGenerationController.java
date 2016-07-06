import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class FullStateGenerationController implements GenerationController {

	private final Random random = new Random();
	private GeneticAlgorithm[] genPool;
	private ExecutorService pool;
	private CyclicBarrier barrier;
	private int cores = 1;

	public FullStateGenerationController(ExecutorService pool, int cores) {
		this.pool = pool;
		this.cores = cores;
		barrier = new CyclicBarrier(cores + 1);
	}

	public FullStateGenerationController() {
		this.pool = Executors.newSingleThreadExecutor();
		barrier = new CyclicBarrier(2);
	}

	@Override
	public GeneticAlgorithm[] breedNewGeneration(final GeneticAlgorithm[] oldGen, final double mutationRate) {
		if (oldGen == null || oldGen.length == 0 || mutationRate < 0.0 || mutationRate > 1.0
				|| !(oldGen[0] instanceof FullStateGA)) {
			throw new IllegalArgumentException();
		}
		final int size = oldGen.length;
		if (genPool == null || genPool.length != size) {
			genPool = new GeneticAlgorithm[size];
			FullStateGA ga = (FullStateGA) oldGen[0];
			for (int i = 0; i < size; i++) {
				genPool[i] = ga.makeEmptyGA();
			}
		}
		GeneticAlgorithm[] newGen = genPool;
		genPool = oldGen;
		final int raffleSize = (size * (size + 1)) / 2;
		Arrays.sort(oldGen, (ga1, ga2) -> Double.compare(ga2.getFitness(), ga1.getFitness()));

		for (int i = 0; i < cores; i++) {
			final int local = i;
			pool.execute(() -> parallelOperation(local, (local * oldGen.length) / cores,
					((local + 1) * oldGen.length) / cores, size, raffleSize, oldGen, newGen, mutationRate));
		}

		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}

		return newGen;
	}

	private void parallelOperation(int nr, int lwr, int upr, int size, int raffleSize, GeneticAlgorithm[] oldGen,
			GeneticAlgorithm[] newGen, double mutationRate) {
		for (int i = lwr; i < upr; i++) {
			// int rnd = random.nextInt(raffleSize);
			// int rnd2 = random.nextInt(raffleSize);
			// int idx1 = size - 1 - (rnd * 2) / (size + 1);
			// int idx2 = size - 1 - (rnd2 * 2) / (size + 1);
			int idx1 = random.nextInt(size/2);
			int idx2 = random.nextInt(size/2);
			FullStateGA parent1 = (FullStateGA) oldGen[idx1];
			FullStateGA parent2 = (FullStateGA) oldGen[idx2];
			FullStateGA child = parent1.crossover(parent2, (FullStateGA) newGen[i]);
			child.mutate(mutationRate);
			child.setFitness(0.0);
		}
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

}
