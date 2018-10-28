package edu.cmu.cs.cs214.hw4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;

import edu.cmu.cs.cs214.hw4.core.LetterTile;
import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;
import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;

public class PlayLetterMoveListener implements ActionListener {

	private ScrabbleGame g;
	private PlayerViewWindow playerViewWindow;
	private LinkedHashMap<LetterTile, Coordinates> word;
	private String exceptionMessage;

	public PlayLetterMoveListener(ScrabbleGame game,
	PlayerViewWindow playerViewWindow,
	LinkedHashMap<LetterTile, Coordinates> word) {
		this.g = game;
		this.playerViewWindow = playerViewWindow;
		this.word = word;
		this.exceptionMessage = null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		exceptionMessage = null;
		try {
			g.playLetterMove(new LinkedHashMap<>(word));
		} catch (NullPointerException e1) {
			this.exceptionMessage = e1.getMessage();
		} catch (IllegalStateException e2) {
			this.exceptionMessage = e2.getMessage();
		} catch (IllegalArgumentException e3) {
			this.exceptionMessage = e3.getMessage();
		} finally {
			if (exceptionMessage == null) {
				// means move successful
				/*
				 * Re-establish invariants
				 */

				// Set selected to null
				playerViewWindow.resetSelectedToNull();
				// Set empty word linkedHashMap.
				playerViewWindow.emptyWordMap();
				// empty allSelectedLetterTiles map
				playerViewWindow.emptyAllSelectedLetterTiles();

				/*
				 * Disable those move buttons whose pre-reqs are
				 * false
				 *
				 */
				playerViewWindow
				.disableMoveButtonsBasedOnPreqs();

				/*
				 * Disable all letter tiles
				 */
				playerViewWindow.disableAllLetterTiles();
			} else { // means move unsucessful. Place tiles back on
					// board.

				JOptionPane.showMessageDialog(playerViewWindow,
				exceptionMessage);

				// Remove tiles from board
				playerViewWindow.removePlacedTilesFromBoard();
				// Put tiles back to rack
				playerViewWindow.deselectAllSelectedTiles();

				/*
				 * Re-establish invariants
				 */

				// Set selected to null
				playerViewWindow.resetSelectedToNull();
				// Set empty word linkedHashMap.
				playerViewWindow.emptyWordMap();
				// empty allSelectedLetterTiles map
				playerViewWindow.emptyAllSelectedLetterTiles();
			}
		}

	}
}
