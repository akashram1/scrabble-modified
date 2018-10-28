package edu.cmu.cs.cs214.hw4.core.squares;

import java.awt.Color;

/**
 * Class represents a Double Word square which multiplies whole word score by 2.
 *
 * @author akash
 *
 */
public class DWSquare extends Square {
	/**
	 *
	 * @param x
	 *                x-coordinate
	 * @param y
	 *                y-coordinate
	 */
	public DWSquare(int x, int y) {
		super(x, y);
		this.letterFactor = 1;
		this.wordFactor = 2;
		this.name = "DW";
		if (x == 7 && y == 7) {
			this.name = "**";
		}
		this.color = new Color(255, 20, 147); // pink
	}

	@Override
	public Square copySquare() {
		int x = this.getLocation().getX();
		int y = this.getLocation().getY();
		DWSquare copy = new DWSquare(x, y);
		this.cloneHelper(copy);
		return copy;
	}

}
