package edu.cmu.cs.cs214.hw4.core;

import java.awt.Color;

import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;

/**
 * Class representation of a LetterTile.
 *
 * @author akash
 *
 */
public class LetterTile implements Tile {
	private Character letter;
	private int points;
	private Coordinates location;
	private int ID;
	public static final Color color = new Color(222, 184, 135);

	/**
	 * Initializes the letter of this LetterTile to letter
	 *
	 * @param letter
	 *                The letter
	 */
	public LetterTile(char letter) {
		this.letter = letter;
		this.points = 1;
		this.ID = 1;
		this.location = new Coordinates(-1, -1);
	}

	public LetterTile(char letter, int points, int ID) {
		this.letter = letter;
		this.points = points;
		// (-1,-1) means letter not placed on board.
		this.location = new Coordinates(-1, -1);
		this.ID = ID;
	}

	/**
	 * Copy constructor.
	 *
	 */

	public LetterTile(LetterTile lt) {
		this.letter = lt.getLetter();
		this.points = lt.getPoints();
		this.location = new Coordinates(lt.getCoordinates());
		this.ID = lt.getID();
	}

	/**
	 * Returns the alphabet that this letter represents.
	 *
	 * @return the LetterTile's letter.
	 */
	public char getLetter() {
		return this.letter;
	}

	@Override
	public int getPoints() {
		return this.points;
	}

	@Override
	public void setCoordinates(Coordinates c) throws NullPointerException {

		if (c == null) {
			throw new NullPointerException(
			"Coordinates c can't be null");
		}
		this.location = new Coordinates(c.getX(), c.getY());
	}

	@Override
	public Coordinates getCoordinates() {

		Coordinates temp = new Coordinates(this.location.getX(),
		this.location.getY());

		return temp;
	}

	public int getID() {
		return this.ID;
	}

	@Override
	public String toString() {
		return String.valueOf(getLetter());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LetterTile)) {
			return false;
		}

		LetterTile that = (LetterTile) obj;
		if (this.letter.equals(that.letter) && this.ID == that.ID) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31 * this.letter + this.ID;
	}

	@Override
	public Color getColor() {
		return color;
	}

}
