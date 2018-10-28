package edu.cmu.cs.cs214.hw4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import edu.cmu.cs.cs214.hw4.core.LetterTile;

public class LetterListener implements ActionListener {
	private LetterTile tile;
	private PlayerViewWindow w;
	private int i;

	public LetterListener(List<LetterTile> letterRack, int i,
	PlayerViewWindow w) {
		this.i = i;
		this.tile = letterRack.get(i);
		this.w = w;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		w.setSelectedLetterTile(tile, i);
		// System.out.println(i);
		w.disableLetterTile(i);
		w.makeLetterTileInvisible(i);

	}

}
