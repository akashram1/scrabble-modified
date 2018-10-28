package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import edu.cmu.cs.cs214.hw4.core.coordinates.CompareCoordinates;
import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;
import edu.cmu.cs.cs214.hw4.core.specialTile.SpecialTile;
import edu.cmu.cs.cs214.hw4.core.squares.DLSquare;
import edu.cmu.cs.cs214.hw4.core.squares.DWSquare;
import edu.cmu.cs.cs214.hw4.core.squares.DummySquare;
import edu.cmu.cs.cs214.hw4.core.squares.NormalSquare;
import edu.cmu.cs.cs214.hw4.core.squares.Square;
import edu.cmu.cs.cs214.hw4.core.squares.TLSquare;
import edu.cmu.cs.cs214.hw4.core.squares.TWSquare;

/**
 * A class representation of the ScrabbleBoard.
 *
 * @author akash
 *
 */
class Board {

	private Square[][] grid;
	// The grid row and column indices of every square is DELTA more
	// than X and Y coordinates of that square.
	private static final int DELTA = 1;
	private List<String> wordsMade;

	protected Board() {
		// 2 extra rows and columns have been used to pad the board to
		// prevent array index out of bounds calculations in many
		// subsequent calculations.
		this.grid = new Square[17][17];
		createArrangement();
		wordsMade = new ArrayList<>();
	}

