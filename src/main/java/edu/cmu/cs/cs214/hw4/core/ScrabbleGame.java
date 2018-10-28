package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;
import edu.cmu.cs.cs214.hw4.core.specialTile.SpecialTile;
import edu.cmu.cs.cs214.hw4.core.specialTile.SpecialTileBag;

/**
 * A class that controls the whole game flow. This class has to be instantiated
 * by the user to start the game. Contains methods to make all kinds of moves in
 * a Scrabble Game.
 *
 * @author akash
 *
 */
public class ScrabbleGame {
	private Board board;
	private LetterTileBag letterBag;
	private SpecialTileBag specialTileBag;
	private int numPlayers;
	private List<Player> players;
	private List<Integer> order;
	private int currentPlayerIndex;
	private int currentPlayerOrderIndex;
	private Dictionary<String> dictionary;
	private GameState gsGivenToUser;
	private int moveCount;
	private LetterMove currentLetterMove;
	public PreReqTracker preReq;
	private EndTurnCalledAfter endTurnAfter;
	private List<Boolean> skipNextTurn;
	Stack<GameState> gsStack;
	private List<String> currentMoveWords;
	private boolean challengeGap;

	public static final int LETTER_RACK_SIZE = 7;

	/**
	 * Instantiates Board, LetterBag, Players, Dictionary.
	 */
	public ScrabbleGame() {
		this.board = new Board();
		this.letterBag = new LetterTileBag();
		this.specialTileBag = new SpecialTileBag();
		numPlayers = 0;
		this.players = new ArrayList<Player>();
		this.order = new ArrayList<Integer>();
		this.currentPlayerIndex = 0;
		this.currentPlayerOrderIndex = 0;
		this.dictionary = new Dictionary<String>();
		this.moveCount = 0;
		preReq = new PreReqTracker();
		endTurnAfter = new EndTurnCalledAfter();
		skipNextTurn = new ArrayList<>();
		gsStack = new Stack<>();
		this.currentMoveWords = new ArrayList<>();

		challengeGap = false;

	}

	/**
	 * First method to be called after instantiating ScrabbleGame. Sets the
	 * number of players in the game.
	 *
	 * @param num
	 *                number of players in the game.
	 * @throws IllegalArgumentException
	 *                 if num is less than 2 or greater than 4.
	 */
	public void setNumPlayers(int num) throws IllegalArgumentException {
		if (num < 2 || num > 4) {
			throw new IllegalArgumentException("Num players"
			+ " must be " + "between 2 and 4. " + "Try again.");
		}

		/*
		 * This method will set the number of players only once i.e. at
		 * the start of the game. Otherwise, it will return without
		 * effecting any change.
		 */
		if (numPlayers == 0) {
			this.numPlayers = num;
			for (int i = 0; i < num; i++) {
				skipNextTurn.add(false);
			}
			return;
		}

		return;
	}

	/**
	 * Returns the number of players in the game. If setNumPlayers(int num)
	 * is not called before it, it returns 0.
	 *
	 * @return the number of players in the game
	 */
	public int getNumPlayers() {
		return numPlayers;
	}

	/**
	 * Second method to be called after instantiating ScrabbleGame.
	 *
	 * @param name
	 *                An ArrayList of String, each String representing a
	 *                person's name.
	 *
	 * @throws IllegalStateException
	 *                 if setNumPlayers(int num) is not called before this
	 *                 method.
	 *
	 * @throws IllegalArgumentException
	 *                 if name is null or is empty.
	 */
	public void setPlayers(ArrayList<String> name) {
		if (numPlayers == 0) {
			throw new IllegalStateException(
			"Cannot be invoked until" + "setNumPlayers(int num)"
			+ " is called.");
		}

		if (name.size() != numPlayers || name == null) {
			throw new IllegalArgumentException(
			"Incorrect number of names. Try again.");
		}

		/*
		 * STEP 1: Creating player object corresponding to each name.
		 */

		for (String str : name) {
			players.add(new Player(str));

		}

		/*
		 * STEP 2: Randomly decide player order.
		 */
		setPlayerOrder();

		/*
		 * STEP 3: Allocate 7 LetterTile s to each player
		 */

		for (Integer i : order) {
			Player p = this.players.get(i);
			provideTilesToPlayer(p, 7);

		}

	}

