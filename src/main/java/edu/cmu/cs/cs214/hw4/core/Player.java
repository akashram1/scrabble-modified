package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.cs.cs214.hw4.core.specialTile.SpecialTile;

public class Player {
	private String name;
	private TileRack rack;
	private int score;

	public Player(String name) {
		this.name = name;
		this.rack = new TileRack();
		this.score = 0;

	}

	/**
	 * A copy constructor
	 *
	 */
	public Player(Player p) {
		this.name = p.name;
		this.rack = new TileRack(p.getTileRack());
		this.score = p.score;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public void seeTileRack() {
		this.rack.toString();
	}

	public List<LetterTile> getLetterTileRack() {
		/*
		 * Returns a defensive copy.
		 */
		return rack.getLetterTileRack();
	}

	public List<SpecialTile> getSpecialTileRack() {
		/*
		 * Returns a defensive copy.
		 */
		return rack.getSpecialTileRack();
	}

	public int getScore() {
		return this.score;
	}

	void updateScoreBy(int n) {
		this.score += n;
	}

	private TileRack getTileRack() {
		return this.rack;
	}

	void setLetterTile(LetterTile lt) {
		/*
		 * NOTE : This method in TileRack DOES NOT create a defensive
		 * copy when storing a letter in the Tile Rack.
		 */
		this.rack.addLetterTileToRack(lt);
	}

	void setSpecialTile(SpecialTile st) {
		/*
		 * NOTE : This method in TileRack DOES NOT create a defensive
		 * copy when storing a special tile in the Tile Rack.
		 */
		this.rack.addSpecialTileToRack(st);
	}

	void removeLetterTile(LetterTile lt) {
		this.rack.removeLetterTile(lt);
	}

	void removeSpecialTile(SpecialTile st) {
		this.rack.removeSpecialTile(st);
	}

	boolean hasSpecialTile(SpecialTile st) {

		return this.rack.specialTiles.contains(st);
	}

	boolean hasLetterTile(LetterTile lt) {

		return this.rack.letterTiles.contains(lt);
	}

	private class TileRack {
		private List<LetterTile> letterTiles;
		private List<SpecialTile> specialTiles;

		TileRack() {
			this.letterTiles = new ArrayList<>(7);
			this.specialTiles = new ArrayList<>();
		}

		/**
		 * A copy constructor.
		 *
		 * @param tr
		 */
		private TileRack(TileRack tr) {
			// getLetterTileRack and getSpecialTileRack both return
			// defensive copies. Therefore deep copying done.
			this.letterTiles = tr.getLetterTileRack();
			this.specialTiles = tr.getSpecialTileRack();
		}

		private void addLetterTileToRack(LetterTile lt)
		throws NullPointerException, IllegalStateException {
			if (lt == null) {
				throw new NullPointerException(
				"LetterTile lt can't be null");
			}

			if (letterTiles.size() < 7) {
				letterTiles.add(lt);
			} else {
				throw new IllegalStateException(
				"Rack full! Cannot store this tile.");
			}
		}

		private void addSpecialTileToRack(SpecialTile st) {
			if (st == null) {
				throw new NullPointerException(
				"SpecialTile st can't be null");
			}

			/*
			 * No limit on the number of special tiles a person can
			 * possess.
			 */
			this.specialTiles.add(st);

		}

		private void removeLetterTile(LetterTile lt) {
			if (lt == null) {
				throw new NullPointerException();
			}
			this.letterTiles.remove(lt);
		}

		private void removeSpecialTile(SpecialTile st) {
			if (st == null) {
				throw new NullPointerException();
			}
			this.specialTiles.remove(st);
		}

		/*
		 * NOTE TO SELF: For the next 2 methods, Defensive Copying? ANS:
		 * YES as only the game player will call this method in Player
		 * and he shouldn't and cannot modify the rack.
		 */
		private List<LetterTile> getLetterTileRack() {
			return letterTileRackCopier(this.letterTiles);
		}

		private List<SpecialTile> getSpecialTileRack() {
			return specialTileRackCopier(this.specialTiles);
		}

		private List<LetterTile> letterTileRackCopier(
		List<LetterTile> lt) {
			List<LetterTile> ltNew = new ArrayList<>(lt.size());
			for (LetterTile l : lt) {
				ltNew.add(new LetterTile(l));
			}
			return ltNew;
		}

		private List<SpecialTile> specialTileRackCopier(
		List<SpecialTile> st) {
			List<SpecialTile> stNew = new ArrayList<>(st.size());

			/*
			 * IMPORTANT!!!!! When implementing SpecialTiles,
			 * implement this
			 */
			// for (SpecialTile s : st) {
			// stNew.add(new SpecialTile(s));
			// }
			return stNew;
		}

		@Override
		public String toString() {
			if (this.letterTiles.size() == 0
			&& this.specialTiles.size() == 0) {
				return "Empty TileRack";
			} else if (this.letterTiles.size() != 0
			&& this.specialTiles.size() == 0) {
				StringBuilder str = new StringBuilder();
				str.append("LETTER TILE RACK:\n");
				str.append(this.seeLetterTileRack());
				str.append("EMPTY SPECIAL TILE RACK.\n");
				return str.toString();
			} else {
				StringBuilder str = new StringBuilder();
				str.append("LETTER TILE RACK:\n");
				str.append(this.seeLetterTileRack());
				str.append("SPECIAL TILE RACK:\n");
				str.append(this.seeSpecialTileRack());
				str.append("\n");
				return str.toString();
			}
		}

		private String seeLetterTileRack() {
			StringBuilder temp = new StringBuilder();
			for (LetterTile l : this.letterTiles) {
				temp.append(l.getLetter() + " ");
			}
			temp.append("\n");
			return temp.toString();
		}

		private String seeSpecialTileRack() {
			StringBuilder temp = new StringBuilder();
			for (SpecialTile s : this.specialTiles) {
				temp.append(s.toString() + " ");

			}
			temp.append("\n");
			return temp.toString();
		}

	}

}
