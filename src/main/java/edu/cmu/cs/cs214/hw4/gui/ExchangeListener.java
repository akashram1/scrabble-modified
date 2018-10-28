package edu.cmu.cs.cs214.hw4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import edu.cmu.cs.cs214.hw4.core.LetterTile;
import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;

public class ExchangeListener implements ActionListener {
	private PlayerViewWindow playerViewWindow;
	private ScrabbleGame game;
	private List<LetterTile> letterTiles;
	private List<LetterTile> exchangeTiles;
	private String errorMessage;

	public ExchangeListener(PlayerViewWindow playerViewWindow,
	ScrabbleGame game, List<LetterTile> letterTiles) {
		this.playerViewWindow = playerViewWindow;
		this.game = game;
		this.letterTiles = letterTiles;
		this.exchangeTiles = new ArrayList<>();
		this.errorMessage = null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		this.errorMessage = null;
		ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();

		for (int i = 0; i < letterTiles.size(); i++) {
			JCheckBox box = new JCheckBox(
			letterTiles.get(i).toString());

			box.addActionListener(new CheckBoxListener(
			letterTiles.get(i), this.exchangeTiles));

			checkBoxes.add(box);
		}
		Object[] obj = (Object[]) checkBoxes
		.toArray(new Object[checkBoxes.size()]);

		int answer = JOptionPane.showConfirmDialog(playerViewWindow,
		obj, "Select Exchange Tiles", JOptionPane.OK_CANCEL_OPTION,
		JOptionPane.QUESTION_MESSAGE);

		if (answer == JOptionPane.CANCEL_OPTION) {
			return;
		} else {
			try {
				game.exchange(exchangeTiles);
			} catch (NullPointerException n) {
				this.errorMessage = n.getMessage();
			} catch (IllegalStateException e) {
				this.errorMessage = e.getMessage();
			} finally {

				if (errorMessage == null) {
				} else {
					JOptionPane.showMessageDialog(
					playerViewWindow, errorMessage);
				}
			}
		}

	}

}
