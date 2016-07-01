import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	private GameImpl game;

	@Before
	public void setUp() {
		game = new GameImpl();
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

	@Test
	public void moveRight() throws Exception {
		int[] field = { 0, 0, 2, 2, 0, 2, 2, 2, 2, 0, 2, 0, 4, 0, 4, 2 };
		int[] expected = { 0, 0, 0, 4, 0, 0, 2, 4, 0, 0, 0, 4, 0, 0, 8, 2 };

		game.setField(field);
		game.moveRight();

		assertArrayEquals(expected, game.getField());
	}

	@Test
	public void moveLeft() throws Exception {
		int[] field = { 2, 2, 0, 0, 2, 0, 0, 2, 0, 0, 2, 2, 2, 2, 2, 2 };
		int[] expected = { 4, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 4, 4, 0, 0 };

		game.setField(field);
		game.moveLeft();

		assertArrayEquals(expected, game.getField());

	}

}
