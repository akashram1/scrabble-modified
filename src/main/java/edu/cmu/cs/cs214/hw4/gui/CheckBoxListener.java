package edu.cmu.cs.cs214.hw4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import edu.cmu.cs.cs214.hw4.core.LetterTile;

public class CheckBoxListener implements ActionListener {
	private LetterTile tile;
	private List<LetterTile> exchangeTiles;

	public CheckBoxListener(LetterTile tile,
	List<LetterTile> exchangeTiles) {
		this.tile = tile;
		this.exchangeTiles = exchangeTiles;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.exchangeTiles.add(tile);

	}

}
