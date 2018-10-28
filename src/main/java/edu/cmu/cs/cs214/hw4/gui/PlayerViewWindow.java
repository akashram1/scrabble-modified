package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import edu.cmu.cs.cs214.hw4.core.GameState;
import edu.cmu.cs.cs214.hw4.core.LetterTile;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;
import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;
import edu.cmu.cs.cs214.hw4.core.squares.Square;

public class PlayerViewWindow extends JFrame {

	public enum MoveTypes {
		LETTER(0), SPECIAL(1), END(2), PASS(3), BUY(4), EXCHANGE(
		5), CHALLENGE(6);

		private int n;

		private MoveTypes(int n) {
			this.n = n;
		}

		public int getIndex() {
			return n;
		}

		@Override
		public String toString() {
			switch (n) {
			case 0:
				return "Play Letter Tiles";
			case 1:
				return "Play Special Tiles";
			case 2:
				return "End Turn";
			case 3:
				return "Pass";
			case 4:
				return "Buy";
			case 5:
				return "Exchange";
			case 6:
				return "Challenge";

			default:
				return "";
			}

		}

	}

	private static final long serialVersionUID = 1L;

	/*
	 * Reference to the subject (whose observer this class is)
	 */
	private GameSubject subject;

	/*
	 * References from ScrabbleGame - user does not modify. Updated after
	 * every valid move.
	 */
	private GameState gameState;
	private ScrabbleGame game;
	private Player player;
	private List<LetterTile> letterRack;
	private int playerIndex; // index of this player in
					// gameState.getAllPlayersList()
	private List<Player> allPlayers;

	/*
	 * Variables needed for the GUI to play game - modified by user.
	 */
	private LetterTile selected; // letter tile selected by user
	private int selectedIndex;
	private Map<LetterTile, Integer> allSelectedLetterTiles;
	private LinkedHashMap<LetterTile, Coordinates> word;

	/*
	 * Display settings
	 */
	private static final int NUM_COLUMNS = 15;
	private static final int NUM_ROWS = 15;
	private static final int BORDER_GAP = 50;
	private static final Border border = new EmptyBorder(20, BORDER_GAP, 20,
	BORDER_GAP);
	private static final int SQUARE_SIZE = 60;
	private static final Color BACKGROUND_COL = new Color(238, 232, 170);
	private static final int MOVE_PANEL_SIZE = 200;
	private static final int INTER_MOVE_BUTTON_GAP = 10;
	private static final int NUM_OF_POSSIBLE_MOVES = 7;
	private static final int SCORE_AREA_WIDTH = 200;
	private static final int SCORE_AREA_HEIGHT = 400;
	private static final int LETTER_RACK_SIZE = 7;
	private static final int RACK_LEFT_BORDER = 300;
	private static final int RACK_RIGHT_BORDER = 550;
	private static final Font font = new JLabel().getFont();
	private static final Font scoresFont = new Font(Font.SANS_SERIF,
	Font.ITALIC, 15);
	private static final Font boardLetterFont = new Font(Font.MONOSPACED,
	Font.BOLD, 30);
	/*
	 * Panels
	 */
	JPanel boardPanel;
	JPanel movePanel;
	JPanel scores;
	JPanel composite;
	JPanel rack;
	JPanel lettersPane;
	JPanel specialPane;
	JPanel curr;
	JLabel currentPlayer;

	/*
	 * Button and label references
	 */
	private JButton[][] guiBoard;
	private JLabel[][] guiBoardLabels;

	private JButton[] letterRackButtons;
	private JLabel[] letterRackButtonsLabels;

	private JLabel[][] playerAndScore;
	private JButton[] moveButtons;

