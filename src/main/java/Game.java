import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

	private int[] field;
	private int height;
	private int width;

	public Game() {
		this(4, 4);
	}

	public Game(int height, int width) {
		this.height = height;
		this.width = width;
		field = new int[height * width];
	}

	public int[] getField() {
		return field;
	}

	public void setField(int[] field) {
		if (field.length != height * width) {
			throw new IllegalArgumentException("The field has the wrong Dimensions");
		}
		this.field = field;
	}

	public void moveDown() {
		List<Integer[]> collumns = new ArrayList<>(width);
		for (int c = 0; c < width; c++) {
			Integer[] collumn = new Integer[height];
			for (int r = 0; r < height; r++) {
				collumn[r] = c + r * height;
			}
			collumns.add(collumn);
		}
		for (Integer[] refs : collumns) {
			// Move all down
			List<Integer> uniqueValues = Arrays.asList(refs).stream().map(i -> field[i.intValue()]).filter(i -> i != 0)
					.collect(Collectors.toList());
			for (int j = refs.length - 1, i = 1; j >= 0; i++, j--) {
				if (uniqueValues.size() >= i) {
					field[refs[j]] = uniqueValues.get(uniqueValues.size() - i);
				} else {
					field[refs[j]] = 0;
				}
			}
			// Build new Pairs
			int current_ref = refs[refs.length - 1];
			for (int i = refs.length - 2; i >= 0; i--) {
				int next_ref = refs[i];
				int a = field[current_ref];
				int b = field[next_ref];
				if (b != 0 && (a == b || a == 0)) {
					field[current_ref] = a + b;
					for (int j = i; j >= 0; j--) {
						int before = 0;
						if (j != 0) {
							before = field[refs[j - 1]];
						}
						field[refs[j]] = before;
					}
				} else {
					current_ref = next_ref;
				}
			}
		}
	}

}