	/**
	 * Copy constructor.
	 */
	protected Board(Board b) {
		this.grid = new Square[17][17];
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 17; j++) {
				this.grid[i][j] = b.grid[i][j].copySquare();
			}
		}
	}

	private void createArrangement() {
		HashSet<Coordinates> nonNormalSquares = new HashSet<>();

		List<Coordinates> tw = new ArrayList<Coordinates>();
		tw.add(new Coordinates(0, 0));
		tw.add(new Coordinates(0, 7));
		tw.add(new Coordinates(0, 14));
		tw.add(new Coordinates(7, 0));
		tw.add(new Coordinates(7, 14));
		tw.add(new Coordinates(14, 0));
		tw.add(new Coordinates(14, 7));
		tw.add(new Coordinates(14, 14));

		// Create triple word score squares
		for (Coordinates c : tw) {
			Coordinates c1 = getActualCoordinate(c);
			grid[c1.getX()][c1.getY()] = new TWSquare(c.getX(),
			c.getY());
			nonNormalSquares.add(c);
		}

		// Create double word score squares

		for (int i = 0; i < 15; i++) {
			if (i != 0 && i != 5 && i != 6 && i != 8 && i != 9
			&& i != 14) {
				grid[i + DELTA][i + DELTA] = new DWSquare(i, i);
				grid[i + DELTA][14 - i + DELTA] = new DWSquare(
				i, 14 - i);

				if (i != 7) {
					nonNormalSquares
					.add(new Coordinates(i, i));
					nonNormalSquares
					.add(new Coordinates(i, 14 - i));
				} else {
					nonNormalSquares
					.add(new Coordinates(7, 7));
				}
			}

		}

		// Create double letter score squares

		for (int i = 0; i < 15; i++) {
			if (i == 0 || i == 14) {
				grid[i + DELTA][3 + DELTA] = new DLSquare(i, 3);
				nonNormalSquares.add(new Coordinates(i, 3));
				grid[i + DELTA][11 + DELTA] = new DLSquare(i,
				11);
				nonNormalSquares.add(new Coordinates(i, 11));
			} else if (i == 2 || i == 12) {
				grid[i + DELTA][6 + DELTA] = new DLSquare(i, 6);
				nonNormalSquares.add(new Coordinates(i, 6));
				grid[i + DELTA][8 + DELTA] = new DLSquare(i, 8);
				nonNormalSquares.add(new Coordinates(i, 8));
			} else if (i == 3 || i == 11) {
				grid[i + DELTA][0 + DELTA] = new DLSquare(i, 0);
				nonNormalSquares.add(new Coordinates(i, 0));
				grid[i + DELTA][7 + DELTA] = new DLSquare(i, 7);
				nonNormalSquares.add(new Coordinates(i, 7));
				grid[i + DELTA][14 + DELTA] = new DLSquare(i,
				14);
				nonNormalSquares.add(new Coordinates(i, 14));
			} else if (i == 6 || i == 8) {
				grid[i + DELTA][2 + DELTA] = new DLSquare(i, 2);
				nonNormalSquares.add(new Coordinates(i, 2));
				grid[i + DELTA][6 + DELTA] = new DLSquare(i, 6);
				nonNormalSquares.add(new Coordinates(i, 6));
				grid[i + DELTA][8 + DELTA] = new DLSquare(i, 8);
				nonNormalSquares.add(new Coordinates(i, 8));
				grid[i + DELTA][12 + DELTA] = new DLSquare(i,
				12);
				nonNormalSquares.add(new Coordinates(i, 12));
			} else if (i == 7) {
				grid[i + DELTA][3 + DELTA] = new DLSquare(i, 3);
				nonNormalSquares.add(new Coordinates(i, 3));
				grid[i + DELTA][11 + DELTA] = new DLSquare(i,
				11);
				nonNormalSquares.add(new Coordinates(i, 11));
			}
		}

		// Create triple letter score squares

		for (int i = 0; i < 15; i++) {
			if (i == 1 || i == 13) {
				grid[i + DELTA][5 + DELTA] = new TLSquare(i, 5);
				nonNormalSquares.add(new Coordinates(i, 5));
				grid[i + DELTA][9 + DELTA] = new TLSquare(i, 9);
				nonNormalSquares.add(new Coordinates(i, 9));
			} else if (i == 5 || i == 9) {
				grid[i + DELTA][1 + DELTA] = new TLSquare(i, 1);
				nonNormalSquares.add(new Coordinates(i, 1));
				grid[i + DELTA][5 + DELTA] = new TLSquare(i, 5);
				nonNormalSquares.add(new Coordinates(i, 5));
				grid[i + DELTA][9 + DELTA] = new TLSquare(i, 9);
				nonNormalSquares.add(new Coordinates(i, 9));
				grid[i + DELTA][13 + DELTA] = new TLSquare(i,
				13);
				nonNormalSquares.add(new Coordinates(i, 13));

			}
		}

		// Create the pad squares along first and last rows and columns
		for (int i = 0; i < 17; i++) {
			grid[0][i] = new DummySquare(-1, -1);
			grid[16][i] = new DummySquare(-1, -1);
		}

		for (int i = 0; i < 17; i++) {
			grid[i][0] = new DummySquare(-1, -1);
			grid[i][16] = new DummySquare(-1, -1);
		}

		// Create normal squares

		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (!nonNormalSquares
				.contains(new Coordinates(i, j))) {
					grid[i + DELTA][j
					+ DELTA] = new NormalSquare(i, j);
				}
			}
		}

	}

	private Coordinates getActualCoordinate(Coordinates notReal) {
		return new Coordinates(notReal.getX() + 1, notReal.getY() + 1);
	}

	/*
	 * Checks if coordinates are valid. That is based on 2 conditions: 1: x
	 * and y coordinate should be between 0 and 14 (both inclusive) each and
	 * 2: Coordinates are not repeated.
	 */
	boolean areValidCoordinates(LetterMove letterMove) {

		List<Coordinates> word = letterMove.getCoordinatesList();
		// Check if coordinates are within bound
		for (Coordinates c : word) {
			if (c.getX() >= 0 && c.getX() <= 14 && c.getY() >= 0
			&& c.getY() <= 14) {
				continue;
			} else {
				return false;
			}
		}

		// Check if coordinates are not repeated
		Iterator<Coordinates> iter = word.iterator();
		Coordinates initial = iter.next();
		while (iter.hasNext()) {
			Coordinates next = iter.next();
			if (initial.equals(next)) {
				return false;
			}
			initial = next;

		}

		return true;
	}

	/*
	 * Checks if a the set of coordinates intersect with that of the Star
	 * Square at (7,7)
	 */
	boolean isOnStar(LetterMove letterMove) {
		List<Coordinates> word = letterMove.getCoordinatesList();
		return word.contains(new Coordinates(7, 7)) == true;
	}

	/*
	 * Checks if the square at Coordinates c is occupied by a LetterTile or
	 * Not.
	 *
	 */

	boolean isSquareOccupiedByLetterTile(Coordinates c) {
		if (grid[c.getX() + 1][c.getY() + 1].isOccupiedByLetterTile()) {
			return true;
		}
		return false;
	}

	boolean areCoordinatesInSameRowOrColumn(LetterMove letterMove) {
		List<Integer> xcoordinates = letterMove.getXCoordinatesOnly();
		List<Integer> ycoordinates = letterMove.getYCoordinatesOnly();
		// Check if all have same X Coordinate

		if (doAllHaveSameCoordinate(xcoordinates)) {
			letterMove.setIsHorizontalMove(true);
			return true;
		}

		// Else check if they all have same Y Coordinate

		else if (doAllHaveSameCoordinate(ycoordinates)) {
			letterMove.setIsHorizontalMove(false);
			return true;
		}
		// Else move is wrong
		return false;
	}

	boolean areCoordinatesContiguous(LetterMove letterMove) {

		List<Coordinates> list = letterMove.getCoordinatesList();
		Collections.sort(list, new CompareCoordinates());

		Iterator<Coordinates> iter = list.iterator();
		Coordinates initialCoordinate = iter.next();

		if (letterMove.isHorizontalMove()) {
			// Check the successive differences in y coordinates are
			// equal to 1.
			while (iter.hasNext()) {
				Coordinates nextCoordinate = iter.next();
				if (nextCoordinate.getY()
				- initialCoordinate.getY() != 1) {
					return false;
				}
				initialCoordinate = nextCoordinate;
			}

			return true;

		} else if (letterMove.isVerticalMove()) {
			while (iter.hasNext()) {
				Coordinates nextCoordinate = iter.next();
				if (nextCoordinate.getX()
				- initialCoordinate.getX() != 1) {
					return false;
				}
				initialCoordinate = nextCoordinate;
			}

			return true;
		}

		return false;

	}

	/**
	 * Can be invoked only if played coordinates are contiguous.
	 */
	boolean isContigMoveTouchingPreexistingTileAtEdge(
	LetterMove letterMove) {
		List<Coordinates> list = letterMove.getCoordinatesList();
		Collections.sort(list, new CompareCoordinates());
		Coordinates first = list.get(0);
		Coordinates last = list.get(list.size() - 1);
		if (letterMove.isHorizontalMove()) {
			if (grid[first.getX() + DELTA][first.getY() - 1 + DELTA]
			.isOccupiedByLetterTile()) {
				return true;
			} else if (grid[last.getX() + DELTA][last.getY() + 1
			+ DELTA].isOccupiedByLetterTile()) {
				return true;
			} else {
				for (Coordinates c : list) {
					// Checking square above
					int aboveX = c.getX() - 1;
					int aboveY = c.getY();

					if (grid[aboveX + DELTA][aboveY + DELTA]
					.isOccupiedByLetterTile()) {
						return true;
					}

					// Checking square below
					int belowX = c.getX() + 1;
					int belowY = c.getY();

					if (grid[belowX + DELTA][belowY + DELTA]
					.isOccupiedByLetterTile()) {
						return true;
					}

				}
			}
		} else if (letterMove.isVerticalMove()) {
			if (grid[first.getX() - 1 + DELTA][first.getY() + DELTA]
			.isOccupiedByLetterTile()) {
				return true;
			} else if (grid[last.getX() + 1 + DELTA][last.getY() + 1
			+ DELTA].isOccupiedByLetterTile()) {
				return true;
			} else {
				for (Coordinates c : list) {
					// Checking square to the left
					int leftX = c.getX();
					int leftY = c.getY() - 1;
					if (grid[leftX + DELTA][leftY + DELTA]
					.isOccupiedByLetterTile()) {
						return true;
					}

					// Checking square to the right
					int rightX = c.getX();
					int rightY = c.getY();
					if (grid[rightX + DELTA][rightY + DELTA]
					.isOccupiedByLetterTile()) {
						return true;
					}

				}
			}
		}
		return false;
	}

	/**
	 * Can be invoked only if played coordinates are non-contiguous
	 */
	boolean isNonContigMoveTouchingPreexistingTileAtEdge(
	LetterMove letterMove) {
		List<Coordinates> list = letterMove.getCoordinatesList();
		Collections.sort(list, new CompareCoordinates());
		Iterator<Coordinates> iter = list.iterator();
		Coordinates initialCoordinate = iter.next();

		if (letterMove.isHorizontalMove()) {
			int x = initialCoordinate.getX();
			while (iter.hasNext()) {
				Coordinates nextCoordinate = iter.next();
				if (nextCoordinate.getY()
				- initialCoordinate.getY() != 1) {
					int start = initialCoordinate.getY()
					+ 1;
					int finish = nextCoordinate.getY() - 1;

					for (int i = start; i <= finish; i++) {
						if (!grid[x + DELTA][i + DELTA]
						.isOccupiedByLetterTile()) {
							return false;
						}
					}
				}

				initialCoordinate = nextCoordinate;
			}
		} else if (letterMove.isVerticalMove()) {
			int y = initialCoordinate.getY();
			while (iter.hasNext()) {
				Coordinates nextCoordinate = iter.next();
				if (nextCoordinate.getX()
				- initialCoordinate.getX() != 1) {
					int start = initialCoordinate.getX()
					+ 1;
					int finish = nextCoordinate.getX() - 1;

					for (int i = start; i <= finish; i++) {
						if (!grid[i + DELTA][y + DELTA]
						.isOccupiedByLetterTile()) {
							return false;
						}
					}
				}
			}
		}

		return true;
	}

	private boolean doAllHaveSameCoordinate(List<Integer> coordinateList) {
		Integer temp = new Integer(coordinateList.get(0));
		for (Integer i : coordinateList) {
			if (!i.equals(temp)) {
				return false;
			}
		}

		return true;
	}

	/*
	 * Precondition: Coordinates c are valid. Q: Should a new Copy of the
	 * tile be made before setting?
	 */
	void setTile(Tile t, Coordinates c) {
		if (t instanceof LetterTile) {
			grid[c.getX() + DELTA][c.getY() + DELTA]
			.setLetterTile((LetterTile) t);
			t.setCoordinates(c);
		} else if (t instanceof SpecialTile) {
			grid[c.getX() + DELTA][c.getY() + DELTA]
			.setSpecialTile((SpecialTile) t);
			t.setCoordinates(c);
		}
		return;

	}

	/*
	 * Precondition: letterMove.isEmpty() == false
	 */
	int calculateScore(LetterMove letterMove) {

		List<Coordinates> coordinates = letterMove.getCoordinatesList();
		Collections.sort(coordinates, new CompareCoordinates());
		Coordinates playedFirst = coordinates.get(0);
		Coordinates startScoreComputation;
		int totalScore = 0;
		if (!letterMove.wasBoomTileActivated()) {
			if (letterMove.isHorizontalMove()) {
				/*
				 * Step 1: Find the left-most coordinate
				 * (startScoreComputation) from where score
				 * computation should start.
				 */
				startScoreComputation = findLeftMostStartCoordinateHelper(
				playedFirst);

				/*
				 * Step 2: Compute horizontal score.
				 */

				totalScore += calculateHorizontalScoreHelper(
				startScoreComputation);

				/*
				 * Step 3: Compute vertical score only if
				 * tiles(s) are present above and/or below.
				 */

				for (Coordinates c : coordinates) {
					if (isAboveTileOccupied(c)
					|| isBelowTileOccupied(c)) {
						/*
						 * Step 3.1: Find top most
						 * filled square from where
						 * score computation shoud
						 * start.
						 */
						startScoreComputation = findTopMostStartCoordinateHelper(
						c);

						totalScore += calculateVerticalScoreHelper(
						startScoreComputation);
					}
				}
			} else if (letterMove.isVerticalMove()) {

				/*
				 * Step 1: Find top-most coordinate from where
				 * score computation should start.
				 */

				startScoreComputation = findTopMostStartCoordinateHelper(
				playedFirst);

				/*
				 * Step 2: Compute vertical score.
				 */
				totalScore += calculateVerticalScoreHelper(
				startScoreComputation);

				/*
				 * Step 3: Calculate individual horizontal
				 * scores.
				 */
				for (Coordinates c : coordinates) {
					if (isLeftTileOccupied(c)
					|| isRightTileOccupied(c)) {
						/*
						 * Step 3.1: Find left most
						 * filled square from where
						 * score computation shoud
						 * start.
						 */
						startScoreComputation = findLeftMostStartCoordinateHelper(
						c);

						totalScore += calculateHorizontalScoreHelper(
						startScoreComputation);
					}
				}

			}

		} else {
			// IMPLEMENT HOW TO CALCULATE SCORE WHEN BOOM TILE IS
			// ACTIVATED.
			System.out.println();
		}

		return totalScore;
	}

	private boolean isAboveTileOccupied(Coordinates c) {
		if (c.getX() == 0) {
			return false;
		} else if (grid[c.getX() - 1 + DELTA][c.getY() + DELTA]
		.isOccupiedByLetterTile()) {
			return true;
		}
		return false;

	}

	private boolean isBelowTileOccupied(Coordinates c) {
		if (c.getX() == 16) {
			return false;
		} else if (grid[c.getX() + 1 + DELTA][c.getY() + DELTA]
		.isOccupiedByLetterTile()) {
			return true;
		}
		return false;
	}

	private boolean isLeftTileOccupied(Coordinates c) {
		if (c.getY() == 0) {
			return false;
		} else if (grid[c.getX() + DELTA][c.getY() - 1 + DELTA]
		.isOccupiedByLetterTile()) {
			return true;
		}
		return false;

	}

	private boolean isRightTileOccupied(Coordinates c) {
		if (c.getY() == 16) {
			return false;
		} else if (grid[c.getX() + DELTA][c.getY() + 1 + DELTA]
		.isOccupiedByLetterTile()) {
			return true;
		}
		return false;

	}

	private Coordinates findLeftMostStartCoordinateHelper(Coordinates c) {
		int x = c.getX();
		int y = c.getY();

		for (int i = y;; i--) {
			if (!grid[x + DELTA][i + DELTA].isOccupiedByLetterTile()
			|| grid[x + DELTA][i + DELTA] instanceof DummySquare) {
				return new Coordinates(x, i + 1);
			}
		}

	}

	private Coordinates findTopMostStartCoordinateHelper(Coordinates c) {
		int x = c.getX();
		int y = c.getY();

		for (int i = x;; i--) {
			if (!grid[i + DELTA][y + DELTA].isOccupiedByLetterTile()
			|| grid[i + DELTA][y + DELTA] instanceof DummySquare) {
				return new Coordinates(i + 1, y);
			}
		}

	}

	private int calculateHorizontalScoreHelper(
	Coordinates startScoreComputation) {
		StringBuilder s = new StringBuilder();
		List<Integer> wordFactors = new ArrayList<>();

		int tempScore = 0;
		int x = startScoreComputation.getX();
		int y = startScoreComputation.getY();

		for (int i = y;; i++) {
			if (!grid[x + DELTA][i + DELTA].isOccupiedByLetterTile()
			|| grid[x + DELTA][i + DELTA] instanceof DummySquare) {
				break;
			}
			LetterTile temp = (LetterTile) grid[x + DELTA][i
			+ DELTA].getTile();
			s.append(temp.getLetter());
			int letterFactor = grid[x + DELTA][i + DELTA]
			.getLetterScoreFactor();
			int wordFactor = grid[x + DELTA][i + DELTA]
			.getWordScoreFactor();
			int tilePoints = grid[x + DELTA][i + DELTA].getTile()
			.getPoints();
			tempScore += tilePoints * letterFactor;
			wordFactors.add(wordFactor);
		}

		for (Integer fac : wordFactors) {
			tempScore *= fac;
		}

		// inserting the new words in this.wordsMade.
		this.wordsMade.add(s.toString());

		return tempScore;
	}

	private int calculateVerticalScoreHelper(
	Coordinates startScoreComputation) {
		StringBuilder s = new StringBuilder();
		List<Integer> wordFactors = new ArrayList<>();

		int tempScore = 0;
		int x = startScoreComputation.getX();
		int y = startScoreComputation.getY();

		for (int i = x;; i++) {
			if (!grid[i + DELTA][y + DELTA].isOccupiedByLetterTile()
			|| grid[i + DELTA][y + DELTA] instanceof DummySquare) {
				break;
			}
			LetterTile temp = (LetterTile) grid[i + DELTA][y
			+ DELTA].getTile();
			s.append(temp.getLetter());
			int letterFactor = grid[i + DELTA][y + DELTA]
			.getLetterScoreFactor();
			int wordFactor = grid[i + DELTA][y + DELTA]
			.getWordScoreFactor();
			int tilePoints = grid[i + DELTA][y + DELTA].getTile()
			.getPoints();

			tempScore += tilePoints * letterFactor;
			wordFactors.add(wordFactor);
		}

		for (Integer fac : wordFactors) {
			tempScore *= fac;
		}

		// inserting the new words in this.wordsMade.
		this.wordsMade.add(s.toString());
		return tempScore;
	}

	List<String> getWordsMade() {
		List<String> temp = this.wordsMade;
		this.wordsMade = new ArrayList<>();
		return temp;
	}

	public Square getSquare(int x, int y) {
		return this.grid[x + DELTA][y + DELTA];
	}

	@Override
	public String toString() {
		StringBuilder temp = new StringBuilder();
		for (int i = 1; i < 16; i++) {
			for (int j = 1; j < 16; j++) {
				if (grid[i][j].isOccupiedByLetterTile()) {
					temp.append(grid[i][j].getTile() + " ");
				} else {
					temp
					.append(grid[i][j].toString() + " ");
				}
			}
			temp.append("\n");
		}
		return temp.toString();
	}

}
