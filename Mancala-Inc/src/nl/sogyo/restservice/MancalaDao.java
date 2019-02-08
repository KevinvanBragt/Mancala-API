package nl.sogyo.restservice;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import domain.Mancala;


public class MancalaDao {

	private HashMap<Integer, Mancala> savedMancalaGames = new HashMap<>();
	private static MancalaDao uniqueMancalaDao = null;
	
	private MancalaDao() {	
		//load
	}
		
	public static MancalaDao getMancalaDaoInstance() {
		if (uniqueMancalaDao == null) {
			uniqueMancalaDao = new MancalaDao();
		}
		return uniqueMancalaDao;
	}
	
	protected HashMap<Integer, Mancala> getGames(){
		return this.savedMancalaGames;
	}
	
	public void setGame(Mancala mancala, int id) {
		savedMancalaGames.put(id, mancala);
	}
	
	public int setGame(Mancala mancala) {
		int id = generateId();
		setGame(mancala, id);
		return id;
	}
	
	public Mancala getGame(int id) {
		return savedMancalaGames.get(id);
	}
	
	protected boolean deleteGame(int id) {
		if (savedMancalaGames.containsKey(id)) {
			savedMancalaGames.remove(id);
			return true;
		} else { return false; }
		
		
	}
	
	private int generateId() {
		//inefficient but easy
		int id = 0;
		do {
			id = new Random().nextInt();
			if (id < 0) { id *= -1; }
		} while(savedMancalaGames.containsKey(id));
		return id;
	}
}
