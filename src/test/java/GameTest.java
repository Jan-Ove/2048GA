import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	private Game game;

	@Before
	public void setUp() {
		game = new Game();
	}

	@Test(expected = IllegalArgumentException.class)
	public void setFieldRejectsFieldWithWrongSize() throws Exception {
		int[] field = new int[15];
		game.setField(field);
	}

	@Test
	public void setFieldAcceptFieldWithRightSize() throws Exception {
		int[] field = new int[16];
		game.setField(field);
		assertArrayEquals(field, game.getField());
	}

	@Test
	public void moveDown() throws Exception {
		int[] field = { 2, 0, 2, 0, 2, 0, 0, 4, 2, 8, 0, 8, 4, 8, 16, 8 };
		int[] expected = { 0, 0, 0, 0, 2, 0, 0, 0, 4, 0, 2, 4, 4, 16, 16, 16 };
		game.setField(field);
		game.moveDown();

		assertArrayEquals(expected, game.getField());
	}

	@Test
	public void moveUp() throws Exception {
		int[] field = { 2, 2, 0, 2, 2, 2, 0, 0, 4, 2, 0, 0, 0, 0, 2, 2 };
		int[] expected = { 4, 4, 2, 4, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		game.setField(field);
		game.moveUp();

		assertArrayEquals(expected, game.getField());
	}

}
