package com.projet.jeu.application;

import org.apache.log4j.Logger;

import com.projet.jeu.context.ApplicationContext;
import com.projet.jeu.jeux.JeuRecherche;
import com.projet.jeu.jeux.JeuType;
import com.projet.jeu.menu.Menu;

public class Application {

	public static final Logger log = Logger.getLogger(Application.class);

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ApplicationContext();
		do {
		Menu menu = new Menu(applicationContext);
		applicationContext = menu.getApplicationContext();
		if (applicationContext.getJeuType() == JeuType.MASTEMIND) {
			System.out.println("Lanzo Mastermind");
			System.out.println(applicationContext.getJeuMode());
		} else if(applicationContext.getJeuType() == JeuType.RECHERCHE) {
			System.out.println("Lanzo Recherce");
			System.out.println(applicationContext.getJeuMode());
			applicationContext.setJeu(new JeuRecherche(applicationContext));
		}
		} while(applicationContext.gererSortie() != 'O' && applicationContext.gererSortie() != 'o');

	}

}
