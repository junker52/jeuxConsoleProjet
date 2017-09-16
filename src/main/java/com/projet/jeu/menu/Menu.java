package com.projet.jeu.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*<p>
 * Classe en charge de la gestion des menus dans le jeu.
 *</p> 
 * @author junker52
 */
public class Menu {
	
	private short menu_option;
	private short submenu_option;
	private BufferedReader lecteur = new BufferedReader(new InputStreamReader(System.in)); 
	
	//Constructor par defaut
	public Menu() {}
	
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
			reponse = Short.valueOf(lecteur.readLine());			
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
		this.setMenu_option(reponse);
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
			reponse = Short.valueOf(lecteur.readLine());			
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
		this.setSubmenu_option(reponse);
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

	public BufferedReader getLecteur() {
		return lecteur;
	}

	public void setLecteur(BufferedReader lecteur) {
		this.lecteur = lecteur;
	}
	
	
	
	
	
	
	
	
	
	

}
