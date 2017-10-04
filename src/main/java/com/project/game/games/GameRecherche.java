package com.project.game.games;

import java.io.IOException;
import java.util.ArrayList;

import com.project.game.context.ApplicationContext;

/*
 * Classe permettant de gerer le jeu recherche +/-
 * 
 * @author junker52
 */

public class GameRecherche extends Game {

	private String evaluationPlayer=" ";
	private String evaluationComput=" ";
	protected ArrayList<Integer> secretCombPlayer = new ArrayList<Integer>();
	protected ArrayList<Integer> attemptCombPlayer = new ArrayList<Integer>();

	/*
	 * <p> Constructor pour lancer le jeu directement </p>
	 * 
	 * @param a
	 * 		L'objet ApplicationContext de l'application.
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

	/*
	 * <p> Methode pour lancer le jeu recherche en mode Challenger </p>
	 * 
	 */
	@Override
	void executeGameChallenge() {
		System.out.println("Welcome to Recherche +//- || Mode Challenger");
		super.secretComb = super.setSecretComb();
		super.showSolution(super.secretComb);
		// D'abord un loop pour les essais
		for (int i = 0; i < applicationContext.getNumberOfAttemps(); i++) {
			super.attemptComb = setAttemptComb();
			if (!isSecretCobination(attemptComb, secretComb)) {
				String avancement = evaluateCombination(secretComb, attemptComb);
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
	
	/*
	 * <p> Methode pour lancer le jeu recherche en mode Defenseur </p>
	 * 
	 */
	@Override
	void executeGameDefense() {
		super.attemptComb = null;
		System.out.println("Welcome to Recherche +//-  || Mode Defense");
		super.secretComb = super.inputSecretComb();
		super.showSolution(super.secretComb);
		for (int i = 0; i < applicationContext.getNumberOfAttemps(); i++) {
			if (attemptComb == null) {
				attemptComb = super.setSecretComb();
			}
			System.out.println("The computer attempt is : " + attemptComb);
			if (super.isSecretCobination(attemptComb, secretComb)) {
				System.out.println("Bravo! You have found the secret combination in " + (i + 1) + " attempts");
				break;
			}
			this.evaluationPlayer = evaluateCombinationPlayer();
			System.out.println("Help: " + this.evaluationPlayer);
			super.attemptComb = this.findCombination(evaluationPlayer);
			if (i >= applicationContext.getNumberOfAttemps() - 1) {
				System.out.println("MEC! There are not more attemps! GAME OVER!");
			}
		}

	}

	/*
	 * <p> Methode pour lancer le jeu recherche en mode Duel </p>
	 * 
	 */
	@Override
	void executeGameDuel() {
		System.out.println("Welcome to Recherche +//-  || Mode Duel");
		//Saisir les combinaisons secretes
		super.secretComb = super.setSecretComb();
		this.secretCombPlayer = super.inputSecretComb();
		//Printer les combis
		if (applicationContext.isModeDevelop()) {
			System.out.println("Computer secret combination: ");
			super.showSolution(secretComb);
			System.out.println("Player secret combination:");
			super.showSolution(secretCombPlayer);
		}
		attemptComb = null; attemptCombPlayer = null;
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
				attemptComb = super.setSecretComb();
			}
			System.out.println("Computer attempt: " + attemptComb);
			if (super.isSecretCobination(attemptComb,secretCombPlayer)) {
				System.out.println("Computer has found your secret combination in " + i_comput + " attempts");
				break;
			}
			this.evaluationComput = evaluateCombinationPlayer();
			System.out.println("Help: " + this.evaluationComput);
			super.attemptComb = this.findCombination(evaluationComput);			
			
			//Ensuite, c'est le joueur qui devine la combinaison du pc
			System.out.println("Player goes against the computer combination: ");	
			System.out.println("Player attempt is: " + attemptCombPlayer+"  help: "+evaluationPlayer);
			attemptCombPlayer = super.setAttemptComb();			
			if (super.isSecretCobination(attemptCombPlayer, secretComb)) {
				System.out.println("Bravo! You have found the secret combination in " + i_joueur + " attempts");
				break;
			}
			this.evaluationPlayer = evaluateCombination(secretComb, attemptCombPlayer);
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


	/*
	 * <p> Methode en charge de genere un String piste en faisant la comparaison entre la combinaison saissie 
	 *  et la combinaison secrete </p>
	 *  
	 *  @return La piste en string
	 *  
	 *  @param secretComb
	 *  	ArrayList contenent la combinaison secrete du jeu
	 *  @param attemptComb
	 *  	ArrayList contenent la combinaison saisie
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
	
	/*
	 * <p>Methode de input lorsque le joueur doit lui saisir la piste (il agit comme defenseur) </p>
	 * @return La piste saisie 
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
			respo = evaluateCombinationPlayer();
		}
		return respo;
	}

	/*
	 * <p>En base à une piste, cette methode s'occupe de transforme la piste pour obtenir une nouvelle combinaison d'essai </p>
	 * 
	 * @return Un nouveau essai
	 * @param evaluationPlayer 
	 * 		La piste obtenu lors de l'essai precedent
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
