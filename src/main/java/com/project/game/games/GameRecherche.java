package com.project.game.games;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.project.game.context.ApplicationContext;

/**
 * <p>This class allows to execute the Recherche Game</p>
 * 
 * @author junker52
 */

public class GameRecherche extends Game {

	private String evaluationPlayer=" ";
	private String evaluationComput=" ";
	public static final Logger log = Logger.getLogger(GameRecherche.class);

	/**
	 * <p> Constructor for Recherche Game </p>
	 * 
	 * @param a
	 * 		The ApplicationContext of the game
	 */
	public GameRecherche(ApplicationContext a) {
		super(a);
		switch (applicationContext.getGameMode()) {
		case CHALLENGE:
			executeGameChallenge();
			break;
		case DEFENSE:
			executeGameDefense();
			break;
		case DUEL:
			executeGameDuel();
			break;
		}
	}

	/**
	 * <p> This method executes the game in mode challenge  </p>
	 * 
	 */
	@Override
	void executeGameChallenge() {
		System.out.println("Welcome to Recherche +//- || Mode Challenger");
		super.setSecretComb(super.setRandomSecretComb());
		super.showSolution(super.getSecretComb());
		// D'abord un loop pour les essais
		for (int i = 0; i < applicationContext.getNumberOfAttemps(); i++) {
			super.attemptComb = setAttemptComb();
			if (!isSecretCobination(attemptComb, super.getSecretComb())) {
				log.info("Evaluating combination...");
				String avancement = evaluateCombination(super.getSecretComb(), attemptComb);
				System.out.println("Help: " + avancement);
			} else {
				System.out.println("Bravo! You have found the secret combination in " + (i + 1) + " attempts");
				break;
			}
			if (i >= applicationContext.getNumberOfAttemps() - 1) {
				System.out.println("MEC! There are not more attemps! GAME OVER!");
			}
		}
		
	}
	
	/**
	 * <p> This method executes the game in mode defense </p>
	 * 
	 */
	@Override
	void executeGameDefense() {
		super.attemptComb = null;
		System.out.println("Welcome to Recherche +//-  || Mode Defense");
		super.setSecretComb(super.inputSecretComb());
		super.showSolution(super.getSecretComb());
		for (int i = 0; i < applicationContext.getNumberOfAttemps(); i++) {
			if (attemptComb == null) {
				attemptComb = super.setRandomSecretComb();
			}
			System.out.println("The computer attempt is : " + attemptComb);
			if (super.isSecretCobination(attemptComb, super.getSecretComb())) {
				System.out.println("Bravo! You have found the secret combination in " + (i + 1) + " attempts");
				break;
			}
			this.evaluationPlayer = evaluateCombinationPlayer();
			System.out.println("Help: " + this.evaluationPlayer);
			log.info("Finding player combination...");
			super.attemptComb = this.findCombination(evaluationPlayer);
			if (i >= applicationContext.getNumberOfAttemps() - 1) {
				System.out.println("MEC! There are not more attemps! GAME OVER!");
			}
		}

	}

