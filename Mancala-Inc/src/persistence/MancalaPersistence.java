package persistence;
import java.util.ArrayList;

import domain.Mancala;

public class MancalaPersistence {

	private ArrayList<Mancala> savedMancalaGames = new ArrayList<>();
	private static MancalaPersistence uniqueMancalaPersistence;
	
	private MancalaPersistence() {}
	public static MancalaPersistence getMancalaPersistenceInstance() {
		if (uniqueMancalaPersistence == null) {
			uniqueMancalaPersistence = new MancalaPersistence();
		}
		return uniqueMancalaPersistence;
	}
	
	public void saveGame(Mancala mancala) {
		//let's make it work first without adding id's
		savedMancalaGames.set(0, mancala);
	}
	
	public Mancala getSavedGame() {
		//idem for getting it back, no id yet
		return savedMancalaGames.get(0);
	}
	
}
