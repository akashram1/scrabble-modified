package edu.cmu.cs.cs214.hw4.core.squares;

import java.awt.Color;

/**
 * Represents the squares that line the 15*15 scrabble board. Created to prevent
 * certain ArrayIndexOutOfBoundsExceptions from occuring during certain
 * LetterMove validation steps.
 *
 * @author akash
 *
 */
public class DummySquare extends Square {

	public DummySquare(int x, int y) {
		super(x, y);
		this.letterFactor = 0;
		this.wordFactor = 0;
		this.name = "XX";
		this.color = new Color(0, 0, 0); // black
	}

	@Override
	public Square copySquare() {
		return this;
	}

}