	private void setPlayerOrder() {
		Random n = new Random();

		List<Integer> orderTemp = new ArrayList<Integer>();
		for (int i = 0; i < players.size(); i++) {
			orderTemp.add(i);
		}

		while (!orderTemp.isEmpty()) {
			this.order
			.add(orderTemp.remove(n.nextInt(orderTemp.size())));
		}
		this.currentPlayerIndex = this.order.get(0);

	}

	private void provideTilesToPlayer(Player p, int num) {
		for (int i = 1; i <= num; i++) {
			if (letterBag.isEmpty()) {
				break;
			}
			p.setLetterTile(letterBag.supplyLetter());
		}

		return;

	}

	/**
	 * Third method to be called after starting instantiating ScrabbleGame.
	 *
	 * @return a GameState reference from which the user can obtain
	 *         information about player names, scores, tile racks, board
	 *         state etc.
	 *
	 * @throws IllegalStateException
	 *                 if setPlayers method is not invoked.
	 */
	public GameState startGame() throws IllegalStateException {
		if (numPlayers == 0) {
			throw new IllegalStateException(
			"Cannot be invoked until " + "setPlayers method"
			+ " is called.");
		}

		this.gsGivenToUser = new GameState(this.players, 0, this.order,
		this.board, this.skipNextTurn);
		gsGivenToUser.setWordsMade(this.currentMoveWords);
		// Pushing as soon as new GameState is created / updated
		this.gsStack.push(new GameState(gsGivenToUser));
		return gsGivenToUser;
	}

