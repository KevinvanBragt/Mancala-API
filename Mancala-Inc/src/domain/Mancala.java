package domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class Mancala implements Serializable {

	/** facade for Mancala domain and API **/
	
	private static final long serialVersionUID = 1L;
	public final Cup startingcup;

	public Mancala() {
		this.startingcup = new Pitt(null, 0);
	}
	
	/** domain methods **/
	
	public void makeMoveFacade(int selectedCup) {
		if (validMove(selectedCup)) {
			((Pitt) startingcup.getNextCup(selectedCup)).giveAwayStones();
		}
	}

	public int[] getGameState() {
		int[] gameState = new int[16];
		
		for (int x=0; x<14; x++) {
			gameState[x] = this.startingcup.getNextCup(x).getStones();
		}
		
		if (this.startingcup.getOwner().getHasTurn()) {	gameState[14] = 1;}
		else { gameState[14] = 2;}	
		
		setGameStateWinner(gameState);
		
		return gameState;
	}
	
	private void setGameStateWinner(int[] gameState) {
		if (this.startingcup.getOwner().getGameOutcome() == null) {
			gameState[15] = 0;
		}
		else if (this.startingcup.getOwner().getGameOutcome() == Player.gameOutcomes.WIN) {
			gameState[15] = 1;
		}
		else if (this.startingcup.getOwner().getGameOutcome() == Player.gameOutcomes.LOSE) {
			gameState[15] = 2;
		}
		else if (this.startingcup.getOwner().getGameOutcome() == Player.gameOutcomes.DRAW) {
			gameState[15] = -1;
		}
	}
	
	private boolean validMove(int move) {
		if ((startingcup.getNextCup(move).getStones() != 0) && (!(startingcup.getNextCup(move) instanceof Kalaha)) && (startingcup.getNextCup(move).getOwner().getHasTurn())) {
			return true;
		} else {
		return false;
		}
	}
	
}
