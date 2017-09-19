package com.project.game.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.project.game.context.ApplicationContext;
import com.project.game.games.GameMode;
import com.project.game.games.GameType;

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
			aplicationContext.setGameMode(GameMode.CHALLENGE);
			break;
		case 2:
			aplicationContext.setGameMode(GameMode.DEFENSE);
			break;
			
		case 3:
			aplicationContext.setGameMode(GameMode.DUEL);
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
		System.out.println("Welcome to GameProject");
		System.out.println("======================");
		System.out.println("Choose an option, please:");
		System.out.println("1 - Recherche +/-");
		System.out.println("2 - Mastermind");
		try {
			reponse = Short.valueOf(ApplicationContext.getReader().readLine());			
		} catch (Exception e) {
			//Erreur de format des nombres
			System.out.println("Input error. Choose between \" 1\" and \" 2\" please:");
			reponse=this.debutMenu();
		}		
		if(reponse != 1 && reponse != 2) {
			//Si le choix est hors du rang 
			System.out.println("Input error. Choose between \" 1\" and \" 2\" please:");
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
		System.out.println("Choose the game mode, please: ");
		System.out.println("======================");
		System.out.println("1 - Mode Chanllenger");
		System.out.println("2 - Mode Defenseur +/-");
		System.out.println("3 - Mode Duel");
		
		try {
			reponse = Short.valueOf(ApplicationContext.getReader().readLine());			
		} catch (Exception e) {
			//Erreur de format des nombres
			System.out.println("Input error. Choose between \" 1\", \" 2\" and \" 3\" please:");
			reponse=this.submenu();
		}		
		if(reponse != 1 && reponse != 2 && reponse != 3) {
			//Si le choix est hors du rang 
			System.out.println("Input error. Choose between \" 1\", \" 2\" and \" 3\"  please:");
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
			applicationContext.setGameType(GameType.RECHERCHE); 
		} else {
			applicationContext.setGameType(GameType.MASTEMIND); 
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
