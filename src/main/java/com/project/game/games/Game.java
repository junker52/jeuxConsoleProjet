package com.project.game.games;

import java.util.ArrayList;

import com.project.game.context.ApplicationContext;

/*
 * Classe abstraite de base pour creer les jeux Recherche et Mastermind
 * 
 * @author junker52
 */
public abstract class Game {

	protected ArrayList<Integer> secretComb = new ArrayList<Integer>();
	protected ArrayList<Integer> attemptComb = new ArrayList<Integer>();
	protected ApplicationContext applicationContext;

	public Game(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;

	}

	// Getters et Setters

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	// Methodes
	public ArrayList<Integer> setSecretComb() {
		ArrayList<Integer> combinaison = new ArrayList<Integer>();
		for (int i = 0; i < applicationContext.getNumberOfBox(); i++) {
			int numRandom = (int) (Math.random() * 10);
			//Problems with int "0" in the first place
			while(i == 0 && numRandom == 0)
			{
				numRandom = (int) (Math.random() * 10);
			}	
			combinaison.add(numRandom);
		}
		return combinaison;
	}

	public void showSolution(ArrayList<Integer> array) {
		// Gestion du mode developpeur
		if (applicationContext.isModeDevelop()) {
			System.out.println(array.toString() + " \n");
		}
	}

	public ArrayList<Integer> setAttemptComb() {
		ArrayList<Integer> combinaison = new ArrayList<Integer>();
		System.out.print("Write a combination of " + applicationContext.getNumberOfBox() + " numbers: \t");
		try {
			int combInt = Integer.valueOf(ApplicationContext.getReader().readLine());
			String combString = Integer.toString(combInt);
			for (int i = 0; i < applicationContext.getNumberOfBox(); i++) {
				int chifre = Integer.parseInt("" + combString.charAt(i));
				combinaison.add(chifre);
			}
		} catch (Exception e) {
			System.out.println("Input Error. Retry by using only numbers.");
			combinaison = setAttemptComb();
		}

		return combinaison;

	}

	public boolean isSecretCobination(ArrayList<Integer> combinasion, ArrayList<Integer> combSecrete) {
		boolean winner = false;
		if (combSecrete.equals(combinasion)) {
			winner = true;
		}
		return winner;
	}

	public ArrayList<Integer> inputSecretComb() {
		System.out.println("Write a secret combination of " + applicationContext.getNumberOfBox() + " numbers: ");
		ArrayList<Integer> combinaison = new ArrayList<Integer>();
		try {
			int combInt = Integer.valueOf(ApplicationContext.getReader().readLine());
			String combString = Integer.toString(combInt);
			for (int i = 0; i < applicationContext.getNumberOfBox(); i++) {
				int chifre = Integer.parseInt("" + combString.charAt(i));
				combinaison.add(chifre);
			}
		} catch (Exception e) {
			System.out.println("Input Error. Retry by using only numbers.");
			combinaison = inputSecretComb();
		}
		return combinaison;
	}

	abstract void executeGameChallenge();

	abstract void executeGameDefense();

	abstract void executeGameDuel();

	abstract String evaluateCombination(ArrayList<Integer> SecretComb, ArrayList<Integer> AttemptComb);

	abstract String evaluateCombinationPlayer();

}