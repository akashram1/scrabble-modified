package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.cs.cs214.hw4.core.squares.Square;

/**
 * A class that encapsulates certain parameters of ScrabbleGame after each valid
 * move. These include states of each Player (includes score, TileRack), the
 * Board, currenPlayerIndex and list of words created in that valid move.
 *
 * @author akash
 *
 */
public class GameState {
	private List<Player> playerStates;
	private int currentPlayerOrderIndex;
	private List<Integer> currentPlayerOrder;
	private int currentPlayerIndex;
	private Board boardState;
	private List<String> wordsMade;
	private List<Boolean> skipNextTurn;

	GameState(List<Player> playerStates, int currentPlayerOrderIndex,
	List<Integer> currentPlayerOrder, Board boardState,
	List<Boolean> skipNextTurn) {
		// Defensive copy of player states.
		this.playerStates = setPlayerStates(playerStates);
		this.currentPlayerOrderIndex = currentPlayerOrderIndex;

		// Defensive copy of order list. Orders may get shuffled in the
		// future.
		this.currentPlayerOrder = new ArrayList<>(currentPlayerOrder);
		this.currentPlayerIndex = this.currentPlayerOrder
		.get(currentPlayerOrderIndex);

		// Defensive copy of the board.
		this.boardState = new Board(boardState);
		this.wordsMade = new ArrayList<>();

		this.skipNextTurn = new ArrayList<>(skipNextTurn);
	}

	/*
	 * Copy constuctor.
	 *
	 * @param gs
	 */
	GameState(GameState gs) {
		// Defensive copy of player states.
		this.playerStates = setPlayerStates(gs.playerStates);
		this.currentPlayerOrderIndex = gs.currentPlayerOrderIndex;

		// Defensive copy of order list. Orders may get shuffled in the
		// future.
		this.currentPlayerOrder = new ArrayList<>(
		gs.currentPlayerOrder);
		this.currentPlayerIndex = this.currentPlayerOrder
		.get(currentPlayerOrderIndex);

		// Defensive copy of the board.
		this.boardState = new Board(gs.boardState);
		this.wordsMade = new ArrayList<>(gs.wordsMade);

		this.skipNextTurn = new ArrayList<>(gs.skipNextTurn);
	}

	public GameState() {
		// TODO Auto-generated constructor stub
	}

	private List<Player> setPlayerStates(List<Player> playerStates) {

		/*
		 * STEP 1: Create a defensive copy of the list
		 */
		List<Player> playersCopy = new ArrayList<Player>();

		for (Player p : playerStates) {
			playersCopy.add(new Player(p));
		}

		return playersCopy;
	}

	void updateGameState(List<Player> playerStates,
	int currentPlayerOrderIndex, List<Integer> currentPlayerOrder,
	Board boardState) {
		// Defensive copy of player states.
		this.playerStates = playerStates;
		this.currentPlayerOrderIndex = currentPlayerOrderIndex;

		// Defensive copy of order list. Orders may get shuffled in the
		// future.
		this.currentPlayerOrder = currentPlayerOrder;
		this.currentPlayerIndex = this.currentPlayerOrder
		.get(currentPlayerOrderIndex);

		// Defensive copy of the board.
		this.boardState = boardState;

	}

	void updateCurrPlayerIndex(int n) {
		this.currentPlayerIndex = n;
	}
	/*
	 * Player only needs to see the names, scores, tile racks, oard states
	 * after every valid move. Therefore all getters must return defensive
	 * copies only.
	 */

	/**
	 * Returns a list of all Players. getCurrentPlayerIndex() method can be
	 * invoked to get index of the current Player in this list .
	 *
	 * @return an ArrayList of Player. The scores and TileRack of each
	 *         player can be accessed via this Player reference.
	 */
	public ArrayList<Player> getAllPlayersList() {

		return (ArrayList<Player>) playerStates;
	}

	/**
	 * Returns index of currentPlayer. The reference to this Player can be
	 * found at this index in the ArrayList<Player> returned by
	 * getAllPlayersList() method.
	 *
	 * @return index of the current Player.
	 */
	public int getCurrentPlayerIndex() {

		return currentPlayerIndex;
	}

	/**
	 * Returns current state of a square on the board.
	 *
	 * @return Board
	 */
	public Square getCurrentSquareState(int x, int y) {
		return this.boardState.getSquare(x, y);
	}

	Board getCurrentBoardState() {
		return this.boardState;
	}

	void setWordsMade(List<String> wordsMade) {
		this.wordsMade.clear();
		if (wordsMade == null || wordsMade.size() == 0) {
			this.wordsMade = wordsMade;
		} else {
			for (String str : wordsMade) {
				this.wordsMade.add(str);
			}
		}
	}

	List<String> getWordsMade() {
		return this.wordsMade;
	}

}
