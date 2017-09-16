package com.projet.jeu.application;

import com.projet.jeu.menu.Menu;

public class Application {

	public static void main(String[] args) {
		Menu menu = new Menu();
		short rep = menu.debutMenu();
		short subrep = menu.submenu();

	}

}
