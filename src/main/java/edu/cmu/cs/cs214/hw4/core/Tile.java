package edu.cmu.cs.cs214.hw4.core;

import java.awt.Color;

import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;

/**
 * Interface represents a tile with common tile features setters like
 * getCoordinates and getters like getPoints
 *
 * @author akash
 *
 */
public interface Tile {
	/**
	 * This method should be invoked when a tile is set in some square on
	 * the board.
	 *
	 * @param c
	 *                The coordinates where the tile is set.
	 */
	void setCoordinates(Coordinates c);

	/**
	 * Returns the coordinates where Tile is located.
	 *
	 * @return Coordinates
	 */
	Coordinates getCoordinates();

	/**
	 * Returns the points of the Tile.
	 *
	 * @return points of Tile
	 */
	int getPoints();

	Color getColor();

	@Override
	public String toString();
}
