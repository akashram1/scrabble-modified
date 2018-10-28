package edu.cmu.cs.cs214.hw4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;

import edu.cmu.cs.cs214.hw4.core.LetterTile;
import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;

public class SquareListener implements ActionListener {

	private int x;
	private int y;
	private LetterTile tile;
	private LinkedHashMap<LetterTile, Coordinates> word;
	private PlayerViewWindow playerViewWindow;

	public SquareListener(int x, int y,
	LinkedHashMap<LetterTile, Coordinates> word,
	PlayerViewWindow playerViewWindow) {
		this.x = x;
		this.y = y;
		tile = null;
		this.word = word;
		this.playerViewWindow = playerViewWindow;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Check if this square is already occupied.
		if (tile != null) {
			return; // without doing anything.
		}
		LetterTile selected = playerViewWindow.getSelectedLetterTile();
		tile = selected;
		if (selected != null) {
			Coordinates coordinates = new Coordinates(x, y);
			word.put(selected, coordinates);

			// display it on the board.
			playerViewWindow.updateSquareButtonWithLetterTile(x, y,
			tile);
			// reset selected tile
			playerViewWindow.resetSelectedToNull();
		} else {
			// This happens when user clicks on square without
			// previously clicking on a LetterTile.
			JOptionPane.showMessageDialog(playerViewWindow,
			"Please select a tile before clicking on the Board.");
		}

	}

	/**
	 * Should be invoked if playLetterMove was unsuccessful and tiles placed
	 * on the board are removed and put back onto the rack.
	 * 
	 * @param word
	 */
	public void reinitializeListener(
	LinkedHashMap<LetterTile, Coordinates> word) {
		tile = null;
		this.word = word;

	}

}