	/**
	 * Constructor.
	 *
	 * @param game
	 *                Reference to ScrabbleGame
	 * @param gs
	 *                Reference to GameState obtained on invoking
	 *                startGame()
	 * @param p
	 *                Reference to the Player whose view is shown by this
	 *                Frame.
	 */
	public PlayerViewWindow(ScrabbleGame game, GameState gs, int i,
	GameSubject subject) {
		this.subject = subject;

		/*
		 * Initializing ScrrableGame variables
		 */
		this.game = game;
		this.gameState = gs;
		this.playerIndex = i;
		this.player = gs.getAllPlayersList().get(i);
		this.selected = null;
		word = new LinkedHashMap<>();
		letterRack = player.getLetterTileRack();
		allPlayers = gs.getAllPlayersList();

		/*
		 * Initializing buttons and labels
		 */
		guiBoard = new JButton[NUM_ROWS][NUM_COLUMNS];
		guiBoardLabels = new JLabel[NUM_ROWS][NUM_COLUMNS];
		letterRackButtons = new JButton[LETTER_RACK_SIZE];
		letterRackButtonsLabels = new JLabel[letterRackButtons.length];
		moveButtons = new JButton[MoveTypes.values().length];

		playerAndScore = new JLabel[this.game.getNumPlayers()][2];
		for (i = 0; i < this.game.getNumPlayers(); i++) {

			playerAndScore[i][0] = new JLabel();
			playerAndScore[i][0].setText(
			this.gameState.getAllPlayersList().get(i).getName());
			playerAndScore[i][0].setFont(scoresFont);

			playerAndScore[i][1] = new JLabel();
			playerAndScore[i][1].setText(String.valueOf(
			this.gameState.getAllPlayersList().get(i).getScore()));
			playerAndScore[i][0].setFont(scoresFont);

		}

		selected = null;
		allSelectedLetterTiles = new HashMap<>();

		setUp();
	}

	private void setUp() {
		/*
		 * Frame setup
		 */
		setTitle(player.getName() + "'s view");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setBackground(BACKGROUND_COL);

		/*
		 * COMPONENT 1: Create a panel for the board.
		 */
		boardPanel = new JPanel();
		setUpBoardPanel(boardPanel);
		updateBoardPanel();

		/*
		 * COMPONENT 2: Create a grid panel for the various move
		 * buttons.
		 */
		movePanel = new JPanel();
		setUpMoveButtonsPanel(movePanel);
		updateMovePanel();

		/*
		 * COMPONENT 3: Create a panel to display scores of all players.
		 */
		scores = new JPanel();
		setUpScorePanel(scores);
		updateScorePanel();
		/*
		 * Nest the score panel and move-button panel in another panel.
		 */
		composite = new JPanel();
		composite.setLayout(new GridLayout(2, 1, 20, 20));
		composite.setBackground(BACKGROUND_COL);
		composite.setBorder(new EmptyBorder(0, 0, 0, BORDER_GAP));
		composite.add(scores);
		composite.add(movePanel);

		/*
		 * COMPONENT 4: Create a panel for LetterTile and SpecialTile
		 * racks
		 */
		rack = new JPanel();
		lettersPane = new JPanel();
		specialPane = new JPanel();
		setUpRackPanel(rack, lettersPane, specialPane);
		updateLetterRackButtons();
		/*
		 * COMPONENT 5: Create a JLabel to display the currentPlayer
		 */
		curr = new JPanel();
		currentPlayer = new JLabel(gameState.getAllPlayersList()
		.get(gameState.getCurrentPlayerIndex()).getName());
		setUpCurrPlayerPanel(curr, currentPlayer);
		updateCurrPlayerPanel();
		/*
		 * Add all components to the board.
		 */
		add(boardPanel, BorderLayout.CENTER);
		add(curr, BorderLayout.NORTH);
		add(composite, BorderLayout.EAST);
		add(rack, BorderLayout.SOUTH);

		// Finally:
		pack();
		setResizable(true);
		setVisible(true);
	}

	private void setUpCurrPlayerPanel(JPanel curr, JLabel currentPlayer) {

		curr.setLayout(new GridLayout(1, 2));
		curr.setBorder(new EmptyBorder(20, 20, 0, 800));
		curr.setBackground(BACKGROUND_COL);
		JLabel title = new JLabel("CURRENT PLAYER: ");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		curr.add(title);
		currentPlayer.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		curr.add(currentPlayer);

	}

