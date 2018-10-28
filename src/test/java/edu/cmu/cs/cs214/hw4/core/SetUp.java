package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;

public class SetUp {
	ScrabbleGame g;
	ArrayList<String> names;
	GameState gs;
	LinkedHashMap<LetterTile, Coordinates> word;
	List<LetterTile> p1LetterTileRack;

	@Before
	public void setUp() {
		g = new ScrabbleGame();
		names = new ArrayList<>();
		word = new LinkedHashMap<>();
	}

	/*
	 * Testing invalid player numbers
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test1() {
		g.setNumPlayers(1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test2() {
		g.setNumPlayers(5);
	}

	/*
	 * Testing wrong number of names.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test3() {
		g.setNumPlayers(2);
		names.add("Donald");
		names.add("Abed");
		names.add("Zac");

		g.setPlayers(names);
	}

	/*
	 * Invoking methods in wrong order.
	 */
	@Test(expected = IllegalStateException.class)
	public void test4() {
		names.add("Donald");
		names.add("Abed");
		names.add("Zac");
		names.add("Ernie");
		g.setPlayers(names);

	}

	/*
	 * Invoking methods in wrong order.
	 */
	@Test(expected = IllegalStateException.class)
	public void test4_1() {
		g.playLetterMove(word);

	}

	/*
	 * Playing a move without any entries.
	 */
	@Test(expected = NullPointerException.class)
	public void test5() {
		g.setNumPlayers(4);
		names.add("Donald");
		names.add("Abed");
		names.add("Zac");
		names.add("Ernie");
		g.setPlayers(names);
		gs = g.startGame();
		g.playLetterMove(word);

	}

	/*
	 * Passing InvalidCoordinates
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test6() {
		g.setNumPlayers(4);
		names.add("Donald");
		names.add("Abed");
		names.add("Zac");
		names.add("Ernie");
		g.setPlayers(names);
		gs = g.startGame();
		p1LetterTileRack = gs.getAllPlayersList()
		.get(gs.getCurrentPlayerIndex()).getLetterTileRack();
		word.put(p1LetterTileRack.get(0), new Coordinates(15, -8));
		g.playLetterMove(word);

	}

	/*
	 * Passing two entries with same coordinates.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test7() {
		g.setNumPlayers(4);
		names.add("Donald");
		names.add("Abed");
		names.add("Zac");
		names.add("Ernie");
		g.setPlayers(names);
		gs = g.startGame();
		p1LetterTileRack = gs.getAllPlayersList()
		.get(gs.getCurrentPlayerIndex()).getLetterTileRack();
		word.put(p1LetterTileRack.get(0), new Coordinates(7, 7));
		word.put(p1LetterTileRack.get(1), new Coordinates(7, 7));

		g.playLetterMove(word);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test8() {
		g.setNumPlayers(4);
		names.add("Donald");
		names.add("Abed");
		names.add("Zac");
		names.add("Ernie");
		g.setPlayers(names);
		gs = g.startGame();

		p1LetterTileRack = gs.getAllPlayersList()
		.get(gs.getCurrentPlayerIndex()).getLetterTileRack();
		word.put(p1LetterTileRack.get(0), new Coordinates(8, 7));
		word.put(p1LetterTileRack.get(1), new Coordinates(7, 7));
		word.put(p1LetterTileRack.get(2), new Coordinates(7, 7));
		g.playLetterMove(word);

	}

	/*
	 * First player doesn't place letter on STAR square
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test9() {
		g.setNumPlayers(4);
		names.add("Donald");
		names.add("Abed");
		names.add("Zac");
		names.add("Ernie");
		g.setPlayers(names);
		gs = g.startGame();

		p1LetterTileRack = gs.getAllPlayersList()
		.get(gs.getCurrentPlayerIndex()).getLetterTileRack();
		word.put(p1LetterTileRack.get(0), new Coordinates(0, 0));
		word.put(p1LetterTileRack.get(1), new Coordinates(1, 2));
		word.put(p1LetterTileRack.get(2), new Coordinates(2, 7));

		g.playLetterMove(word);

	}

	/*
	 * First player places tile on Star but tiles not contiguous Subcase 1:
	 * Letters don't have same X or Y coordinate
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test10() {
		g.setNumPlayers(4);
		names.add("Donald");
		names.add("Abed");
		names.add("Zac");
		names.add("Ernie");
		g.setPlayers(names);
		gs = g.startGame();

		p1LetterTileRack = gs.getAllPlayersList()
		.get(gs.getCurrentPlayerIndex()).getLetterTileRack();
		word.put(p1LetterTileRack.get(0), new Coordinates(5, 6));
		word.put(p1LetterTileRack.get(1), new Coordinates(7, 7));
		word.put(p1LetterTileRack.get(2), new Coordinates(2, 7));

		g.playLetterMove(word);

	}

	/*
	 * First player places tile on Star but tiles not contiguous Subcase 2:
	 * Same X coordinate by differences in Y not 1.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test11() {
		g.setNumPlayers(4);
		names.add("Donald");
		names.add("Abed");
		names.add("Zac");
		names.add("Ernie");
		g.setPlayers(names);
		gs = g.startGame();

		p1LetterTileRack = gs.getAllPlayersList()
		.get(gs.getCurrentPlayerIndex()).getLetterTileRack();
		word.put(p1LetterTileRack.get(0), new Coordinates(7, 5));
		word.put(p1LetterTileRack.get(1), new Coordinates(7, 7));
		word.put(p1LetterTileRack.get(2), new Coordinates(7, 12));

		g.playLetterMove(word);

	}

	/*
	 * First player doesn't places tile on Star but tiles not contiguous
	 * Subcase 3: Letters have same Y coordinate but X coordinates are not
	 * successive.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test12() {
		g.setNumPlayers(4);
		names.add("Donald");
		names.add("Abed");
		names.add("Zac");
		names.add("Ernie");
		g.setPlayers(names);
		gs = g.startGame();

		p1LetterTileRack = gs.getAllPlayersList()
		.get(gs.getCurrentPlayerIndex()).getLetterTileRack();
		word.put(p1LetterTileRack.get(0), new Coordinates(3, 7));
		word.put(p1LetterTileRack.get(1), new Coordinates(7, 7));
		word.put(p1LetterTileRack.get(6), new Coordinates(14, 7));

		g.playLetterMove(word);

	}

	/*
	 * Contiguity satisfied but playing a tile that player does not have in
	 * his/her TileRack.
	 */

	@Test(expected = IllegalArgumentException.class)
	public void test13() {
		g.setNumPlayers(4);
		names.add("Donald");
		names.add("Abed");
		names.add("Zac");
		names.add("Ernie");
		g.setPlayers(names);
		gs = g.startGame();

		p1LetterTileRack = gs.getAllPlayersList()
		.get(gs.getCurrentPlayerIndex()).getLetterTileRack();
		word.put(new LetterTile('Z'), new Coordinates(6, 7));
		word.put(new LetterTile('X'), new Coordinates(7, 7));
		g.playLetterMove(word);

	}

}
