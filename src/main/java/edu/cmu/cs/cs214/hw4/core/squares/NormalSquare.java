package edu.cmu.cs.cs214.hw4.core.squares;

import java.awt.Color;

/**
 * Class represents a normal square which multiplies letter tile score by 1.
 *
 * @author akash
 *
 */
public class NormalSquare extends Square {
	/**
	 *
	 * @param x
	 *                x coordinate
	 * @param y
	 *                y coordinate
	 */
	public NormalSquare(int x, int y) {
		super(x, y);
		this.letterFactor = 1;
		this.wordFactor = 1;
		this.name = "  ";
		this.color = new Color(255, 255, 255); // white
	}

	@Override
	public Square copySquare() {
		int x = this.getLocation().getX();
		int y = this.getLocation().getY();
		NormalSquare copy = new NormalSquare(x, y);
		this.cloneHelper(copy);
		return copy;
	}

}
