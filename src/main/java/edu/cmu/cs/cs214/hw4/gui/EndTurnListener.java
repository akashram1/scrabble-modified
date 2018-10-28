package edu.cmu.cs.cs214.hw4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;

public class EndTurnListener implements ActionListener {
	private ScrabbleGame game;
	private PlayerViewWindow playerViewWindow;
	private GameSubject subject;
	private String exceptionMessage;

	public EndTurnListener(ScrabbleGame game,
	PlayerViewWindow playerViewWindow, GameSubject subject) {
		this.game = game;
		this.playerViewWindow = playerViewWindow;
		this.subject = subject;
		exceptionMessage = null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		exceptionMessage = null;
		try {
			game.endTurn();
		} catch (IllegalStateException e) {
			exceptionMessage = e.getMessage();
		} finally {
			if (exceptionMessage == null) {
				subject.notifyChangeHasOccurred();
			} else {
				JOptionPane.showMessageDialog(playerViewWindow,
				exceptionMessage);
			}
		}

	}

}
