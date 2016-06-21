import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class FastPlayFitnessFunctionTest {

	private FastPlayFitnessFunction fit;

	@Before
	public void setUp() {
		fit = new FastPlayFitnessFunction();
	}

	@Test
	public void testOnlyTwos() throws Exception {
		int[] feld = { 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		double actual = fit.evaluateFitness(feld, 0);
		double expected = 0;
		assertEquals(expected, actual, 0);
	}

	@Test
	public void testOneFour() throws Exception {
		int[] feld = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 };
		double actual = fit.evaluateFitness(feld, 0);
		double expected = 5;
		assertEquals(expected, actual, 0);
	}

	@Test
	public void testOneFourAndSomeTwos() throws Exception {
		int[] feld = { 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 4 };
		double actual = fit.evaluateFitness(feld, 0);
		int expected = 5;
		assertEquals(expected, actual, 0);
	}

	@Test
	public void testOneEight() throws Exception {
		int[] feld = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8 };
		double actual = fit.evaluateFitness(feld, 0);
		int expected = 10;
		assertEquals(expected, actual, 0);
	}

	@Test
	public void testOneEightAndAFour() throws Exception {
		int[] feld = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 8 };
		double actual = fit.evaluateFitness(feld, 0);
		int expected = 14;
		assertEquals(expected, actual, 0);
	}

}
