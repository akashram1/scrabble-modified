package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;

public class FirstPlayerOnlyTests {
	ScrabbleGame g;
	ArrayList<String> names;
	GameState gs;
	LinkedHashMap<LetterTile, Coordinates> word;
	List<LetterTile> p1LetterTileRack;
	int firstPlayerLocation;
	Player player;

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
		firstPlayerLocation = gs.getCurrentPlayerIndex();
		player = gs.getAllPlayersList().get(firstPlayerLocation);
		p1LetterTileRack = player.getLetterTileRack();
	}

	/*
	 * First player satisfies all pre-reqs. Checking if score calculation is
	 * correct for a horizontal first move.
	 */

	@Test
	public void test1() {

		word.put(p1LetterTileRack.get(0), new Coordinates(7, 6));
		word.put(p1LetterTileRack.get(1), new Coordinates(7, 7));
		word.put(p1LetterTileRack.get(2), new Coordinates(7, 8));
		word.put(p1LetterTileRack.get(3), new Coordinates(7, 9));
		g.playLetterMove(word);
		g.endTurn();

		int expectScore = 0;
		for (int i = 0; i < 4; i++) {
			expectScore += p1LetterTileRack.get(i).getPoints();
		}
		expectScore *= 2;

		// UPDATE REFERENCES AFTER END TURN.
		player = gs.getAllPlayersList().get(firstPlayerLocation);
		p1LetterTileRack = player.getLetterTileRack();

		assertEquals(expectScore, player.getScore());

	}

	/*
	 * First player satisfies all pre-reqs. Checking if score calculation is
	 * correct for a vertical first move.
	 */
	@Test
	public void test2() {

		word.put(p1LetterTileRack.get(0), new Coordinates(5, 7));
		word.put(p1LetterTileRack.get(1), new Coordinates(6, 7));
		word.put(p1LetterTileRack.get(2), new Coordinates(7, 7));
		word.put(p1LetterTileRack.get(3), new Coordinates(8, 7));
		g.playLetterMove(word);
		g.endTurn();
		int expectScore = 0;
		for (int i = 0; i < 4; i++) {
			expectScore += p1LetterTileRack.get(i).getPoints();
		}
		expectScore *= 2;

		// UPDATE REFERENCES AFTER END TURN.
		player = gs.getAllPlayersList().get(firstPlayerLocation);
		p1LetterTileRack = player.getLetterTileRack();

		assertEquals(expectScore, player.getScore());

	}

	/*
	 * First player satisfies all pre-reqs. Checking if score calculation is
	 * correct for a horizontal first move covering the DLSquare at (7,3)
	 */

	@Test
	public void test3() {

		word.put(p1LetterTileRack.get(0), new Coordinates(7, 3));
		word.put(p1LetterTileRack.get(1), new Coordinates(7, 4));
		word.put(p1LetterTileRack.get(2), new Coordinates(7, 5));
		word.put(p1LetterTileRack.get(3), new Coordinates(7, 6));
		word.put(p1LetterTileRack.get(4), new Coordinates(7, 7));
		g.playLetterMove(word);
		g.endTurn();
		int expectScore = 0;
		expectScore = p1LetterTileRack.get(0).getPoints() * 2;
		for (int i = 1; i < 5; i++) {
			expectScore += p1LetterTileRack.get(i).getPoints();
		}
		expectScore *= 2;

		// UPDATE REFERENCES AFTER END TURN.
		player = gs.getAllPlayersList().get(firstPlayerLocation);
		p1LetterTileRack = player.getLetterTileRack();

		assertEquals(expectScore, player.getScore());

	}

	/*
	 * First player satisfies all pre-reqs. Checking if score calculation is
	 * correct for a vertical first move covering the DLSquare at (3,7)
	 */
	@Test
	public void test4() {

		word.put(p1LetterTileRack.get(0), new Coordinates(3, 7));
		word.put(p1LetterTileRack.get(1), new Coordinates(4, 7));
		word.put(p1LetterTileRack.get(2), new Coordinates(5, 7));
		word.put(p1LetterTileRack.get(3), new Coordinates(6, 7));
		word.put(p1LetterTileRack.get(4), new Coordinates(7, 7));
		g.playLetterMove(word);
		g.endTurn();
		int expectScore = 0;
		expectScore = p1LetterTileRack.get(0).getPoints() * 2;

		for (int i = 1; i < 5; i++) {
			expectScore += p1LetterTileRack.get(i).getPoints();
		}
		expectScore *= 2;

		// UPDATE REFERENCES AFTER END TURN.
		player = gs.getAllPlayersList().get(firstPlayerLocation);
		p1LetterTileRack = player.getLetterTileRack();

		assertEquals(expectScore, player.getScore());

	}

}
