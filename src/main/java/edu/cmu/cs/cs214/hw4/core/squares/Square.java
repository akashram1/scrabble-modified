package edu.cmu.cs.cs214.hw4.core.squares;

import java.awt.Color;

import edu.cmu.cs.cs214.hw4.core.LetterTile;
import edu.cmu.cs.cs214.hw4.core.Tile;
import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;
import edu.cmu.cs.cs214.hw4.core.specialTile.SpecialTile;

/**
 * An abstract class that encapsulates information about a Square.
 *
 * @author akash
 *
 */
public abstract class Square {
	private Tile tile;
	private boolean occupiedByLetterState;
	private boolean occupiedBySpecialState;
	private Coordinates location;
	protected int letterFactor;
	protected int wordFactor;
	protected String name;
	protected Color color;

	public Square(int x, int y) {
		tile = null;
		this.occupiedByLetterState = false;
		this.occupiedBySpecialState = false;
		location = new Coordinates(x, y);

	}

	public void setLetterTile(LetterTile t)
	throws IllegalArgumentException {
		if (t == null) {
			throw new IllegalArgumentException(
			"Tile t cannot be null");
		}
		this.tile = new LetterTile(t);
		this.occupiedByLetterState = true;
	}

	public void setSpecialTile(SpecialTile t)
	throws IllegalArgumentException {
		if (t == null) {
			throw new IllegalArgumentException(
			"Tile t cannot be null");
		}
		this.tile = t;
		this.occupiedBySpecialState = true;
	}

	/**
	 * Returns the Tile (LetterTile/SpecialTile present in this Square).
	 *
	 * @return Tile at this square.
	 */

	public Tile getTile() {
		return this.tile;
	}

	/**
	 * Returns the Location of this square.
	 *
	 * @return Coordinates of this Square.
	 */
	public Coordinates getLocation() {
		return this.location;
	}

	/**
	 * Method to check whether the Square is occupied by a LetterTile.
	 *
	 * @return true if Square is occupied by a LetterTile. else false.
	 */
	public boolean isOccupiedByLetterTile() {
		return this.occupiedByLetterState;
	}

	/**
	 * Method to check whether the Square is occupied by a SpecialTile.
	 *
	 * @return true if Square is occupied by a SpecialTile. else false.
	 */
	public boolean isOccupiedBySpecialTile() {
		return this.occupiedBySpecialState;
	}

	public Tile removeTile() {
		Tile temp = this.tile;
		this.tile = null;
		if (this.tile instanceof LetterTile) {
			this.occupiedByLetterState = false;
		} else {
			this.occupiedBySpecialState = false;
		}
		return temp;
	}

	public int getLetterScoreFactor() {
		return this.letterFactor;
	}

	public int getWordScoreFactor() {
		return this.wordFactor;
	}

	@Override
	public String toString() {
		if (isOccupiedByLetterTile()) {
			return this.tile.toString();
		}
		return this.name;
	}

	protected void cloneHelper(Square copy) {
		if (this.isOccupiedByLetterTile()) {
			copy.setLetterTile((LetterTile) this.tile);
		} else if (this.isOccupiedBySpecialTile()) {
			copy.setSpecialTile((SpecialTile) this.tile);
		}
	}

	/**
	 * A method that returns a deep copy of the current Square.
	 *
	 * @return a copy of the current Square.
	 */
	public abstract Square copySquare();

	/**
	 * Returns the color of the Square.
	 *
	 * @return the Color instance representing the color of that square.
	 */
	public Color getColor() {
		if (isOccupiedByLetterTile() || isOccupiedBySpecialTile()) {
			return this.tile.getColor();
		}
		return this.color;
	}

}
