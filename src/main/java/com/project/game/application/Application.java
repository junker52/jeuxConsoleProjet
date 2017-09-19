package com.project.game.application;

import org.apache.log4j.Logger;

import com.project.game.context.ApplicationContext;
import com.project.game.games.GameRecherche;
import com.project.game.games.GameType;
import com.project.game.menu.Menu;

public class Application {

	public static final Logger log = Logger.getLogger(Application.class);

	public static void main(String[] args) {

		//Test
		ApplicationContext applicationContext = new ApplicationContext();
		do {
		applicationContext.setGame(null);
		Menu menu = new Menu(applicationContext);
		applicationContext = menu.getApplicationContext();
		if (applicationContext.getGameType() == GameType.MASTEMIND) {
			System.out.println("Lanzo Mastermind");
			System.out.println(applicationContext.getGameMode());
		} else if(applicationContext.getGameType() == GameType.RECHERCHE) {
			applicationContext.setGame(new GameRecherche(applicationContext));
		}
		applicationContext.setExitControl(applicationContext.exitApplication());
		} while(applicationContext.getExitControl() != 'O' && applicationContext.getExitControl() != 'o');

	}
	
}
