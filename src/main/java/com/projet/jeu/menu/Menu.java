package com.projet.jeu.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.projet.jeu.context.ApplicationContext;
import com.projet.jeu.jeux.JeuMode;
import com.projet.jeu.jeux.JeuType;

/*<p>
 * Classe en charge de la gestion des menus dans le jeu.
 *</p> 
 * @author junker52
 */
public class Menu {
	
	private short menu_option;
	private short submenu_option;
	private ApplicationContext applicationContext;
	
	//Constructor par defaut
	public Menu(ApplicationContext aplicationContext) {
		this.applicationContext = aplicationContext;
		this.menu_option = debutMenu();
		setApplicationJeuType();
		this.submenu_option = submenu();
		switch (submenu_option) {
		case 1:
			aplicationContext.setJeuMode(JeuMode.CHALLENGE);
			break;
		case 2:
			aplicationContext.setJeuMode(JeuMode.DEFENSEUR);
			break;
			
		case 3:
			aplicationContext.setJeuMode(JeuMode.DUEL);
			break;
		}
		
	}
	
	//Gestion des menus
	
	/*
	 * Menu de demarrage du jeu
	 * 
	 * @return le jeu choisi
	 */
	public short debutMenu() {
		short reponse;
		System.out.println("Bienvenu au JeuProjet");
		System.out.println("======================");
		System.out.println("Faites votre choix:");
		System.out.println("1 - Recherche +/-");
		System.out.println("2 - Mastermind");
		try {
			reponse = Short.valueOf(ApplicationContext.getLecteur().readLine());			
		} catch (Exception e) {
			//Erreur de format des nombres
			System.out.println("Erreur de saisie. Choisisez entre \" 1\" et \" 2\" svp:");
			reponse=this.debutMenu();
		}		
		if(reponse != 1 && reponse != 2) {
			//Si le choix est hors du rang 
			System.out.println("Erreur de saisie. Choisisez entre \" 1\" et \" 2\" svp:");
			reponse=this.debutMenu();
		}
		return reponse; 
	}
	
	/*
	 * Submenu apres le menu de demarrage permettant de choisir le mode
	 * 
	 * @return le mode de jeu choisi
	 */
	public short submenu() {
		short reponse;
		System.out.println("Choisissez le mode de jeu:");
		System.out.println("======================");
		System.out.println("1 - Mode Chanllenger");
		System.out.println("2 - Mode Defenseur +/-");
		System.out.println("3 - Mode Duel");
		
		try {
			reponse = Short.valueOf(ApplicationContext.getLecteur().readLine());			
		} catch (Exception e) {
			//Erreur de format des nombres
			System.out.println("Erreur de saisie. Choisisez entre \" 1\", \" 2\" et \" 3\" svp:");
			reponse=this.submenu();
		}		
		if(reponse != 1 && reponse != 2 && reponse != 3) {
			//Si le choix est hors du rang 
			System.out.println("Erreur de saisie. Choisisez entre \" 1\", \" 2\" et \" 3\"  svp:");
			reponse=this.submenu();
		}
		return reponse; 
	}
	
	//Getters et Setters

	public short getMenu_option() {
		return menu_option;
	}
	
	public void setMenu_option(short menu_option) {
		this.menu_option = menu_option;
	}

	public short getSubmenu_option() {
		return submenu_option;
	}

	public void setSubmenu_option(short submenu_option) {
		this.submenu_option = submenu_option;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void setApplicationJeuType() {
		if (this.menu_option == 1) {
			applicationContext.setJeuType(JeuType.RECHERCHE); 
		} else {
			applicationContext.setJeuType(JeuType.MASTEMIND); 
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
