import static org.junit.Assert.*;

import org.junit.Test;

public class FullStateGATest {
	private static final byte[] INI_CHROM_0 = new byte[256];
	private int[] field;
	private byte[] chromo;
	private FullStateGA ga;
	private int states;

	@Test
	public void testGetFixMoveDirection() {
		int[] field = {3,3,3,3};
		ga = new FullStateGA(INI_CHROM_0, 2);
		assertEquals(0, ga.getMoveDirection(field));
		field[0] = 0;
		assertEquals(0, ga.getMoveDirection(field));
	}
	
	@Test
	public void testGetBasicMoveDirection() {
		field = new int[4];
		chromo = INI_CHROM_0.clone();
		states = 4;
		ga = new FullStateGA(chromo, 2);
		
		for(int i = 0; i < 256; i++) {
			testCorrectIndexWithValues4(i,i);
		}
		
	}
	
	private void testCorrectIndexWithValues4(int fieldValue, int chromoIndex) {
		setFieldsForValue(fieldValue);
		chromo[chromoIndex] = 1;
		assertEquals(1, ga.getMoveDirection(field));
		chromo[chromoIndex] = 0;
	}
	
	private void setFieldsForValue(int value) {
		field[0] = value & 0B11;
		field[1] = (value >> 2) & 0B11;
		field[2] = (value >> 4) & 0B11;
		field[3] = (value >> 6) & 0B11;
	}

	@Test
	public void testPublicConstructor() {
		states = 4;
		field = new int[4];
		ga = new FullStateGA(4, states);
		for(int i = 0; i < 256; i++) {
			setFieldsForValue(i);
			int dir = ga.getMoveDirection(field);
			assertTrue(dir >= 0 && dir <= 3);
		}
	}
	
	@Test
	public void testMutation() {
		states = 8;
		field = new int[4];
		ga = new FullStateGA(4, states);
		int dir = ga.getMoveDirection(field);
		
		do {
			ga.mutate(1.0);
		} while (dir == ga.getMoveDirection(field));

	}
	
	@Test
	public void testCrossover() {
		states = 8;
		ga = new FullStateGA(4, states);
		FullStateGA other = new FullStateGA(4, states);
		FullStateGA child = ga.crossover(other);
		assertNotEquals(other, child);
		assertNotEquals(ga, child);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalCrossover() {
		ga = new FullStateGA(4, 4);
		ga.crossover(new FullStateGA(8, 8));
	}
}
