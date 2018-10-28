package edu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.hw4.core.GameState;
import edu.cmu.cs.cs214.hw4.core.LetterTile;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;
import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;

public class TwoPlayerGame {
	ScrabbleGame g;
	ArrayList<String> names;
	GameState gs;
	LinkedHashMap<LetterTile, Coordinates> word;
	List<LetterTile> p1LetterTileRack;
	List<LetterTile> p2LetterTileRack;
	int firstPlayerIndex;
	int secondPlayerIndex;
	Player player;
	LetterTile centre;

	String firstPlayerWord = new String();

	@Before
	public void setUp() throws Exception {
		g = new ScrabbleGame();
		names = new ArrayList<>();
		word = new LinkedHashMap<>();
		g.setNumPlayers(2);
		names.add("Donald");
		names.add("Abed");
		g.setPlayers(names);
		gs = g.startGame();

		firstPlayerIndex = gs.getCurrentPlayerIndex();

		player = gs.getAllPlayersList().get(gs.getCurrentPlayerIndex());

		p1LetterTileRack = player.getLetterTileRack();
		centre = p1LetterTileRack.get(1);
		word.put(p1LetterTileRack.get(5), new Coordinates(7, 6));
		word.put(p1LetterTileRack.get(2), new Coordinates(7, 7));
		word.put(p1LetterTileRack.get(1), new Coordinates(7, 8));
		word.put(p1LetterTileRack.get(3), new Coordinates(7, 9));
		g.playLetterMove(word);
		g.endTurn();
	}

	/**
	 * Second player challenges first player.
	 */
	@Test
	public void test() {
		int challengerIndex = Math
		.abs((firstPlayerIndex + 1) % names.size());

	}

}
