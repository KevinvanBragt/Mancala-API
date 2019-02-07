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

import domain.Mancala;


public class MancalaDao {

	private HashMap<Integer, Mancala> savedMancalaGames = new HashMap<>();
	private static MancalaDao uniqueMancalaDao = null;
	
	private MancalaDao() {	
		//actual loading
		//this.savedMancalaGames = loadGames();
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
		//actual persistence
		//saveGames();
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
	
	private HashMap<Integer, Mancala> loadGames() {
		HashMap<Integer, Mancala> mancalaGames = null;
		FileInputStream fis = null;
		try {
			File file = new File("MancalaPersistence.dat");
			if (!file.exists()) {
				mancalaGames = new HashMap<>();
				mancalaGames.put(0, new Mancala());
				this.savedMancalaGames = mancalaGames;
				saveGames();
			} else {
				fis = new FileInputStream(file);
				try (ObjectInputStream ois = new ObjectInputStream(fis)){
						mancalaGames = (HashMap<Integer, Mancala>) ois.readObject();
				} 	
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try{
				if (fis != null) {
					fis.close();
				}
			}
			catch (IOException e) { e.printStackTrace();
			} catch (NullPointerException e) { e.printStackTrace();
			}
		}
		return mancalaGames;
	}

	private void saveGames() {
		try {
			File file = new File("MancalaPersistence.dat");
			FileOutputStream fos;
			fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(savedMancalaGames);
			oos.close();
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace();
		}
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
