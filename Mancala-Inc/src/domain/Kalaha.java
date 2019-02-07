package domain;
import domain.Player;
import java.io.Serializable;

class Kalaha extends Cup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7725077136272313478L;

	public Kalaha(int counter, Player owner) {
		this.setStones(0);
		this.setOwner(owner);
		counter++;

		if (counter == 7) {
			this.setNextCup(new Pitt(owner.getOpponent(), counter));
		}
		if (counter == 14) {
			this.setNextCup(getStartingCup());
		}
	}

	@Override
	protected void passStones(int stones) {

		if (stones == 1 && this.getOwner().getHasTurn()) {
			this.addStones(1);
			stones--;
			// ended turn in own kalaha, hence turn is not switched. but might have been the
			// last stones
			this.getNextCup().checkGameEnd(0, this.getOwner().getPlayerTakingTurn());
		} else if (stones > 1 && this.getOwner().getHasTurn()) {
			this.addStones(1);
			stones--;
			this.getNextCup().passStones(stones);
		} else if (!this.getOwner().getHasTurn()) {
			this.getNextCup().passStones(stones);
		}

	}

	// player argument is the player to receive stones
	protected void passToKalaha(int stones, Player player) {
		if (this.getOwner() == player) {
			this.addStones(stones);
		} else {
			this.getNextCup().passToKalaha(stones, player);
		}

	}

	@Override
	protected void getToOpposing(int counter) {
		counter = counter * -1;
		this.getNextCup().getToOpposing(counter);
	}

	protected void checkGameEnd(int stones, Player playerTakingTurn) {
		if (this.getOwner() != playerTakingTurn) {
			stones = 0;
			this.getNextCup().checkGameEnd(stones, playerTakingTurn);
		} else if (stones == 0 && this.getOwner() == playerTakingTurn){
				// no more stones in cups of player taking turn, hence use this kalaha to determine winner
				if (this.getStones() > 24) {
					this.setGameOutcome(this.getOwner(), 0);
				} else if (this.getStones() == 24) {
					this.setGameOutcome(null, 0);
				} else  if(this.getStones() < 24) {
					this.setGameOutcome(this.getOwner().getOpponent(), 0);
				}
			} 
		}

	protected void setGameOutcome(Player winner, int counter) {
		if (this.getOwner() == winner) {
			this.getOwner().setGameOutcome(Player.gameOutcomes.WIN);
		}
		else if (this.getOwner().getOpponent() == winner) {
			this.getOwner().setGameOutcome(Player.gameOutcomes.LOSE);
		}
		else if (winner == null) {
			this.getOwner().setGameOutcome(Player.gameOutcomes.DRAW);
		}
		
		if (counter < 1) {
			this.getNextCup().setGameOutcome(winner, ++counter);
		}
	}
}