	/**
	 * To be called by each player if he wishes to place LetterTile on the
	 * board. Can be followed by a call of playSpecialMove() or endTurn().
	 *
	 * @param word
	 *                a LinkedHashMap containing LetterTiles of the current
	 *                player as keys and their Coordinates as values.
	 * @throws NullPointerException
	 *                 if any LetterTile/Coordinate in an entry in word is
	 *                 null.
	 * @throws IllegalStateException
	 *                 1. If playSpecialMove(SpecialTile st, Coordinates c),
	 *                 pass(), exchange(List<LetterTile> list) OR
	 *                 buy(HashMap<SpecialTile, Integer> list) is called
	 *                 before this. 2. If numPlayers == 0
	 * @throws IllegalArgumentException
	 *                 1.If any x or y coordinate is <0 or > 14 2.If first
	 *                 Player doesn't play a tile on (7,7) 3. If tiles are
	 *                 not placed in same row or column 4. If the square
	 *                 corresponding to a Coordinates in word is previously
	 *                 occupied by a LetterTile. 5. If first player does not
	 *                 place tiles contiguously on the board. 6. If word
	 *                 does not touch a preexisting tile on the Board
	 *                 (except for the very first playLetterMove() call.
	 */
	public void playLetterMove(LinkedHashMap<LetterTile, Coordinates> word)
	throws NullPointerException, IllegalStateException,
	IllegalArgumentException {

		if (!this.preReq.preReqForPlayLetter) {
			throw new IllegalStateException(
			"Can't invoke this method now.");
		}
		if (numPlayers == 0) {
			throw new IllegalStateException(
			"Cannot be invoked until" + "setNumPlayers(int num)"
			+ " is called.");
		}

		// Ensures atleast 1 entry is present in word
		if (word == null || word.size() == 0) {
			throw new NullPointerException("Invalid move.");
		}

		// Ensures no null LetterTiles or Coordinates

		for (LetterTile lt : word.keySet()) {
			if (lt == null) {
				throw new NullPointerException(
				"LetterTile reference(s) is "
				+ "null in method argument");
			}
		}

		for (Coordinates c : word.values()) {
			if (c == null) {
				throw new NullPointerException(
				"Coordinates reference(s) is "
				+ "null in method argument");
			}
		}

		/*
		 * STEP 0: Check if player possesses the played tiles.
		 */

		for (LetterTile t : word.keySet()) {
			if (!players.get(currentPlayerIndex).getLetterTileRack()
			.contains(t)) {
				throw new IllegalArgumentException(
				"LetterTile not in your TileRack");
			}
		}

		/*
		 * STEP 1: Obtaining the Coordinates of the squares.
		 */
		LetterMove letterMove = new LetterMove(word);

		/*
		 * STEP 2: General Move Validation: Check if the coordinates are
		 * valid i.e if they are within limits.
		 */

		if (!board.areValidCoordinates(letterMove)) {
			throw new IllegalArgumentException(
			"x and y " + "coordinates supplied are invalid.");
		}

		/*
		 * STEP ONLY FOR FIRST PLAYER: Check if star square is occupied.
		 */
		if (moveCount == 0)

		{
			if (!board.isOnStar(letterMove)) {
				throw new IllegalArgumentException(
				"Star not occupied. Try again.");
			}
		}

		/*
		 * STEP 3: General Move Validation: Check if square(s) is
		 * occupied previously.
		 */

		for (Coordinates c : letterMove.getCoordinatesList()) {
			if (board.isSquareOccupiedByLetterTile(c)) {
				throw new IllegalArgumentException(
				"Square is occupied.");
			}
		}

		/*
		 * STEP 4: Check if coordinates are in the same row or column.
		 */

		if (!board.areCoordinatesInSameRowOrColumn(letterMove)) {
			throw new IllegalArgumentException(
			" Tiles are not placed in same row or column.");
		}

		/*
		 * STEP 5: Contiguity checking. Tiles are vertically or
		 * horizontally contiguous if their successive x and y
		 * coordinates, respectively, differ exactly by 1.
		 */

		// The first move tiles must be contiguous.
		if (moveCount == 0) {
			if (!board.areCoordinatesContiguous(letterMove)) {
				throw new IllegalArgumentException(
				"Tiles not placed contiguously "
				+ "by first player.");
			}
		} else { // for all successive moves

			// if move is contiguous check if atleast 1 tile touches
			// a pre-existing tile at some edge (NOT CORNER!).
			if (board.areCoordinatesContiguous(letterMove)) {
				// Check all surrounding squares for previously
				// placed tiles.

				if (board
				.isContigMoveTouchingPreexistingTileAtEdge(
				letterMove)) {
					letterMove
					.setIsTouchingPreexistingLetterTile(
					true);
				} else {
					throw new IllegalArgumentException(
					" Placed tiles must "
					+ "touch atleast 1 "
					+ "preexisting tile.");
				}

			}
			// else if move is not contiguous
			else {
				// check if gaps are filled by pre-existing
				// tiles.

				if (board
				.isNonContigMoveTouchingPreexistingTileAtEdge(
				letterMove)) {
					letterMove
					.setIsTouchingPreexistingLetterTile(
					true);
				} else {
					throw new IllegalArgumentException(
					" Placed tiles must "
					+ "touch atleast 1 "
					+ "preexisting tile.");
				}
			}
		}

		/*
		 * STEP 6: Update local variable currentLetterMove for reference
		 * in endTurn()
		 */
		this.currentLetterMove = letterMove;

		/*
		 * STEP 7: A person can now invoke only endturn() or
		 * playSpecialMove() followed by endTurn(). Also nextPlayer can
		 * challenge this move.
		 */
		this.preReq.setPreReqs(false, true, false, false, false, true,
		true);

		/*
		 * STEP 8: Tell endTurn last move before calling endTurn was
		 * PlayLetterMove
		 */
		this.endTurnAfter.setEndTurnCalledAfter(true, false, false,
		false, false);

		/*
		 * STEP 9: Set challengeGap to false
		 */
		this.challengeGap = false;
	}

	/**
	 * A method to place a SpecialTile on the board. This tile will be
	 * viewable only by the player who placed this tile.Can be called only
	 * after playLetterMove(LinkedHashMap<LetterTile, Coordinates> word) is
	 * called.
	 *
	 * @param st
	 *                the SpecialTile
	 *
	 * @param c
	 *                Coordinates where the SpecialTile is to be placed
	 *
	 * @throws IllegalStateException
	 *                 1. If playLetterMove is not called before it. 2.If
	 *                 buy, exchange or pass method is called before it.
	 * @throws IllegalArgumentException
	 *                 1. If x and/or y coordinates of c are <0 or > 14. 2.
	 *                 If LetterTile exists at c previously.
	 */

