package com.projet.jeu.application;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.projet.jeu.menu.Menu;

public class Application {

	public static final Logger log = Logger.getLogger(Application.class);

	public static void main(String[] args) {

		// Configuracion base del logj4 obtenida del archivo log4.properties
		//VER COMO CONFIGURAR LOG4J PARA QUE NO SE MUESTRE EN CONSOLA
		BasicConfigurator.configure();

		// Logeando mensajes
		log.info("Message de test type info");
		log.fatal("Coucou, epic fail");
		Menu menu = new Menu();
		short rep = menu.debutMenu();
		short subrep = menu.submenu();

	}

}
