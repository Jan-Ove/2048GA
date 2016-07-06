import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FullStateGenerationControllerTest {

	@Test
	public void testBreedNewGeneration() {
		GeneticAlgorithm[] oldGen = new GeneticAlgorithm[123];
		for (int i = 0; i < 123; i++) {
			FullStateGA ga = new FullStateGA(4, 4);
			ga.setFitness(10.0);
			oldGen[i] = ga;
		}
		FullStateGenerationController controller = new FullStateGenerationController();
		GeneticAlgorithm[] newGen = controller.breedNewGeneration(oldGen, 0.125);
		assertEquals(oldGen.length, newGen.length);
		for (GeneticAlgorithm ga : newGen) {
			assertTrue(ga.getFitness() == 0.0);
		}
	}

}