	public void playSpecialMove(SpecialTile st, Coordinates c) {

		/*
		 * STEP 0: Check if pre-reqs to call this method are satisfied
		 */

		if (!preReq.preReqForPlaySpecial) {
			throw new IllegalStateException(
			"Cannot invoke this method without invoking "
			+ "playLetterMove before this.");
		}
		/*
		 * STEP 1: Check if current player possesses this tile.
		 */

		if (!players.get(currentPlayerIndex).hasSpecialTile(st)) {
			throw new IllegalArgumentException(
			"Player does not possess " + st.toString());
		}

		/*
		 * STEP 2: Ensure coordinates are valid.
		 */
		if (c.getX() < 0 || c.getX() > 14 || c.getY() < 0
		|| c.getY() > 14) {
			throw new IllegalArgumentException(
			"Coordinates invalid.");
		}

		/*
		 * STEP 3: Ensure no letter tile is in this position. This
		 * includes the positions taken up by the Letters of this move.
		 */
		if (board.isSquareOccupiedByLetterTile(c)) {
			throw new IllegalArgumentException(
			"Cannot place special tile on letter tile.");
		}

		if (currentLetterMove.getCoordinatesList().contains(c)) {
			throw new IllegalArgumentException(
			"Cannot place special tile on letter tile placed"
			+ " in same move.");
		}

		/*
		 * STEP 6: Remove this tile from Player's Special Tile Rack.
		 */

		players.get(currentPlayerIndex).removeSpecialTile(st);

		/*
		 * STEP 7: Place the tile on the board.
		 */
		board.setTile(st, c);

		/*
		 * STEP 8: Change pre-requisites
		 */
		this.preReq.setPreReqs(false, true, false, false, false, true,
		true);

		/*
		 * STEP 9: Tell endTurn last move before calling endTurn was
		 * PlaySpecialMove
		 */
		this.endTurnAfter.setEndTurnCalledAfter(false, true, false,
		false, false);

		/*
		 * STEP 10: Set Challenge Gap to false;
		 */
		this.challengeGap = false;
	}

