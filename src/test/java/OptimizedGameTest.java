import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class OptimizedGameTest {
	
	private OptimizedGame game = new OptimizedGame();
	
	@Test
	public void testMoveSingleLine0() {
		int[] line = { 0, 0, 0, 0 };
		int[] rslt = { 0, 0, 0, 0 };
		internalTestLine(line, rslt);
	}

	@Test
	public void testMoveSingleLine1() {
		int[] line = { 0, 1, 0, 0 };
		int[] rslt = { 1, 0, 0, 0 };
		internalTestLine(line, rslt);
	}

	@Test
	public void testMoveSingleLine2() {
		int[] line = { 1, 1, 0, 0 };
		int[] rslt = { 2, 0, 0, 0 };
		internalTestLine(line, rslt);
	}

	@Test
	public void testMoveSingleLine3() {
		int[] line = { 1, 1, 1, 0 };
		int[] rslt = { 2, 1, 0, 0 };
		internalTestLine(line, rslt);
	}

	@Test
	public void testMoveSingleLine4() {
		int[] line = { 1, 1, 1, 1 };
		int[] rslt = { 2, 2, 0, 0 };
		internalTestLine(line, rslt);
	}

	@Test
	public void testMoveSingleLine5() {
		int[] line = { 0, 0, 0, 2 };
		int[] rslt = { 2, 0, 0, 0 };
		internalTestLine(line, rslt);
	}

	@Test
	public void testMoveSingleLine6() {
		int[] line = { 0, 1, 0, 1 };
		int[] rslt = { 2, 0, 0, 0 };
		internalTestLine(line, rslt);
	}

	@Test
	public void testMoveSingleLine7() {
		int[] line = { 2, 1, 2, 1 };
		int[] rslt = { 2, 1, 2, 1 };
		internalTestLine(line, rslt);
	}

	@Test
	public void testMoveSingleLine8() {
		int[] line = { 0, 1, 0, 2 };
		int[] rslt = { 1, 2, 0, 0 };
		internalTestLine(line, rslt);
	}

	@Test
	public void testMoveSingleLine9() {
		int[] line = { 3, 0, 3, 1 };
		int[] rslt = { 4, 1, 0, 0 };
		internalTestLine(line, rslt);
	}

	@Test
	public void testMoveSingleLine10() {
		int[] line = { 0, 2, 0, 2, 1, 1 };
		int[] rslt = { 3, 2, 0, 0, 0, 0 };
		internalTestLine(line, rslt);
	}

	@Test
	public void testMoveSingleLine11() {
		int[] line = { 0, 3, 2, 1, 0, 1 };
		int[] rslt = { 3, 2, 2, 0, 0, 0 };
		internalTestLine(line, rslt);
	}

	private void internalTestLine(int[] line, int[] expectedResult) {
		game.moveSingleLine(line);
		assertTrue(Arrays.toString(line), Arrays.equals(line, expectedResult));
	}

	@Test
	public void testMoveDown() {
		OptimizedGame game = new OptimizedGame(3, 2);
		int[] initial = { 1, 3, 0, 0, 3, 1 };
		int[] results = { 0, 0, 0, 1, 4, 1 };
		int[] field = game.getField();
		System.arraycopy(initial, 0, field, 0, field.length);
		assertTrue(game.moveDown());
		assertTrue(Arrays.toString(field), Arrays.equals(field, results));
	}
	
	@Test
	public void testMoveDown2() {
		OptimizedGame game = new OptimizedGame(2, 3);
		int[] initial = { 1, 0, 1, 2, 1, 0 };
		int[] results = { 0, 0, 1, 0, 2, 2 };
		int[] field = game.getField();
		System.arraycopy(initial, 0, field, 0, field.length);
		assertTrue(game.moveDown());
		assertTrue(Arrays.toString(field), Arrays.equals(field, results));
	}
	
	@Test
	public void testMoveUp() {
		OptimizedGame game = new OptimizedGame(3, 2);
		int[] initial = { 1, 3, 0, 0, 3, 1 };
		int[] results = { 1, 4, 1, 0, 0, 0 };
		int[] field = game.getField();
		System.arraycopy(initial, 0, field, 0, field.length);
		assertTrue(game.moveUp());
		assertTrue(Arrays.toString(field), Arrays.equals(field, results));
	}
	
	@Test
	public void testMoveLeft() {
		OptimizedGame game = new OptimizedGame(3, 3);
		int[] initial = { 1, 0, 1, 0, 3, 1, 1, 2, 2 };
		int[] results = { 2, 0, 0, 3, 1, 0, 1, 3, 0 };
		int[] field = game.getField();
		System.arraycopy(initial, 0, field, 0, initial.length);
		assertTrue(game.moveLeft());
		assertTrue(Arrays.toString(field), Arrays.equals(field, results));
	}
	
	@Test
	public void testMoveRight() {
		OptimizedGame game = new OptimizedGame(3, 3);
		int[] initial = { 1, 0, 1, 0, 3, 1, 1, 2, 2 };
		int[] results = { 0, 0, 2, 0, 3, 1, 0, 1, 3 };
		int[] field = game.getField();
		System.arraycopy(initial, 0, field, 0, initial.length);
		assertTrue(game.moveRight());
		assertTrue(Arrays.toString(field), Arrays.equals(field, results));
	}

}