	private void setUpRackPanel(JPanel rack, JPanel lettersPane,
	JPanel specialPane) {
		rack.setLayout(new GridLayout(2, 1, 10, 10));
		rack.setBackground(BACKGROUND_COL);
		rack.setBorder(new EmptyBorder(0, 350, 0, 550));
		// Creating panel for LetterTiles

		lettersPane.setLayout(new GridLayout(1, LETTER_RACK_SIZE,
		INTER_MOVE_BUTTON_GAP, INTER_MOVE_BUTTON_GAP));

		lettersPane.setBackground(BACKGROUND_COL);
		lettersPane.setPreferredSize(new Dimension(400, 50));

		for (int i = 0; i < letterRack.size(); i++) {
			letterRackButtons[i] = new JButton();
			letterRackButtons[i]
			.setHorizontalAlignment(JLabel.CENTER);
			letterRackButtons[i]
			.setVerticalAlignment(JLabel.CENTER);
			letterRackButtonsLabels[i] = new JLabel(
			letterRack.get(i).toString());
			letterRackButtonsLabels[i]
			.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
			letterRackButtons[i].add(letterRackButtonsLabels[i]);

			letterRackButtons[i].setBackground(LetterTile.color);
			letterRackButtons[i]
			.setBorder(new LineBorder(Color.BLACK));
			letterRackButtons[i].addActionListener(
			new LetterListener(letterRack, i, this));
			lettersPane.add(letterRackButtons[i]);

		}

		// Creating panel for SpecialTiles

		specialPane.setBackground(BACKGROUND_COL);

		// Add both to rack

		rack.add(lettersPane);
		rack.add(specialPane);
	}

	private void setUpScorePanel(JPanel scores) {
		scores.setBackground(Color.WHITE);
		scores.setLayout(new BorderLayout());

		Border outline = BorderFactory.createLineBorder(Color.BLACK);
		scores.setBorder(BorderFactory.createCompoundBorder(outline,
		BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		// Adding title
		JLabel scoresLabel = new JLabel("SCORES");
		scoresLabel.setHorizontalAlignment(JLabel.CENTER);
		scoresLabel.setVerticalAlignment(JLabel.CENTER);
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
		scoresLabel.setFont(font);

		scores.add(scoresLabel, BorderLayout.NORTH);

		// Adding players and scores
		JPanel nestedInScores = new JPanel();
		nestedInScores
		.setLayout(new GridLayout(game.getNumPlayers(), 2, 10, 10));
		nestedInScores.setBorder(new EmptyBorder(5, 50, 250, 20));
		for (int i = 0; i < game.getNumPlayers(); i++) {
			nestedInScores.add(playerAndScore[i][0]);
			nestedInScores.add(playerAndScore[i][1]);
		}
		nestedInScores.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		nestedInScores.setAlignmentY(JPanel.CENTER_ALIGNMENT);
		nestedInScores.setBackground(Color.WHITE);
		scores.add(nestedInScores, BorderLayout.CENTER);

	}

	private void setUpMoveButtonsPanel(JPanel movePanel) {

		movePanel.setBackground(BACKGROUND_COL);

		movePanel.setPreferredSize(
		new Dimension(MOVE_PANEL_SIZE, MOVE_PANEL_SIZE));

		movePanel.setLayout(new GridLayout(NUM_OF_POSSIBLE_MOVES, 1,
		INTER_MOVE_BUTTON_GAP, INTER_MOVE_BUTTON_GAP));

		for (int i = 0; i < NUM_OF_POSSIBLE_MOVES; i++) {
			moveButtons[i] = new JButton();
			moveButtons[i]
			.setText(MoveTypes.values()[i].toString());

			addListenerToMoveButton(moveButtons[i],
			MoveTypes.values()[i]);

			movePanel.add(moveButtons[i]);

		}

	}

	private void setUpBoardPanel(JPanel boardPanel) {

		// it should have a grid layout
		boardPanel.setLayout(new GridLayout(NUM_ROWS, NUM_COLUMNS));
		boardPanel.setBorder(border);
		boardPanel.setBackground(BACKGROUND_COL);
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLUMNS; j++) {
				guiBoard[i][j] = new JButton();
				guiBoard[i][j].setPreferredSize(
				new Dimension(SQUARE_SIZE, SQUARE_SIZE));
				Square square = gameState
				.getCurrentSquareState(i, j);

				guiBoard[i][j].setBackground(square.getColor());
				guiBoardLabels[i][j] = new JLabel(
				square.toString());
				guiBoardLabels[i][j]
				.setHorizontalAlignment(SwingConstants.CENTER);
				guiBoardLabels[i][j]
				.setVerticalAlignment(SwingConstants.CENTER);
				guiBoard[i][j].add(guiBoardLabels[i][j]);

				guiBoard[i][j].addActionListener(
				new SquareListener(i, j, word, this));
				boardPanel.add(guiBoard[i][j]);
			}
		}

	}

