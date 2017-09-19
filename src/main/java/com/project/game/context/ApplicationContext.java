package com.project.game.context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;

import com.project.game.games.Game;
import com.project.game.games.GameMode;
import com.project.game.games.GameType;

public class ApplicationContext {
	
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private GameMode gameMode;
	private GameType gameType;
	private Game game;
	private int numberOfAttemps;
	private int numberOfBox;
	private boolean modeDevelop;
	private char exitControl = 'N';
	
	//Constructor a rellenar
	public ApplicationContext() {
		//Inisialisation des parametres de config
		readConfigFile();
		
		//Inisialisation log4j
		BasicConfigurator.configure();
	}
	
	//Methode pour lire le fichier config
	private void readConfigFile() {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("src/main/resources/config.properties"));
			this.numberOfAttemps = Integer.valueOf(properties.getProperty("nombreEssai"));
			this.numberOfBox = Integer.valueOf(properties.getProperty("nombreCases"));
			if (properties.getProperty("modeDevelopeur").equalsIgnoreCase("Oui")) {
				this.modeDevelop = true;
			} else {
				this.modeDevelop = false;
			}			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur de lecture du fichier config.properties");
			e.printStackTrace();
			
		}
	}
	
	//Methode pour gerer la sortie du game
	public char exitApplication() {
		System.out.println("Voulez-vous quitter le game? (O/N)");
		char resp;
		try {
			resp = reader.readLine().charAt(0);
		} catch (IOException e) {
			System.out.println("Erreur de saissie. Reesayez seulement avec O ou N");
			resp = exitControl;
		}
		return resp;
		
	}

	//Getters et Setters
	
	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getNumberOfAttemps() {
		return numberOfAttemps;
	}

	public int getNumberOfBox() {
		return numberOfBox;
	}

	public boolean isModeDevelop() {
		return modeDevelop;
	}

	public static BufferedReader getReader() {
		return reader;
	}

	public static void setReader(BufferedReader lecteur) {
		ApplicationContext.reader = lecteur;
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public char getExitControl() {
		return exitControl;
	}

	public void setExitControl(char exitControl) {
		this.exitControl = exitControl;
	}
	
	
	
	
	
	
	

	

}
