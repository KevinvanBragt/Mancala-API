package domain;


public class Player {

	private boolean hasTurn;
	private String name;
	private Player opponent;
	
	Player(boolean hasTurn, String name){
		this.hasTurn = hasTurn;
		this.name = name;
	if (hasTurn) {
		this.setOpponent(new Player(false, "Player2"));
		this.getOpponent().setOpponent(this);
		}   
	}
	
	protected void setHasTurn(boolean Turn) {
		this.hasTurn = Turn;
	}

	public boolean getHasTurn() {
		return this.hasTurn;
	}
	
	protected String getName() {
		return this.name;
	}
	
	protected void switchTurns(int x) {
		this.hasTurn = !this.hasTurn;
		if (x == 0) {
			this.getOpponent().switchTurns(99);
		}
	}

	protected Player getOpponent() {
		return opponent;
	}

	protected void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	protected Player getPlayerTakingTurn() {
	Player playerTakingTurn = null;
	if (this.getHasTurn() == true) {
		playerTakingTurn = this;
	} else {
		playerTakingTurn = this.getOpponent();
	}
	return playerTakingTurn;
	}
	
	
}	

