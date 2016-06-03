import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

public class Game {

	private int[] field;
	private int height;
	private int width;

	public Game() {
		this(4, 4);
		addRandomNumber();
		addRandomNumber();
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

	public void down() {
		moveDown();
		addRandomNumber();
	}

	public void up() {
		moveUp();
		addRandomNumber();
	}

	public void left() {
		moveLeft();
		addRandomNumber();
	}

	public void right() {
		moveRight();
		addRandomNumber();
	}

	void moveDown() {
		moveFields(getVerticalRefs(false));
	}

	void moveUp() {
		moveFields(getVerticalRefs(true));
	}

	void moveRight() {
		moveFields(getHorizontalRefs(false));
	}

	void moveLeft() {
		moveFields(getHorizontalRefs(true));
	}

	public boolean isOtherMovePossible() {
		boolean contains = ArrayUtils.contains(field, 0);
		if (contains) {
			return true;
		}
		int[] referencefield = getField();
		moveUp();
		moveDown();
		moveLeft();
		moveRight();
		int[] newField = getField();
		setField(referencefield);

		return !Objects.deepEquals(referencefield, newField);
	}

	void addRandomNumber() {
		List<Integer> freeFields = new ArrayList<>();
		for (int i = 0; i < field.length; i++) {
			if (field[i] == 0) {
				freeFields.add(i);
			}
		}
		if (freeFields.isEmpty()) {
			return;
		}
		boolean isFour = Math.random() <= 0.1;
		int addNumber = isFour ? 4 : 2;
		Collections.shuffle(freeFields);
		field[freeFields.get(0)] = addNumber;
	}

	private void moveFields(List<Integer[]> collumns) {
		for (Integer[] refs : collumns) {
			// Move all down
			List<Integer> notZeroValues = Arrays.asList(refs).stream().map(i -> field[i.intValue()]).filter(i -> i != 0)
					.collect(Collectors.toList());
			for (int j = refs.length - 1, i = 1; j >= 0; i++, j--) {
				if (notZeroValues.size() >= i) {
					field[refs[j]] = notZeroValues.get(notZeroValues.size() - i);
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
				}
				current_ref = next_ref;

			}
		}
	}

	private List<Integer[]> getVerticalRefs(boolean reverse) {
		List<Integer[]> collumns = new ArrayList<>(width);
		for (int c = 0; c < width; c++) {
			Integer[] collumn = new Integer[height];
			for (int r = 0; r < height; r++) {
				collumn[r] = c + r * height;
			}
			collumns.add(collumn);
		}
		if (reverse) {
			collumns.forEach(a -> ArrayUtils.reverse(a));
		}
		return collumns;
	}

	private List<Integer[]> getHorizontalRefs(boolean reverse) {
		List<Integer[]> rows = new ArrayList<>(height);
		for (int i = 0; i < height; i++) {
			Integer[] a = new Integer[width];
			for (int j = 0; j < width; j++) {
				a[j] = j + (i * width);
			}
			rows.add(a);
		}

		if (reverse) {
			rows.forEach(a -> ArrayUtils.reverse(a));
		}
		return rows;
	}

}