	/**
	 * <p> This method executes the game in mode duel </p>
	 * 
	 */
	@Override
	void executeGameDuel() {
		System.out.println("Welcome to Recherche +//-  || Mode Duel");
		//Saisir les combinaisons secretes
		super.setSecretComb(super.setRandomSecretComb());
		super.secretCombPlayer = super.inputSecretComb();
		//Printer les combis
		if (applicationContext.isModeDevelop()) {
			System.out.println("Computer secret combination: ");
			super.showSolution(super.getSecretComb());
			System.out.println("Player secret combination:");
			super.showSolution(secretCombPlayer);
		}
		attemptComb = null; super.attemptCombPlayer = null;
		//init des compteus pour les deux jueurs
		int i_comput = 0;
		int i_joueur = 0;
		do {
			//Augmentons les compteurs
			i_comput++;
			i_joueur++;
			//D'abord l'ordinateur commence avec la combinaison secrete du joueur
			System.out.println("Computer goes against the player combination");
			if (attemptComb == null) {
				attemptComb = super.setRandomSecretComb();
			}
			System.out.println("Computer attempt: " + attemptComb);
			if (super.isSecretCobination(attemptComb, super.secretCombPlayer)) {
				System.out.println("Computer has found your secret combination in " + i_comput + " attempts");
				break;
			}
			this.evaluationComput = evaluateCombinationPlayer();
			System.out.println("Help: " + this.evaluationComput);
			super.attemptComb = this.findCombination(evaluationComput);			
			
			//Ensuite, c'est le joueur qui devine la combinaison du pc
			System.out.println("Player goes against the computer combination: ");	
			System.out.println("Player attempt is: " + super.attemptCombPlayer+"  help: "+evaluationPlayer);
			super.attemptCombPlayer = super.setAttemptComb();			
			if (super.isSecretCobination(super.attemptCombPlayer, getSecretComb())) {
				System.out.println("Bravo! You have found the secret combination in " + i_joueur + " attempts");
				break;
			}
			this.evaluationPlayer = evaluateCombination(super.getSecretComb(), super.attemptCombPlayer);
			System.out.println("Help : " + this.evaluationPlayer);		
			
			//Verif des essais
			if (i_comput >= applicationContext.getNumberOfAttemps() - 1) {
				System.out.println("BRAVO! Computer has run out its attempts!");
			} else if (i_joueur >= applicationContext.getNumberOfAttemps() -1) {
				System.out.println("MEC! There are not more attemps! GAME OVER!");
			}			
			
		} while (i_comput < applicationContext.getNumberOfAttemps() ||
				i_joueur < applicationContext.getNumberOfAttemps());
		

	}


	/**
	 * <p> This method evaluates two combinations getting a result used by the player to guess the secret combination </p>
	 *  
	 *  @return Result Help String
	 *  
	 *  @param secretComb
	 *  	ArrayList of secret combination
	 *  @param attemptComb
	 *  	ArrayList of attempt combination
	 * 
	 */
	
	public String evaluateCombination(ArrayList<Integer> secretComb, ArrayList<Integer> attemptComb) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < attemptComb.size(); i++) {
			if (secretComb.get(i) > attemptComb.get(i)) {
				result.append("> ");
			} else if (secretComb.get(i) == attemptComb.get(i)) {
				result.append("= ");
			} else if (secretComb.get(i) < attemptComb.get(i)) {
				result.append("< ");
			}
		}
		return result.toString();
	}
	
	/**
	 * <p>This method allows the user to set the help used by the computer tu guess the secret combination </p>
	 * @return Help String (>,<,=) 
	 * @see com.project.game.games.Game#evaluateCombinaisonJoueur()
	 */
	@Override
	String evaluateCombinationPlayer() {
		System.out.println("Evaluate combination (>,<,=): ");
		String respo = " ";
		try {
			boolean verif_string = true;
			respo = (String) ApplicationContext.getReader().readLine();
			StringBuffer sb = new StringBuffer(respo);
			for (int i = 0; i < applicationContext.getNumberOfBox(); i++) {
				if (sb.charAt(i) != '>' && sb.charAt(i) != '<' && sb.charAt(i) != '=') {
					verif_string = false;
				}
			}
			// Si la verif ne se passe pas bien, on relance la methode.
			if (verif_string == false) {
				System.out.println("Invalid evaluation. Retry!");
				respo = evaluateCombinationPlayer();
			}
		} catch (IOException e) {
			System.out.println("Invalid evaluation. Retry!");
			log.error("Error in evaluateCombinationPlayer");
			respo = evaluateCombinationPlayer();
		}
		return respo;
	}

	/**
	 * <p>Using help string, this methods calculates another combination</p>
	 * 
	 * @return New ArrayList attemp
	 * @param evaluationPlayer 
	 * 		Help String
	 */
	public ArrayList<Integer> findCombination(String evaluationPlayer) {
		ArrayList<Integer> combi = new ArrayList<Integer>();
		for (int i = 0; i < applicationContext.getNumberOfBox(); i++) {
			// À ce moment, on n'a pas encore récrit la donnée attemptComb
			if (evaluationPlayer.charAt(i) == '>') {
				combi.add(attemptComb.get(i) + 1);
			} else if (evaluationPlayer.charAt(i) == '<') {
				combi.add(attemptComb.get(i) - 1);
			} else if (evaluationPlayer.charAt(i) == '=') {
				combi.add(attemptComb.get(i));
			}
		}
		return combi;
	}

}
