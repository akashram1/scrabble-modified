package edu.cmu.cs.cs214.hw4.core.coordinates;

import java.util.Comparator;

/**
 * A class that defines custom sorting of Coordinates.
 *
 * @author akash
 *
 */
public class CompareCoordinates implements Comparator<Coordinates> {
	/**
	 * This method can be called on 2 coordinates only if 1 of the following
	 * 2 conditions are satisfied : 1: They have same x coordinates OR 2.
	 * They have same y coordinates. Therefore it will sort a horizontal and
	 * vertical move by increasing y and x coordinates respectively *
	 */
	@Override
	public int compare(Coordinates c1, Coordinates c2) {
		if (c1.getX() == c2.getX()) {
			return c1.getY() - c2.getY();
		} else if (c1.getY() == c2.getY()) {
			return c1.getX() - c2.getX();
		} else {
			throw new IllegalStateException(
			"method can be invoked only if "
			+ "2 coordinates are in the"
			+ " same row OR same column.");
		}

	}

}
