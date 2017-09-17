package com.projet.jeu.context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;

import com.projet.jeu.jeux.Jeu;
import com.projet.jeu.jeux.JeuMode;
import com.projet.jeu.jeux.JeuType;

public class ApplicationContext {
	
	private static BufferedReader lecteur = new BufferedReader(new InputStreamReader(System.in));
	private JeuMode jeuMode;
	private JeuType jeuType;
	private Jeu jeu;
	private int nombreEssais;
	private int nombreCases;
	private boolean modeDevelop;
	private char exitControl = 'N';
	
	//Constructor a rellenar
	public ApplicationContext() {
		//Inisialisation des parametres de config
		lireConfigFile();
		
		//Inisialisation log4j
		BasicConfigurator.configure();
	}
	
	//Methode pour lire le fichier config
	private void lireConfigFile() {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("src/main/resources/config.properties"));
			this.nombreEssais = Integer.valueOf(properties.getProperty("nombreEssai"));
			this.nombreCases = Integer.valueOf(properties.getProperty("nombreCases"));
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
	
	//Methode pour gerer la sortie du jeu
	public char gererSortie() {
		System.out.println("Voulez-vous quitter le jeu? (O/N)");
		char resp;
		try {
			resp = lecteur.readLine().charAt(0);
		} catch (IOException e) {
			System.out.println("Erreur de saissie. Reesayez seulement avec O ou N");
			resp = exitControl;
		}
		return resp;
		
	}

	//Getters et Setters
	
	public JeuMode getJeuMode() {
		return jeuMode;
	}

	public void setJeuMode(JeuMode jeuMode) {
		this.jeuMode = jeuMode;
	}

	public Jeu getJeu() {
		return jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

	public int getNombreEssais() {
		return nombreEssais;
	}

	public int getNombreCases() {
		return nombreCases;
	}

	public boolean isModeDevelop() {
		return modeDevelop;
	}

	public static BufferedReader getLecteur() {
		return lecteur;
	}

	public static void setLecteur(BufferedReader lecteur) {
		ApplicationContext.lecteur = lecteur;
	}

	public JeuType getJeuType() {
		return jeuType;
	}

	public void setJeuType(JeuType jeuType) {
		this.jeuType = jeuType;
	}

	public char getExitControl() {
		return exitControl;
	}

	public void setExitControl(char exitControl) {
		this.exitControl = exitControl;
	}
	
	
	
	
	
	
	

	

}
