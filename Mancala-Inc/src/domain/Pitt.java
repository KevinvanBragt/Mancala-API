package domain;

import java.io.Serializable;

class Pitt extends Cup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2697928569673165924L;

	protected Pitt(Player owner, int counter) {
		this.setStones(4);
		this.setOwner(owner);
		counter++;

		if (counter == 1) {
			owner = new Player(true, "Player1");
			this.setOwner(owner);
			setStartingCup(this);
			this.setNextCup(new Pitt(owner, counter));
		} else if (counter < 6 || (counter > 6 && counter < 13)) {
			this.setNextCup(new Pitt(owner, counter));
		} else if (counter == 6 || counter == 13) {
			this.setNextCup(new Kalaha(counter, owner));
		} 

	}

	protected void giveAwayStones() {
		int stones = this.getStones();
		this.setStones(0);
		this.getNextCup().passStones(stones);
	}

	protected void passToKalaha(int stones, Player player) {
		this.getNextCup().passToKalaha(stones, player);
	}
	
	protected void giveToKalaha(Player player) {
		int stones = this.getStones();
		this.setStones(0);
		this.getNextCup().passToKalaha(stones, player);
	}

	@Override
	protected void passStones(int stones) {
		stones--;
		this.addStones(1);
		
		if (stones > 0) {
			this.getNextCup().passStones(stones);
		}	
		else if (stones == 0 && this.getStones() == 1 && this.getOwner().getHasTurn()) {
			this.getToOpposing(0);
			this.getOwner().switchTurns(0);
			this.checkGameEnd(0, this.getOwner().getPlayerTakingTurn());
		
		} else if (stones == 0 && (this.getStones() != 1 || !(this.getOwner().getHasTurn()))) {
			// did not end turn in kalaha, hence switch turn
			this.getOwner().switchTurns(0);
			this.checkGameEnd(0, this.getOwner().getPlayerTakingTurn());
		}		
	}	
		
	protected void getToOpposing(int counter) {
		counter++;
		if (counter == 0) {
			//found opposing kalaha, steal action, but only if there are any stones
			if (this.getStones() > 0) {
				this.steal();
			}
		}  else {
			getNextCup().getToOpposing(counter);
		}
	}
	
	protected void steal() {
		// the if else statement is for making sure the single stone added to the last cup (of player taking turn) is only cleared to kalaha if stealing actually happens (ie opposing cup != empty)
		if (this.getOwner().getHasTurn()) {
			this.giveToKalaha(this.getOwner().getPlayerTakingTurn());
		}
		else if (!(this.getOwner().getHasTurn())) {
			this.giveToKalaha(this.getOwner().getPlayerTakingTurn());
			this.getToOpposing(0);
		}
		
	}
	
	protected Cup returnCup() {
		return this;
	}
			
	protected void checkGameEnd(int stones, Player playerTakingTurn) {
		
		if (this.getOwner() == playerTakingTurn) {
			stones += this.getStones();
			this.getNextCup().checkGameEnd(stones, playerTakingTurn);
		} else {
			this.getNextCup().checkGameEnd(stones, playerTakingTurn);
		}
		
	}
	
	@Override
	protected void setGameOutcome(Player player, int counter) {
		this.getNextCup().setGameOutcome(player, counter);
		
	}

	
}
