package edu.cmu.cs.cs214.hw4.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Creates the LetterTiles. The letter tile distribution is same as that of the
 * Scrabble Distribution.
 *
 * @author akash
 *
 */
public class LetterTileBag {
	private List<LetterTile> lettersLeft;
	private List<LetterTile> lettersSupplied;
	private Map<Character, Integer> letterCount;
	private Map<Character, Integer> letterPoints;

	private static final int INITIAL_LETTER_COUNT = 98;

	public LetterTileBag() {
		lettersLeft = new LinkedList<LetterTile>();
		lettersSupplied = new LinkedList<LetterTile>();

		// Create list of the number of copies of each tile
		letterCount = new HashMap<Character, Integer>();
		setLetterCount();

		// Create list of number of points for each tile
		letterPoints = new HashMap<Character, Integer>();
		setLetterPoints();

		// Fill the bag with letter tiles.
		fill();
	}

	private void setLetterCount() {
		letterCount.put('A', 9);
		letterCount.put('B', 2);
		letterCount.put('C', 2);
		letterCount.put('D', 4);
		letterCount.put('E', 12);
		letterCount.put('F', 2);
		letterCount.put('G', 3);
		letterCount.put('H', 2);
		letterCount.put('I', 9);
		letterCount.put('J', 1);
		letterCount.put('K', 1);
		letterCount.put('L', 4);
		letterCount.put('M', 2);
		letterCount.put('N', 6);
		letterCount.put('O', 8);
		letterCount.put('P', 2);
		letterCount.put('Q', 1);
		letterCount.put('R', 6);
		letterCount.put('S', 4);
		letterCount.put('T', 6);
		letterCount.put('U', 4);
		letterCount.put('V', 2);
		letterCount.put('W', 2);
		letterCount.put('X', 1);
		letterCount.put('Y', 2);
		letterCount.put('Z', 1);

	}

	private void setLetterPoints() {
		letterPoints.put('A', 1);
		letterPoints.put('B', 3);
		letterPoints.put('C', 3);
		letterPoints.put('D', 2);
		letterPoints.put('E', 1);
		letterPoints.put('F', 4);
		letterPoints.put('G', 2);
		letterPoints.put('H', 4);
		letterPoints.put('I', 1);
		letterPoints.put('J', 8);
		letterPoints.put('K', 5);
		letterPoints.put('L', 1);
		letterPoints.put('M', 3);
		letterPoints.put('N', 1);
		letterPoints.put('O', 1);
		letterPoints.put('P', 3);
		letterPoints.put('Q', 10);
		letterPoints.put('R', 1);
		letterPoints.put('S', 1);
		letterPoints.put('T', 1);
		letterPoints.put('U', 1);
		letterPoints.put('V', 4);
		letterPoints.put('W', 4);
		letterPoints.put('X', 8);
		letterPoints.put('Y', 4);
		letterPoints.put('Z', 10);
	}

	private void fill() {
		int count = 1;
		for (Character ch : letterCount.keySet()) {
			count = 1;
			for (int i = 1; i <= letterCount.get(ch); i++) {
				lettersLeft.add(new LetterTile(ch.charValue(),
				letterPoints.get(ch), count));
				count++;
			}
		}

	}

	public boolean isEmpty() {
		return lettersLeft.size() == 0;
	}

	/**
	 * Supplies one randomly selected letterTile from the bag.
	 *
	 * @return a randomly selected LetterTile.
	 * @return null if bag is empty.
	 */
	public LetterTile supplyLetter() {

		/*
		 * NOTE TO SELF: Should game end when bag is empty? Should I
		 * throw an exception instead of returning null ?
		 */
		if (isEmpty()) {
			return null;
		}

		Random num = new Random();
		/*
		 * Randomly select an index between 0 and < lettersLeft.size() ,
		 * store the tile at that index in lettersSupplied and return
		 * the letter.
		 */

		LetterTile supplyTile = lettersLeft
		.remove(num.nextInt(lettersLeft.size()));

		lettersSupplied.add(supplyTile);
		return supplyTile;
	}

	/**
	 * A letter tile can be stored in the bag only if it is not already
	 * present in the bag. If it is present, the letter is not stored in the
	 * bag.
	 *
	 * @param lt
	 */
	public void storeLetter(LetterTile lt) throws IllegalStateException {

		if (lettersLeft.size() == INITIAL_LETTER_COUNT) {
			throw new IllegalStateException(
			"Bag is full. " + "Method cannot be invoked now.");
		}

		if (lt == null) {
			throw new IllegalArgumentException(
			"LetterTile can't be null");
		}
		if (lettersLeft.contains(lt)) {
			return;
		}

		if (lettersSupplied.contains(lt)) {
			lettersSupplied.remove(lt);
			lettersLeft.add(lt);

		} /*
			 * else: This is not possible. Any LetterTile can either
			 * be present in lettersSupplied or lettersLeft at any
			 * point of time.
			 */

	}
}