	/**
	 * This method must always be called after a Player has finished playing
	 * his move. A player can play playLetterMove() followed by
	 * playSpecialMove() OR buy() OR exchange() OR pass(). Each of these 4
	 * mutually exclusive possibilites must be followed by a call to this
	 * method.
	 *
	 * @throw IllegalStateException if a player tries calling endTurn()
	 *        without satisfying one of the above 4 conditions.
	 */
	public void endTurn() {
		/*
		 * This is the game state after current player has player but
		 * before the next player plays. If special tile is activated,
		 * this game state should be modified and the final game
		 * state(that is pushed to stack) should be this modified temp.
		 */

		GameState temp1;
		System.out.println(preReq.preReqForEndTurn);
		if (!preReq.preReqForEndTurn) {
			throw new IllegalStateException(
			"End turn cannot be invoked now.");
		}

		if (endTurnAfter.playLetterMove) {

			/*
			 * STEP 1: Set tiles in place in the grid.
			 */
			LinkedHashMap<LetterTile, Coordinates> move = this.currentLetterMove
			.getMove();
			for (LetterTile letterTile : move.keySet()) {
				Coordinates temp = move.get(letterTile);
				board.setTile(letterTile, temp);
			}

			/*
			 * STEP 2: Remove these tiles from the player's Tile
			 * Rack.
			 */

			for (LetterTile lt : currentLetterMove.getMove()
			.keySet()) {
				players.get(currentPlayerIndex)
				.removeLetterTile(lt);
			}
			/*
			 * STEP 3: Supply the player with same number of
			 * LetterTiles as played.
			 */
			for (int i = 1; i <= currentLetterMove.getMove()
			.keySet().size(); i++) {
				players.get(currentPlayerIndex)
				.setLetterTile(letterBag.supplyLetter());
			}
			/*
			 * STEP 4: Create a temporary transient GameState that
			 * stores the current board state. Special tile can
			 * affect this board state.
			 */

			/*
			 * STEP 2: Trigger any activated special tile.
			 */

			/*
			 * STEP 3: Compute score on all of the remaining letters
			 * left in move.
			 */

			if (currentLetterMove.isEmpty()) {
				// this can happen if boom tile is activated
				players.get(currentPlayerIndex)
				.updateScoreBy(0);
				System.out.println("0 area");
			} else {
				int score = board
				.calculateScore(currentLetterMove);
				System.out.println(score);
				players.get(currentPlayerIndex)
				.updateScoreBy(score);
			}

			/*
			 * STEP 4: Obtain all new words formed in this move.
			 */
			this.currentMoveWords = board.getWordsMade();

		} else {
			//
		}

		/*
		 * COMMON STEPS TO ALL MOVES:
		 */

		/*
		 * STEP : Update moveCount, currentPlayerIndex.
		 */
		this.moveCount++;

		// Find the next person in order
		this.currentPlayerOrderIndex = (this.currentPlayerOrderIndex
		+ 1) % this.order.size();
		int prevCurrentPlayerIndex = this.currentPlayerIndex;
		this.currentPlayerIndex = this.order
		.get(currentPlayerOrderIndex);

		while (skipNextTurn.get(currentPlayerIndex) != false) {
			skipNextTurn.set(currentPlayerIndex, false);

			// move to the next person
			this.currentPlayerOrderIndex = (this.currentPlayerOrderIndex
			+ 1) % this.order.size();
			this.currentPlayerIndex = this.order
			.get(currentPlayerOrderIndex);
		}

		/*
		 * STEP : Update the fields of gsGivenToUser to
		 * currentGameState.
		 */

		gsGivenToUser.updateGameState(this.players,
		this.currentPlayerOrderIndex, this.order, this.board);
		gsGivenToUser.setWordsMade(currentMoveWords);

		/*
		 * STEP : Make a copy of updated game state and push copy to
		 * stack.
		 */
		GameState temp = new GameState(gsGivenToUser);
		temp.updateCurrPlayerIndex(prevCurrentPlayerIndex);
		this.gsStack.push(temp);

		// clear currentMove words of all strings made in current Move.
		// These words are now only in gsGivenToUser.
		this.currentMoveWords.clear();

		/*
		 * STEP : Change pre-requisites for next move;
		 */
		// This move can be challenged in the next round ONLY if this
		// round has a playLetterMove.
		boolean challengeableTemp;
		if (endTurnAfter.playLetterMove == true) {
			challengeableTemp = true;
		} else {
			challengeableTemp = false;
		}
		this.preReq.setPreReqs(true, false, true, true, true,
		challengeableTemp, false);

		/*
		 * STEP: Reset endTurn after variables
		 */
		this.endTurnAfter.setEndTurnCalledAfter(false, false, false,
		false, false);

		/*
		 * STEP : Set challengeGap to true.
		 */
		this.challengeGap = true;
	}

	/**
	 * A method which allows the current player to skip his/her turn. Must
	 * be followed by a call to endTurn().
	 *
	 * @throws IllegalStateException
	 *                 if anyone of playLetterMove OR playSpecialMove OR buy
	 *                 OR exchange methods is called before this.
	 */
	public void pass() {
		if (!preReq.preReqForPass) {
			throw new IllegalStateException(
			"Cannot invoke pass now.");
		}
		this.currentLetterMove = null;
		/*
		 * STEP: Tell endTurn last move before calling endTurn was pass
		 */
		this.endTurnAfter.setEndTurnCalledAfter(false, false, true,
		false, false);

		/*
		 * STEP 2: Setting pre-reqs for subsequent moves.
		 */
		this.preReq.setPreReqs(false, false, false, false, false, false,
		true);

		this.challengeGap = false;
	}

