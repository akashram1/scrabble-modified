package edu.cmu.cs.cs214.hw4.core.coordinates;

/**
 * A class that encapsules the x and y coordinates.
 *
 * @author akash
 *
 */
public class Coordinates {
	private int x;
	private int y;

	/**
	 * Constructor that initializes a coordinate to the x and y values
	 * passed.
	 *
	 * @param x
	 *                coordinate.
	 * @param y
	 *                coordinate.
	 */
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Copy constructor
	 *
	 * @param c
	 */
	public Coordinates(Coordinates c) {
		this.x = c.x;
		this.y = c.y;
	}

	/**
	 * Returns x coordinate.
	 *
	 * @return x coordinate int.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Returns y coordinate.
	 *
	 * @return y coordinate int.
	 */
	public int getY() {
		return this.y;
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Coordinates)) {
			return false;
		}

		Coordinates temp = (Coordinates) obj;

		if (temp.getX() == this.getX() && temp.getY() == this.getY()) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {

		return 31 * this.getX() + this.getY();
	}

	@Override
	public String toString() {
		return "(" + this.getX() + ", " + this.getY() + ")";
	}

}
