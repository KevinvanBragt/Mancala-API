package domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement; 
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
			((Pitt) startingcup.getNextCup(selectedCup-1)).giveAwayStones();
		}
	}

	public int[] getGameState() {
		int[] gameState = new int[15];
		
		for (int x=0; x<14; x++) {
			gameState[x] = this.startingcup.getNextCup(x).getStones();
		}
		if (this.startingcup.getOwner().getHasTurn()) {	gameState[14] = 1;}
		else { gameState[14] = 0;}	
		
		return gameState;
	}
		
	private boolean validMove(int move) {
		if ((startingcup.getNextCup(move - 1).getStones() != 0) && (!(startingcup.getNextCup(move-1) instanceof Kalaha)) && (startingcup.getNextCup(move - 1).getOwner().getHasTurn())) {
			return true;
		} else {
		return false;
		}
	}
	
}