	/**
	 * Can be called to exchange LetterTile s in current Player's tile rack
	 * with LetterTiles in the LetterBag randomly.
	 *
	 * @param list
	 *                the LetterTiles to be exchanged.
	 * @throws NullPointerException
	 *                 if list is null
	 * @throws IllegalArgumentException
	 *                 1. if list has no LetterTile OR 2. If current Player
	 *                 does not possess the Tile he wishes to exchange. 3.
	 *                 If current Player
	 */
	public void exchange(List<LetterTile> list) {

		if (moveCount == 0) {
			throw new IllegalStateException(
			"First player cannot exchange tiles.");
		}
		if (!preReq.preReqForExchange) {
			throw new IllegalStateException(
			"Cannot invoke exchange now.");
		}
		if (list == null) {
			throw new NullPointerException(
			"Parameter cannot be null.");
		}

		if (list.size() == 0) {
			throw new IllegalArgumentException(
			"Atleast 1 tile needs to be passed for exchanging.");
		}

		/*
		 * STEP 1: Check is current player possesses the tiles to be
		 * exchanged.
		 */

		for (LetterTile tile : list) {
			if (!players.get(currentPlayerIndex).getLetterTileRack()
			.contains(tile)) {
				throw new IllegalArgumentException(
				"Current player don't possess this letter.");
			}
		}

		/*
		 * STEP 2: Remove these tiles from current player's tile rack.
		 */
		for (LetterTile tile : list) {
			players.get(currentPlayerIndex).removeLetterTile(tile);
		}

		/*
		 * STEP 3: Supply player with equal number of new letter tiles
		 * from LetterTileBag.
		 */
		for (int i = 1; i <= list.size(); i++) {
			players.get(currentPlayerIndex)
			.setLetterTile(letterBag.supplyLetter());
		}

		/*
		 * STEP 4: Place tiles to be exchanged back in bag
		 */
		for (LetterTile letterTile : list) {
			letterBag.storeLetter(letterTile);
		}

		/*
		 * STEP 5: Tell endTurn last move before calling endTurn was
		 * exchange
		 */
		this.endTurnAfter.setEndTurnCalledAfter(false, false, false,
		true, false);

		/*
		 * STEP : Setting pre-reqs for subsequent moves.
		 */
		this.preReq.setPreReqs(false, false, false, false, false, false,
		true);

		this.challengeGap = false;
		endTurn();
	}

	/**
	 * Can be invoked to buy special tiles provided the player's scores are
	 * sufficient. If successful, the special tiles bought are added to the
	 * player's special tile rack.
	 *
	 * @param purchase
	 *                is a map with keys being type of SpecialTile and
	 *                values being the number of tiles the player wants
	 *
	 */
	public void buySpecialTiles(Map<SpecialTile, Integer> purchase) {

		/*
		 * STEP: Tell endTurn last move before calling endTurn was
		 * buySpecialTiles
		 */
		this.endTurnAfter.setEndTurnCalledAfter(false, false, false,
		false, true);

		/*
		 * STEP : Setting pre-reqs for subsequent moves.
		 */
		this.endTurnAfter.setEndTurnCalledAfter(false, false, false,
		false, true);

		this.challengeGap = false;
	}

