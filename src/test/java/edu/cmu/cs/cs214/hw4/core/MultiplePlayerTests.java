package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;

public class MultiplePlayerTests {
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
	List<Integer> firstMoveTileScores;
	String firstPlayerWord = new String();

	@Before
	public void setUp() throws Exception {
		g = new ScrabbleGame();
		names = new ArrayList<>();
		word = new LinkedHashMap<>();
		g.setNumPlayers(4);
		names.add("Donald");
		names.add("Abed");
		names.add("Zac");
		names.add("Ernie");
		g.setPlayers(names);
		gs = g.startGame();

		firstPlayerIndex = gs.getCurrentPlayerIndex();

		player = gs.getAllPlayersList().get(gs.getCurrentPlayerIndex());
		firstMoveTileScores = new ArrayList<>();
		p1LetterTileRack = player.getLetterTileRack();
		centre = p1LetterTileRack.get(1);
		word.put(p1LetterTileRack.get(0), new Coordinates(7, 6));
		firstMoveTileScores.add(p1LetterTileRack.get(0).getPoints());
		word.put(p1LetterTileRack.get(1), new Coordinates(7, 7));
		firstMoveTileScores.add(p1LetterTileRack.get(1).getPoints());
		word.put(p1LetterTileRack.get(2), new Coordinates(7, 8));
		firstMoveTileScores.add(p1LetterTileRack.get(2).getPoints());
		word.put(p1LetterTileRack.get(3), new Coordinates(7, 9));
		firstMoveTileScores.add(p1LetterTileRack.get(3).getPoints());

		// Storing the word played by the first player
		for (int i = 0; i < 4; i++) {
			firstPlayerWord += p1LetterTileRack.get(i).getLetter();
		}

		g.playLetterMove(word);
		g.endTurn();
	}

	/*
	 * First player placed no special tiles. Second player places tile on
	 * previously occupied square.
	 */
	@Test
	public void test1() {
		player = gs.getAllPlayersList().get(gs.getCurrentPlayerIndex());
		p2LetterTileRack = player.getLetterTileRack();
		word = new LinkedHashMap<>();
		word.put(p2LetterTileRack.get(0), new Coordinates(7, 7));
		word.put(p2LetterTileRack.get(0), new Coordinates(7, 6));

		try {
			g.playLetterMove(word);
		} catch (IllegalArgumentException e) {
			assertEquals("Square is occupied.", e.getMessage());
		}
	}

	/*
	 * Second player's move does not touch first player's move
	 */
	@Test
	public void test2() {
		player = gs.getAllPlayersList().get(gs.getCurrentPlayerIndex());

		p2LetterTileRack = player.getLetterTileRack();
		word = new LinkedHashMap<>();

		word.put(p2LetterTileRack.get(0), new Coordinates(0, 0));
		word.put(p2LetterTileRack.get(1), new Coordinates(1, 0));
		try {
			g.playLetterMove(word);
		} catch (IllegalArgumentException e) {
			assertEquals(" Placed tiles must " + "touch atleast 1 "
			+ "preexisting tile.", e.getMessage());
		}
	}

	/*
	 * Second player plays a valid vertical move.
	 */
	@Test
	public void test3() {
		secondPlayerIndex = gs.getCurrentPlayerIndex();

		player = gs.getAllPlayersList().get(secondPlayerIndex);

		p2LetterTileRack = player.getLetterTileRack();
		word = new LinkedHashMap<>();

		word.put(p2LetterTileRack.get(0), new Coordinates(6, 7));
		word.put(p2LetterTileRack.get(1), new Coordinates(8, 7));
		g.playLetterMove(word);
		g.endTurn();

		int score = 0;

		for (int i = 0; i < 2; i++) {
			score += p2LetterTileRack.get(i).getPoints();
		}

		score += centre.getPoints();
		score *= 2;

		assertEquals(score,
		gs.getAllPlayersList().get(secondPlayerIndex).getScore());

	}

	/*
	 * Second player plays a valid horizontal move.
	 */
	@Test
	public void test4() {
		secondPlayerIndex = gs.getCurrentPlayerIndex();
		player = gs.getAllPlayersList().get(secondPlayerIndex);

		p2LetterTileRack = player.getLetterTileRack();
		word = new LinkedHashMap<>();

		word.put(p2LetterTileRack.get(0), new Coordinates(8, 6));
		word.put(p2LetterTileRack.get(1), new Coordinates(8, 7));
		word.put(p2LetterTileRack.get(2), new Coordinates(8, 8));
		word.put(p2LetterTileRack.get(3), new Coordinates(8, 9));
		g.playLetterMove(word);
		g.endTurn();

		int horizScore = 0;
		int a = p2LetterTileRack.get(0).getPoints();
		int b = p2LetterTileRack.get(1).getPoints();
		int c = p2LetterTileRack.get(2).getPoints();
		int d = p2LetterTileRack.get(3).getPoints();

		horizScore += 2 * (a + c) + b + d;

		int e = firstMoveTileScores.get(0) + 2 * a;
		int f = 2 * (firstMoveTileScores.get(1) + b);
		int g = firstMoveTileScores.get(2) + 2 * c;
		int h = firstMoveTileScores.get(3) + d;

		int verticalScore = e + f + g + h;

		assertEquals(verticalScore + horizScore,
		gs.getAllPlayersList().get(secondPlayerIndex).getScore());
	}

	/*
	 * Testing pass.
	 */
	@Test
	public void test5() {
		secondPlayerIndex = gs.getCurrentPlayerIndex();
		player = gs.getAllPlayersList().get(secondPlayerIndex);
		g.pass();
		g.endTurn();
		assertEquals(0,
		gs.getAllPlayersList().get(secondPlayerIndex).getScore());
	}

	/*
	 * Testing exchange tiles.
	 */
	@Test
	public void test6() {
		secondPlayerIndex = gs.getCurrentPlayerIndex();
		player = gs.getAllPlayersList().get(secondPlayerIndex);
		p2LetterTileRack = player.getLetterTileRack();
		System.out.println(gs.getAllPlayersList().get(secondPlayerIndex)
		.getLetterTileRack().toString());
		List<LetterTile> tilesExchange = new ArrayList<LetterTile>();
		tilesExchange.add(p2LetterTileRack.get(0));
		tilesExchange.add(p2LetterTileRack.get(1));
		tilesExchange.add(p2LetterTileRack.get(2));
		g.exchange(tilesExchange);
		g.endTurn();
		System.out.println(gs.getAllPlayersList().get(secondPlayerIndex)
		.getLetterTileRack().toString());
		assertEquals(7, gs.getAllPlayersList().get(secondPlayerIndex)
		.getLetterTileRack().size());

	}

	/*
	 * Testing challenge Subcase 1: Challenger is wrong : Previous player
	 * has not played the word claimed by challenger.
	 */

	@Test
	public void test7() {
		int challengerIndex = Math
		.abs((firstPlayerIndex + 1) % names.size());

		try {
			g.challenge(challengerIndex, "ZZZ");
		} catch (IllegalArgumentException e) {
			assertEquals(
			"This word was not created in the last move.",
			e.getMessage());
		}

	}

	/*
	 * Testing challenge: Subcase 2: Challenger is same as Challengee. NOT
	 * ALLOWED!
	 */

	@Test
	public void test8() {

		int challengerIndex = firstPlayerIndex;
		try {
			g.challenge(challengerIndex, firstPlayerWord);
		} catch (IllegalArgumentException e) {
			assertEquals(
			" Challenger and Challengee cannot be same person.",
			e.getMessage());
		}

	}

}
