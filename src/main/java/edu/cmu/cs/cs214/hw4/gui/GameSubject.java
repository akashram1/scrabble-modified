package edu.cmu.cs.cs214.hw4.gui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JFrame;

import edu.cmu.cs.cs214.hw4.core.GameState;
import edu.cmu.cs.cs214.hw4.core.LetterTile;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;
import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;

/**
 * Every PlayerViewWindow is an observer to this GameSubject. When a button is
 * pressed in any PlayerViewWindow, and a successful move is made, a
 * corresponding method in this class is invoked which then updates the
 * neccessary components in all PlayerViewWindows (the observers) .
 *
 * @author akash
 *
 */
public class GameSubject {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;isjdliasjdasdj
	/*
	 * Game variables
	 */
	private ScrabbleGame g;
	private GameState gameState;
	private ArrayList<Player> players;
	private LinkedHashMap<LetterTile, Coordinates> word;
	private int currentPlayerIndex;

	/*
	 * List of observers
	 */
	private List<PlayerViewWindow> observers;

	public GameSubject(ScrabbleGame g, JFrame parentFrame) {
		/*
		 * Initialize the instance game variables.
		 */
		this.g = g;
		this.gameState = g.startGame();
		players = gameState.getAllPlayersList();
		word = new LinkedHashMap<>();
		currentPlayerIndex = gameState.getCurrentPlayerIndex();

		/*
		 * Initialize observer list
		 */
		observers = new ArrayList<>();

		/*
		 * Close the parent window
		 */
		parentFrame.dispose();

		/*
		 * Set up the new windows.
		 */
		setUp();
	}

	private void setUp() {
		/*
		 * Initialize observers.
		 */
		for (int i = 0; i < players.size(); i++) {
			PlayerViewWindow p = new PlayerViewWindow(g, gameState,
			i, this);
			observers.add(p);

		}

	}

	/**
	 * Create methods here to update all PlayerViewWindows accordingly.
	 */

	public void notifyChangeHasOccurred() {
		updateAllObservers();
	}

	private void updateAllObservers() {
		for (PlayerViewWindow observer : observers) {
			observer.update();
		}
	}

}
