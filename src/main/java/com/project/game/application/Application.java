package com.project.game.application;

import org.apache.log4j.Logger;

import com.project.game.context.ApplicationContext;
import com.project.game.games.Game;
import com.project.game.games.GameMastermind;
import com.project.game.games.GameRecherche;
import com.project.game.games.GameType;
import com.project.game.menu.Menu;

public class Application {

	/**
	 * Variable to generate the logs
	 */
	public static final Logger log = Logger.getLogger(Application.class);

	public static void main(String[] args) {

		//Starting point of the game! Enjoy!
		ApplicationContext applicationContext = new ApplicationContext();
		//Checking if there are command-line parameters
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("true")) {
				log.info("Developer mode activated by command line parameter");
				applicationContext.setModeDevelop(true);
			} else if (args[0].equalsIgnoreCase("false")) {
				log.info("Developer mode disabled by command line parameter");
				applicationContext.setModeDevelop(false);
			}
		}
		do {
		Menu menu = new Menu(applicationContext);
		applicationContext = menu.getApplicationContext();
		log.info("Starting game: "+applicationContext.getGameType()+" in mode : "+applicationContext.getGameMode());
		if (applicationContext.getGameType() == GameType.MASTEMIND) {
			new GameMastermind(applicationContext);
		} else if(applicationContext.getGameType() == GameType.RECHERCHE) {
			new GameRecherche(applicationContext);
		}
		log.info("Game's end!");
		applicationContext.setExitControl(applicationContext.exitApplication());
		} while(applicationContext.getExitControl() != 'O' && applicationContext.getExitControl() != 'o');

	}
	
}