	/**
	 * Method should be invoked if a challenger at index challengerIndex in
	 * the list of players, returned by the getAllPlayersList() method of
	 * GameState, wants to challenge a particular word played by the
	 * previous player.
	 *
	 * @param challengerIndex
	 *                The index of the challenger.
	 * @param word
	 *                The word made by the previous player which is to be
	 *                challenged.
	 */
	public void challenge(int challengerIndex, String word) {

		/*
		 * STEP 1: Check if the Game is in a challengeable state. If
		 * previous player passed OR bought tile OR exchanged tile then
		 * game cannot be challenged. AND check if game is within the
		 * challengeGap time window. This is the time after endTurn() of
		 * previous player and playLetterMove/pass/exchange/buy of next
		 * player. This is the only window when previous player can be
		 * challenged.
		 */

		if (!preReq.challengeable) {
			throw new IllegalStateException(
			"Previous move not challengeable");
		}
		if (!challengeGap) {
			throw new IllegalStateException(
			"Game cannot be challenged now.");
		}

		if (challengerIndex < 0 || challengerIndex >= players.size()) {
			throw new IllegalArgumentException(
			"Invalid challengerIndex");
		}
		if (word == null || word.length() == 0) {
			throw new IllegalArgumentException("Word is empty");
		}

		/*
		 * STEP 2: Check if challenger and challengee are same person. -
		 * NOT ALLOWED!
		 */
		if (challengerIndex == gsStack.peek().getCurrentPlayerIndex()) {
			throw new IllegalArgumentException(
			" Challenger and Challengee cannot be same person.");
		}
		/*
		 * STEP 2: Check if word was indeed made in the last Move.
		 */
		GameState prev = gsStack.peek();
		if (!prev.getWordsMade().contains(word.trim().toUpperCase())) {
			throw new IllegalArgumentException(
			"This word was not created in the last move.");
		}

		/*
		 * STEP 3: Now that it is known that the word was made by the
		 * last player, check it it is a valid word.
		 */
		if (this.dictionary.isAWord(word.trim().toUpperCase())) {
			// if the word is valid, challenger loses next turn.

			// if the currentPlayer is not challenger
			if (this.currentPlayerIndex != challengerIndex) {
				this.skipNextTurn.set(challengerIndex, true);
			} else {
				// currentPlayer is challenger.
				// Therefore he loses current turn.

				// Satisfy pre-requisites for endTurn
				preReq.preReqForEndTurn = true;
				// End turn
				endTurn();
			}

		} else {
			// if the word is not valid.
			// challengee(previous player) loses next turn, his
			// played tiles are removed from the board and he loses
			// the points he got for that move.
			GameState previous = gsStack.pop();
			int challengeeIndex = previous.getCurrentPlayerIndex();

			// challengee loses next turn
			this.skipNextTurn.set(challengeeIndex, true);

			// board is returned to state before challengee played
			this.board = gsStack.peek().getCurrentBoardState();

			// challengee's score returned to that before he/she
			// played this invalid word.
			int difference = players.get(challengeeIndex).getScore()
			- prev.getAllPlayersList().get(challengeeIndex)
			.getScore();

			players.get(challengeeIndex).updateScoreBy(-difference);
		}

		/*
		 * STEP 4: Update challengeable state.
		 */
		preReq.challengeable = false;
	}

	/**
	 * A class that keeps tracks of which methods a player can invoke at any
	 * time. SHOULD HAVE MADE THIS ENUM
	 *
	 * @author akash
	 *
	 */
	public final class PreReqTracker {

		public boolean preReqForPlayLetter;
		public boolean preReqForPlaySpecial;
		public boolean preReqForBuy;
		public boolean preReqForExchange;
		public boolean preReqForPass;
		public boolean challengeable;
		public boolean preReqForEndTurn;

		private PreReqTracker() {
			/*
			 * On the first move, player cannot pass, buy special
			 * tiles, challenge previous move (since there isn't
			 * any). He can play letter followed by special OR
			 * exchange tiles.
			 */
			preReqForPlayLetter = true;
			preReqForPlaySpecial = false;
			preReqForBuy = false;
			preReqForExchange = true;
			preReqForPass = false;
			challengeable = false;
			preReqForEndTurn = false;
		}

		private void setPreReqs(boolean pl, boolean ps, boolean b,
		boolean e, boolean pass, boolean challengeable,
		boolean endTurn) {
			this.preReqForPlayLetter = pl;
			this.preReqForPlaySpecial = ps;
			this.preReqForBuy = b;
			this.preReqForExchange = e;
			this.preReqForPass = pass;
			this.challengeable = challengeable;
			this.preReqForEndTurn = endTurn;
		}
	}

	/**
	 * A class that keeps track of which method(s) were invoked before
	 * endTurn() was called.
	 *
	 * @author akash
	 *
	 */
	private final class EndTurnCalledAfter {
		private boolean playLetterMove;
		private boolean playSpecialMove;
		private boolean pass;
		private boolean exchange;
		private boolean buy;

		private EndTurnCalledAfter() {
			playLetterMove = false;
			playSpecialMove = false;
			pass = false;
			exchange = false;
			buy = false;
		}

		private void setEndTurnCalledAfter(boolean lm, boolean sm,
		boolean pass, boolean e, boolean b) {
			playLetterMove = lm;
			playSpecialMove = sm;
			this.pass = pass;
			this.exchange = e;
			this.buy = b;
		}
	}

	/**
	 * Indicates whether game is over.
	 *
	 * @return true if game is over, false otherwise.
	 */
	public boolean isGameOver() {
		return false;
	}

}