	private String getCurrentPlayerName() {
		ArrayList<Player> players = gameState.getAllPlayersList();
		int i = gameState.getCurrentPlayerIndex();
		return players.get(i).getName();
	}

	//////// METHODS RELATED TO CURRENT PLAYER PANEL ///////////////////////
	public void updateCurrPlayerPanel() {
		currentPlayer.setText(getCurrentPlayerName());
	}

	///////// METHODS RELATED TO SCORE PANEL ///////////////////////////////
	public void updateScorePanel() {

		for (int i = 0; i < allPlayers.size(); i++) {
			playerAndScore[i][1]
			.setText(String.valueOf(allPlayers.get(i).getScore()));
		}
	}

	/////// METHODS RELATED TO RACK AND BOARD PANELS ///////////////////

	/**
	 * Allows only 1 tile to be selected at a time. If a previous Tile is
	 * selected, then this method first re-enables that tile button and then
	 * sets selected as the parameter. Ensure selected is set to null after
	 * is called.
	 *
	 * @param selected
	 *                the selected LetterTile in letterRack
	 * @param index
	 *                index of selected tile in letterRack.
	 */
	public void setSelectedLetterTile(LetterTile selected, int index) {
		if (this.selected == null) {
			this.selected = selected;
			this.selectedIndex = index;

			this.allSelectedLetterTiles.put(selected, index);
		} else {
			// pre-existing selected tile has not been placed on
			// Board. Therefore re-enable that letter button.
			letterRackButtons[this.selectedIndex].setEnabled(true);

			// remove the pre-existing tile from
			// allSelectedLetterTiles as it
			// is no longer selected

			allSelectedLetterTiles.remove(this.selected);

			this.selected = selected;
			this.selectedIndex = index;
		}
	}

	/**
	 *
	 * @return the LetterTile selected by the user from the rack.
	 *
	 */
	public LetterTile getSelectedLetterTile() {
		return this.selected;
	}

	public void disableAllLetterTiles() {
		for (JButton button : letterRackButtons) {
			button.setEnabled(false);
		}
	}

	public void disableLetterTile(int index) {
		letterRackButtons[index].setEnabled(false);

	}

	public void enableLetterTile(int index) {
		letterRackButtons[index].setEnabled(true);

	}

	public void makeLetterTileVisible(int i) {
		letterRackButtons[i].setVisible(true);
	}

	public void makeLetterTileInvisible(int i) {
		letterRackButtons[i].setVisible(false);
	}

	public void removePlacedTilesFromBoard() {
		// Remove button from the board. Go back to actual look of board
		// now.
		for (Coordinates c : word.values()) {
			int i = c.getX();
			int j = c.getY();
			Square square = gameState.getCurrentSquareState(i, j);
			guiBoard[i][j].setBackground(square.getColor());
			SquareListener squareListener = (SquareListener) guiBoard[i][j]
			.getActionListeners()[0];
			squareListener.reinitializeListener(word);
			guiBoardLabels[i][j].setText(square.toString());
			guiBoardLabels[i][j].setFont(font);
		}

	}

