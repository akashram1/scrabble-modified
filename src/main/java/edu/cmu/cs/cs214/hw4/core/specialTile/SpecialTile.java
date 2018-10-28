package edu.cmu.cs.cs214.hw4.core.specialTile;

import edu.cmu.cs.cs214.hw4.core.GameState;
import edu.cmu.cs.cs214.hw4.core.Tile;
import edu.cmu.cs.cs214.hw4.core.coordinates.Coordinates;

/**
 * Encapsulates features and behaviour of a Special tile.
 *
 * @author akash
 *
 */
public abstract class SpecialTile implements Tile {

	protected Coordinates location;
	protected GameState gs;
	private int cost;
	protected String name;
	private int ID;
	private int purchaserIndex;
	private int activatorIndex;

	/**
	 * Parameterized constructor.
	 *
	 * @param cost
	 *                cost of the tile
	 * @param ID
	 *                Unique ID of the tile
	 */
	protected SpecialTile(int cost, int ID) {
		this.cost = cost;
		this.ID = ID;
		this.location = null;
	}

	/**
	 * Copy constructor
	 */

	SpecialTile(SpecialTile st) {

	}

	@Override
	public void setCoordinates(Coordinates c) throws NullPointerException {
		if (c == null) {
			throw new NullPointerException(
			"Coordinates c can't be null");
		}
		this.location = new Coordinates(c.getX(), c.getY());
	}

	@Override
	public Coordinates getCoordinates() {
		return this.location;
	}

	@Override
	public int getPoints() {
		return this.cost;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SpecialTile)) {
			return false;
		}

		SpecialTile that = (SpecialTile) obj;
		if (this.name.equals(that.name) && this.ID == that.ID) {
			return true;
		}

		return false;
	}

	/**
	 * Should be called when a player purchases this tile.
	 *
	 * @param i
	 *                is the index of the player in "players" list in
	 *                ScrableGame who purchased this tile.
	 */
	public void setPurchaser(int i) {
		this.purchaserIndex = i;
	}

	/**
	 * Should be called when a player places a letter tile on this tile.
	 *
	 * @param i
	 *                is the index of the player in "players" list in
	 *                ScrabbleGame who activated this tile.
	 */
	public void setActivator(int i) {
		this.activatorIndex = i;
	}

	/**
	 * Should be invoked when a letterTile is placed on this tile. Ensure
	 * that gs should be that gameState after the current player (who
	 * activated this tile) made his move.
	 *
	 * @param gs
	 *                reference to the current GameState which the special
	 *                tile will modify.
	 */

	abstract void activate(GameState gs);
}
