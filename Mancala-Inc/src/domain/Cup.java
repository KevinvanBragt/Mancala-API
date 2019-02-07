package domain;

import java.io.Serializable;

public abstract class Cup implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4379109383192442591L;
	private static Cup startingCup;
	private int stones = 4;
	private Cup nextCup;
	private Player owner;

	protected static Cup getStartingCup() {
		return startingCup;
	}

	protected final static void setStartingCup(Cup startingcup) {
		Cup.startingCup = startingcup;
	}
	
	protected final void setStones(int stones) {
		this.stones = stones;
	}
	
	public final int getStones() {
		return this.stones;
	}
	
	protected final void setNextCup(Cup nextCup) {
		this.nextCup = nextCup;
	}
	
	protected final Cup getNextCup() {
		return this.nextCup;
	}
		
	public Cup getNextCup(int x) {
		// start counting at one time	
		Cup reference = this;
		while (x > 0) {
			Cup y = reference.nextCup;
			reference = y;
			x--;
		} 
		return reference;
	}
	
	protected final void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public final Player getOwner() {
		return this.owner;
	}

	protected final void addStones(int stones) {
		this.setStones(this.getStones() + stones);
	}
	
	protected abstract void checkGameEnd(int stones, Player player);

	protected abstract void passStones(int stones);
	
	protected abstract void passToKalaha(int stones, Player player);

	protected abstract void getToOpposing(int counter);
	
	protected abstract void setGameOutcome(Player player, int counter);
		
}
