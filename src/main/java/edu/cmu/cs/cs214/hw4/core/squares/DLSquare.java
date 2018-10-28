package edu.cmu.cs.cs214.hw4.core.squares;

import java.awt.Color;

/**
 * Class represents a Double Letter square which multiplies letter tile score by
 * 2.
 *
 * @author akash
 *
 */
public class DLSquare extends Square {
	/**
	 *
	 * @param x
	 *                x coordinate
	 * @param y
	 *                y coordinate
	 */
	public DLSquare(int x, int y) {
		super(x, y);
		this.letterFactor = 2;
		this.wordFactor = 1;
		this.name = "DL";
		this.color = new Color(0, 255, 255); // cyan
	}

	@Override
	public Square copySquare() {
		int x = this.getLocation().getX();
		int y = this.getLocation().getY();
		DLSquare copy = new DLSquare(x, y);
		this.cloneHelper(copy);
		return copy;
	}

}