	public void updateSquareButtonWithLetterTile(int x, int y,
	LetterTile t) {

		guiBoardLabels[x][y].setText(String.valueOf(t.getLetter()));
		guiBoardLabels[x][y].setFont(boardLetterFont);
		guiBoard[x][y].setBackground(LetterTile.color);

	}

	public void updateLetterRackButtons() {

		if (playerIndex == gameState.getCurrentPlayerIndex()) {
			// enable buttons
			for (int i = 0; i < letterRack.size(); i++) {
				letterRackButtonsLabels[i]
				.setText(String.valueOf(letterRack.get(i)));
				enableLetterTile(i);

				// remove earlier listener
				letterRackButtons[i].removeActionListener(
				letterRackButtons[i].getActionListeners()[0]);

				// attach new listener
				letterRackButtons[i].addActionListener(
				new LetterListener(letterRack, i, this));

			}
		} else {
			// disable buttons

			for (int i = 0; i < letterRack.size(); i++) {
				letterRackButtonsLabels[i]
				.setText(String.valueOf(letterRack.get(i)));
				letterRackButtons[i].setEnabled(false);
				letterRackButtons[i].setVisible(true);

				// remove earlier listener
				letterRackButtons[i].removeActionListener(
				letterRackButtons[i].getActionListeners()[0]);

				// attach new listener
				letterRackButtons[i].addActionListener(
				new LetterListener(letterRack, i, this));

			}
		}

	}

	public void updateBoardPanel() {
		if (playerIndex == gameState.getCurrentPlayerIndex()) {
			for (int i = 0; i < NUM_ROWS; i++) {
				for (int j = 0; j < NUM_COLUMNS; j++) {
					Square square = gameState
					.getCurrentSquareState(i, j);

					guiBoardLabels[i][j]
					.setText(square.toString());

					if (square.isOccupiedByLetterTile()) {
						guiBoardLabels[i][j]
						.setFont(boardLetterFont);
					}
					guiBoard[i][j]
					.setBackground(square.getColor());
					// enable
					guiBoard[i][j].setEnabled(true);
				}
			}
		} else {
			for (int i = 0; i < NUM_ROWS; i++) {
				for (int j = 0; j < NUM_COLUMNS; j++) {
					Square square = gameState
					.getCurrentSquareState(i, j);

					guiBoardLabels[i][j]
					.setText(square.toString());
					if (square.isOccupiedByLetterTile()) {
						guiBoardLabels[i][j]
						.setFont(boardLetterFont);
					}

					guiBoard[i][j]
					.setBackground(square.getColor());
					// disable
					guiBoard[i][j].setEnabled(false);
				}
			}

		}
	}

	///////// METHODS THAT HELP RE-ESTABLISHING INVARIANTS ///////
	/**
	 * Should be invoked immediately after a selected tile is placed on a
	 * Square on the board. Should also be invoked after playMove is
	 * invoked.
	 */
	public void resetSelectedToNull() {
		this.selected = null;
	}

	public void emptyAllSelectedLetterTiles() {
		this.allSelectedLetterTiles.clear();
	}

	public void emptyWordMap() {
		this.word.clear();
	}

	/////// MOVE BUTTON RELATED METHODS:////////

	public void disableMoveButtonsBasedOnPreqs() {
		if (!game.preReq.preReqForPlayLetter) {
			disableMoveButton(MoveTypes.LETTER);
		}

		if (!game.preReq.preReqForPlaySpecial) {
			disableMoveButton(MoveTypes.SPECIAL);
		}

		if (!game.preReq.preReqForEndTurn) {
			disableMoveButton(MoveTypes.END);
		}

		if (!game.preReq.preReqForPass) {
			disableMoveButton(MoveTypes.PASS);
		}

		if (!game.preReq.preReqForBuy) {
			disableMoveButton(MoveTypes.BUY);
		}

		if (!game.preReq.preReqForExchange) {
			disableMoveButton(MoveTypes.EXCHANGE);
		}

		if (!game.preReq.challengeable) {
			disableMoveButton(MoveTypes.CHALLENGE);
		}
	}

	public void disableMoveButton(MoveTypes moveType) {
		int index = moveType.getIndex();
		moveButtons[index].setEnabled(false);
	}

