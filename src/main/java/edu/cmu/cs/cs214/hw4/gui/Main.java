package edu.cmu.cs.cs214.hw4.gui;

import javax.swing.SwingUtilities;

import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;

public class Main {

	public static void main(String[] args) {
		// Creating an instance of Scrabble Game
		ScrabbleGame game = new ScrabbleGame();

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// STEP 1: Create ScrabbleGame instance

				ScrabbleGame g = new ScrabbleGame();
				// STEP 2: Call the first Window
				new NumPlayersWindow(g);

			}
		});

	}
}
