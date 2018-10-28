package edu.cmu.cs.cs214.hw4.core.squares;

import java.awt.Color;

/**
 * Class represents a Triple Letter square which multiplies letter tile score by
 * 3.
 *
 * @author akash
 *
 */
public class TLSquare extends Square {
	/**
	 *
	 * @param x
	 *                x coordinate
	 * @param y
	 *                y coordinate
	 */
	public TLSquare(int x, int y) {
		super(x, y);
		this.letterFactor = 3;
		this.wordFactor = 1;
		this.name = "TL";
		this.color = new Color(0, 0, 255); // dark blue
	}

	@Override
	public Square copySquare() {
		int x = this.getLocation().getX();
		int y = this.getLocation().getY();
		TLSquare copy = new TLSquare(x, y);
		this.cloneHelper(copy);
		return copy;
	}

}