	public void enableMoveButton(MoveTypes moveType) {
		int index = moveType.getIndex();
		moveButtons[index].setEnabled(true);
	}

	private void addListenerToMoveButton(JButton jButton,
	MoveTypes moveTypes) {
		switch (moveTypes) {
		case LETTER:
			jButton.addActionListener(
			new PlayLetterMoveListener(game, this, this.word));
			break;
		case SPECIAL:
			jButton.addActionListener(new SpecialMoveListener());
			break;
		case END:
			jButton.addActionListener(
			new EndTurnListener(game, this, this.subject));
			break;
		case PASS:
			jButton.addActionListener(new PassListener());
			break;
		case BUY:
			jButton.addActionListener(new BuyListener());
			break;
		case EXCHANGE:
			jButton.addActionListener(
			new ExchangeListener(this, this.game, this.letterRack));
			break;
		case CHALLENGE:
			jButton.addActionListener(new ChallengeListener());
			break;
		default:
			break;

		}

	}

	private void updateMovePanel() {
		// /* Listing all of the pre-reqs */
		// boolean[] prereqs = new boolean[MoveTypes.values().length];
		// prereqs[MoveTypes.LETTER
		// .getIndex()] = game.preReq.preReqForPlayLetter;
		//
		// prereqs[MoveTypes.SPECIAL
		// .getIndex()] = game.preReq.preReqForPlaySpecial;
		//
		// prereqs[MoveTypes.END
		// .getIndex()] = game.preReq.preReqForEndTurn;
		//
		// prereqs[MoveTypes.PASS.getIndex()] =
		// game.preReq.preReqForPass;
		//
		// prereqs[MoveTypes.BUY.getIndex()] = game.preReq.preReqForBuy;
		//
		// prereqs[MoveTypes.EXCHANGE
		// .getIndex()] = game.preReq.preReqForExchange;
		//
		// prereqs[MoveTypes.CHALLENGE
		// .getIndex()] = game.preReq.challengeable;

		if (gameState.getCurrentPlayerIndex() == playerIndex) {
			// Enable buttons based on pre-reqs
			for (int i = 0; i < MoveTypes.values().length; i++) {

				moveButtons[i].setEnabled(true);

				// detach old listener
				moveButtons[i].removeActionListener(
				moveButtons[i].getActionListeners()[0]);

				// attach new listener
				addListenerToMoveButton(moveButtons[i],
				MoveTypes.values()[i]);

			}

			if (!game.preReq.challengeable) {
				moveButtons[MoveTypes.CHALLENGE.getIndex()]
				.setEnabled(false);
			}

		} else {
			for (int i = 0; i < MoveTypes.values().length; i++) {
				moveButtons[i].setEnabled(false);

				// detach old listener
				moveButtons[i].removeActionListener(
				moveButtons[i].getActionListeners()[0]);

				// attach new listener
				addListenerToMoveButton(moveButtons[i],
				MoveTypes.values()[i]);

			}

			if (game.preReq.challengeable) {
				moveButtons[MoveTypes.CHALLENGE.getIndex()]
				.setEnabled(true);
			}
		}
	}

	//// METHODS RELATED TO UNDOING AN INVALID playLetterMove ///
	public void deselectAllSelectedTiles() {

		// Re-enable buttons in the letter tile rack
		for (Integer index : allSelectedLetterTiles.values()) {

			letterRackButtons[index].setEnabled(true);
			letterRackButtons[index].setVisible(true);
		}

	}

	/////// UPDATE METHOD //////

	public void update() {

		// Update references
		this.allPlayers = gameState.getAllPlayersList();
		this.player = allPlayers.get(playerIndex);
		this.letterRack = player.getLetterTileRack();
		System.out.println(game.preReq.preReqForPlayLetter);
		// Update current Player panel;
		updateCurrPlayerPanel();

		// Update score panel
		updateScorePanel();

		// Update letterRackButtons panel
		updateLetterRackButtons();

		// Update board panel
		updateBoardPanel();

		// Update Move button panel'
		updateMovePanel();
	}
}
