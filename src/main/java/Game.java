
public interface Game {

	int[] getField();

	boolean down();

	boolean up();

	boolean left();

	boolean right();

	void reset();
	
	boolean isFinished();

}