import java.util.Scanner;

import org.apache.commons.lang3.ArrayUtils;

public class Main {

	private static Scanner s;
	private static String[] validMoves = { "u", "d", "l", "r" };

	public static void main(String[] args) {
		System.out.println("Welcome to 2048");
		GameImpl g = new GameImpl();
		s = new Scanner(System.in);
		while (true) {
			printField(g.getField());
			performUserAction(g);
		}

	}

	private static void performUserAction(GameImpl g) {
		System.out.println("Please Enter a direction (u, d, l, r):");
		String move = null;
		while (move == null) {
			move = s.next().trim();
			if (!ArrayUtils.contains(validMoves, move)) {
				move = null;
			}
		}
		if (g.isOtherMovePossible()) {
			switch (move) {
			case "u":
				g.up();
				break;
			case "d":
				g.down();
				break;
			case "l":
				g.left();
				break;
			case "r":
				g.right();
				break;
			}
		}
	}

	private static void printField(int[] field) {
		for (int i = 0; i < field.length; i++) {
			int j = field[i];
			System.out.print(" " + j);
			if ((i + 1) % 4 == 0) {
				System.out.print("\n");
			}
		}
	}

}
