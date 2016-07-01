import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public final class FullStateFitnessFunction implements FitnessFunction {

	@Override
	public double evaluateFitness(int[] field, int move) {
		int[] values = Arrays.copyOf(field, field.length);
		Arrays.sort(values);
		ArrayUtils.reverse(values);
		if (field[0] < 2) {
			return 0.0;
		}
		
		double fitness = 0;

		if (values[0] == 7) {
			fitness = 150 - move;
		}
		for(int v: values) {
			fitness += v* 5;
			if(v == 1 || v == 0) {
				break;
			}
		}
		return fitness;
	}

}
