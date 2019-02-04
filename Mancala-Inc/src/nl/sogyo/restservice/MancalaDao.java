package nl.sogyo.restservice;

import java.util.ArrayList; 
import domain.Mancala;


public class MancalaDao {

	private ArrayList<Mancala> savedMancalaGames = new ArrayList<>();
	private static MancalaDao uniqueMancalaDao = null;
	
	private MancalaDao() {}
	public static MancalaDao getMancalaDaoInstance() {
		if (uniqueMancalaDao == null) {
			uniqueMancalaDao = new MancalaDao();
		}
		return uniqueMancalaDao;
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
