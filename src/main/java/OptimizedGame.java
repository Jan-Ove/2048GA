import java.util.Random;

public final class OptimizedGame implements Game {
	private final int[] field;
	private final int height;
	private final int width;
	private final Random rnd = new Random();
	private final int[] lineH;
	private final int[] lineW;
	private final int maxValue;
	private boolean isFinished;

	public OptimizedGame() {
		this(4, 4, 12);
	}

	public OptimizedGame(int w, int h) {
		this(w, h, w + h);
	}

	public OptimizedGame(int w, int h, int states) {
		height = h;
		width = w;
		field = new int[w * h];
		lineW = new int[w];
		lineH = new int[h];
		maxValue = states - 1;
		addRandomNumber();
		addRandomNumber();
	}

	public void reset(long seed) {
		rnd.setSeed(seed);
		for (int i = 0; i < field.length; i++) {
			field[i] = 0;
		}
		addRandomNumber();
		addRandomNumber();
	}

	public int[] getField() {
		return field;
	}

	public boolean down() {
		if (moveDown()) {
			addRandomNumber();
			return true;
		} else {
			return false;
		}
	}

	public boolean up() {
		if (moveUp()) {
			addRandomNumber();
			return true;
		} else {
			return false;
		}
	}

	public boolean left() {
		if (moveLeft()) {
			addRandomNumber();
			return true;
		} else {
			return false;
		}
	}

	public boolean right() {
		if (moveRight()) {
			addRandomNumber();
			return true;
		} else {
			return false;
		}
	}

	boolean moveUp() {
		boolean hasMoved = false;
		for (int column = 0; column < width; column++) {
			for (int i = 0; i < height; i++) {
				lineH[i] = field[column + i * width];
			}
			hasMoved |= moveSingleLine(lineH);
			for (int i = 0; i < height; i++) {
				field[column + i * width] = lineH[i];
			}
		}
		return hasMoved;
	}

	boolean moveDown() {
		boolean hasMoved = false;
		for (int column = 0; column < width; column++) {
			for (int i = 0; i < height; i++) {
				lineH[height - 1 - i] = field[column + i * width];
			}
			hasMoved |= moveSingleLine(lineH);
			for (int i = 0; i < height; i++) {
				field[column + i * width] = lineH[height - 1 - i];
			}
		}
		return hasMoved;
	}

	boolean moveLeft() {
		boolean hasMoved = false;
		for (int row = 0; row < height; row++) {
			for (int i = 0; i < height; i++) {
				lineW[i] = field[i + row * width];
			}
			hasMoved |= moveSingleLine(lineW);
			for (int i = 0; i < height; i++) {
				field[i + row * width] = lineW[i];
			}
		}
		return hasMoved;
	}

	boolean moveRight() {
		boolean hasMoved = false;
		for (int row = 0; row < height; row++) {
			for (int i = 0; i < height; i++) {
				lineW[width - 1 - i] = field[i + row * width];
			}
			hasMoved |= moveSingleLine(lineW);
			for (int i = 0; i < height; i++) {
				field[i + row * width] = lineW[width - 1 - i];
			}
		}
		return hasMoved;
	}

	boolean moveSingleLine(int[] line) {
		int emptyIndex = -1;
		int fullIndex = -1;
		boolean hasMoved = false;
		for (int i = 0; i < line.length; i++) {
			if (line[i] == 0) {
				if (emptyIndex < 0) {
					emptyIndex = i;
				}
				continue;
			}
			if (fullIndex >= 0 && line[fullIndex] == line[i]) {
				line[fullIndex]++;
				if (line[fullIndex] == maxValue) {
					isFinished = true;
				}
				line[i] = 0;
				emptyIndex = fullIndex + 1;
				fullIndex = -1;
				hasMoved = true;
			} else if (emptyIndex >= 0) {
				line[emptyIndex] = line[i];
				line[i] = 0;
				fullIndex = emptyIndex;
				emptyIndex++;
				hasMoved = true;
			} else {
				fullIndex = i;
			}
		}
		return hasMoved;
	}

	private void addRandomNumber() {
		int free = 0;
		for (int f : field) {
			if (f == 0) {
				free++;
			}
		}
		if (free > 0) {
			int pos = rnd.nextInt(free) + 1;
			int i = 0;
			for (; i < field.length; i++) {
				if (field[i] == 0) {
					pos--;
					if (pos == 0) {
						break;
					}
				}
			}
			field[i] = rnd.nextDouble() <= 0.1 ? 2 : 1;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}
}
