import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;
public class FullStateGenerationControllerTest {

	@Test
	public void testBreedNewGeneration() {
		List<GeneticAlgorithm> oldGen = new ArrayList<>(123);
		for(int i = 0; i < 123; i++) {
			FullStateGA ga = new FullStateGA(4, 4);
			ga.setFitness(10.0);
			oldGen.add(ga);
		}
		FullStateGenerationController controller = new FullStateGenerationController();
		List<GeneticAlgorithm> newGen = controller.breedNewGeneration(oldGen, 0.125);
		assertEquals(oldGen.size(), newGen.size());
		for(GeneticAlgorithm ga: newGen){
			assertTrue(ga.getFitness() == 0.0);
		}
	}

}
