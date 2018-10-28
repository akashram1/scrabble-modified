package edu.cmu.cs.cs214.hw4.core.specialTile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SpecialTileBag {
	public enum SpecialTileType {
		B2Z(0), NEG(1), REV(2), BOOM(3), SKIP(4);

		private int index;

		private SpecialTileType(int n) {
			this.index = n;
		}

		public int getIndex() {
			return index;
		}

		@Override
		public String toString() {
			switch (index) {
			case 0:
				return new BackToZeroTile(-1).toString();
			case 1:
				return new NegativePointsTile(-1).toString();
			case 2:
				return new ReverseOrderTile(-1).toString();
			case 3:
				return new BoomTile(-1).toString();
			case 4:
				return new SkipATurnTile(-1).toString();
			default:
				return null;
			}
		}

	}

	private List<LinkedList<SpecialTile>> tilesLeft;

	/*
	 * The initial number of each type of SpecialTile.
	 */
	private static final int EACH_TYPE_INIT_COUNT = 5;

	public SpecialTileBag() {
		tilesLeft = new ArrayList<LinkedList<SpecialTile>>();
		fill();
	}

	private void fill() {

		/*
		 * Creating the list of lists.
		 */
		for (int i = 0; i < SpecialTileType.values().length; i++) {
			this.tilesLeft.add(new LinkedList<>());
		}

		/*
		 * Inserting each special tile in the correct LinkedList
		 */
		for (int i = 1; i <= EACH_TYPE_INIT_COUNT; i++) {
			this.tilesLeft.get(SpecialTileType.B2Z.getIndex())
			.add(new BackToZeroTile(i));

			this.tilesLeft.get(SpecialTileType.NEG.getIndex())
			.add(new NegativePointsTile(i));

			this.tilesLeft.get(SpecialTileType.REV.getIndex())
			.add(new ReverseOrderTile(i));

			this.tilesLeft.get(SpecialTileType.BOOM.getIndex())
			.add(new BoomTile(i));

			this.tilesLeft.get(SpecialTileType.SKIP.getIndex())
			.add(new SkipATurnTile(i));
		}

	}

	/**
	 * Returns reference to an instance of a SpecialTileType s. Will return
	 * null if there are no tiles of type s left.
	 *
	 * @param s
	 *                A particular type of SpecialTileType
	 * @return reference to a new SpecialTile of kind s
	 */
	public SpecialTile supply(SpecialTileType s) {
		/*
		 * STEP 1:Check if any Special tiles of this type are left.
		 */
		if (tilesLeft.get(s.getIndex()).isEmpty()) {
			return null;
		}

		/*
		 * STEP 2: Return an instance of this type
		 */
		return tilesLeft.get(s.getIndex()).pollFirst();

	}

	/**
	 * Return a hashMap with keys being the String representation of each
	 * SpecialTile and the values being the number of tiles of the
	 * corresponding type left in the bag.
	 *
	 * @return HashMap described above.
	 */
	public Map<String, Integer> getState() {
		Map<String, Integer> state = new HashMap<>();
		SpecialTileType[] arr = SpecialTileType.values();
		for (int i = 0; i < SpecialTileType.values().length; i++) {
			state.put(arr[i].toString(), tilesLeft.get(i).size());
		}
		return null;
	}

}
