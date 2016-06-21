import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.math.DoubleMath;
import com.google.common.primitives.Ints;

public class FastPlayFitnessFunction implements FitnessFunction {

	@Override
	public double evaluateFitness(int[] field, int move) {
		List<Integer> f = Ints.asList(field).stream().filter(i -> i >= 4).collect(Collectors.toList());
		if (f.size() == 0) {
			return 0;
		}

		Collections.sort(f);
		Collections.reverse(f);

		double fitness = 0;

		if (field[0] == 2048) {
			fitness = 300 - move;
		}

		int max = f.get(0);
		fitness = ((Math.log(max) / Math.log(2)) - 1) * 5;
		f.remove(0);
		if (!f.isEmpty()) {
			fitness += DoubleMath.mean(f);
		}

		return fitness;
	}

}
