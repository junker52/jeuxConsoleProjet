package com.projet.jeu.context;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;

import com.projet.jeu.jeux.Jeu;
import com.projet.jeu.jeux.JeuMode;

public class ApplicationContext {
	
	private JeuMode jeuMode;
	private Jeu jeu;
	private int nombreEssais;
	private int nombreCases;
	private boolean modeDevelop;
	
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
	
	

	

}
