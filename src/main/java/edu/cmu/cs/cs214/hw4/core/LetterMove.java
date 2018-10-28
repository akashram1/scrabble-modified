package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;

/**
 * Encapsulates information about a LetterMove. This include location of tiles,
 * whether the tiles satisfy conditions of a move etc.
 * 
 * @author akash
 *
 */
class LetterMove {
	private LinkedHashMap<LetterTile, Coordinates> move;
	private boolean isVerticalMove;
	private boolean isHorizontalMove;
	private List<Coordinates> playedCoordinates;
	private boolean isTouchingPreexistingLetterTile;
	private List<Integer> xcoordinates;
	private List<Integer> ycoordinates;
	private boolean boomTileEffectSeen;

	LetterMove(LinkedHashMap<LetterTile, Coordinates> move) {
		this.move = move;
		this.isHorizontalMove = false;
		this.isVerticalMove = false;
		this.playedCoordinates = new ArrayList<>();
		for (Coordinates c : move.values()) {
			playedCoordinates.add(c);
		}
		this.isTouchingPreexistingLetterTile = false;
		xcoordinates = new ArrayList<>();
		ycoordinates = new ArrayList<>();
		for (Coordinates c : playedCoordinates) {
			xcoordinates.add(c.getX());
			ycoordinates.add(c.getY());

		}
		boomTileEffectSeen = false;

	}

	LinkedHashMap<LetterTile, Coordinates> getMove() {
		return this.move;
	}

	List<Coordinates> getCoordinatesList() {

		return new ArrayList<>(playedCoordinates);
	}

	List<Integer> getXCoordinatesOnly() {
		return new ArrayList<>(xcoordinates);
	}

	List<Integer> getYCoordinatesOnly() {
		return new ArrayList<>(ycoordinates);
	}

	/**
	 * Method should be called only if all coordinates in the move fall in
	 * the same row or column.
	 *
	 * @param answer
	 */
	void setIsHorizontalMove(boolean answer) {
		if (answer) {
			this.isHorizontalMove = true;
		} else {
			this.isVerticalMove = true;
		}
	}

	boolean isHorizontalMove() {

		return isHorizontalMove;

	}

	boolean isVerticalMove() {

		return isVerticalMove;
	}

	void setIsTouchingPreexistingLetterTile(boolean ans) {
		this.isTouchingPreexistingLetterTile = ans;
	}

	boolean isEmpty() {
		return move.size() == 0;
	}

	void setBoomTileEffectSeen(boolean ans) {
		this.boomTileEffectSeen = ans;
	}

	boolean wasBoomTileActivated() {
		return this.boomTileEffectSeen;
	}
}
