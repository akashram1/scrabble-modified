package edu.cmu.cs.cs214.hw4.core.squares;

import java.awt.Color;

/**
 * Class represents a Triple Word square which multiplies letter whole word
 * score by 3.
 *
 * @author akash
 *
 */
public class TWSquare extends Square {
	/**
	 *
	 * @param x
	 *                x coordinate
	 * @param y
	 *                y coordinate
	 */
	public TWSquare(int x, int y) {
		super(x, y);
		this.letterFactor = 1;
		this.wordFactor = 3;
		this.name = "TW";
		this.color = new Color(255, 0, 0);
	}

	@Override
	public Square copySquare() {
		int x = this.getLocation().getX();
		int y = this.getLocation().getY();
		TWSquare copy = new TWSquare(x, y);
		this.cloneHelper(copy);
		return copy;
	}
}
