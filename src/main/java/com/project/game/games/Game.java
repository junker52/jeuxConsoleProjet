package com.project.game.games;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.project.game.context.ApplicationContext;

/**
 * Abstract base class to create Mastermind and Recherche games
 * 
 * @author junker52
 */
public abstract class Game {

	private ArrayList<Integer> secretComb = new ArrayList<Integer>();
	protected ArrayList<Integer> attemptComb = new ArrayList<Integer>();
	protected ArrayList<Integer> attemptCombPlayer = new ArrayList<Integer>();
	protected ArrayList<Integer> secretCombPlayer = new ArrayList<Integer>();
	protected ApplicationContext applicationContext;
	public static final Logger log = Logger.getLogger(Game.class);

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
	/**
	 * This method creates a random secret combination
	 * @return Created secret combination
	 */
	public ArrayList<Integer> setRandomSecretComb() {
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

	/**
	 * In case of developer mode, this mode prints the secret combination
	 * @param array
	 * 		Secret Combination to be printed
	 */
	public void showSolution(ArrayList<Integer> array) {
		// Gestion du mode developpeur
		if (applicationContext.isModeDevelop()) {
			log.info("Developer mode is On.");
			System.out.println("Secret Combination is: "+array.toString() + " \n");
		}
	}
	
	/**
	 * <p>This methods takes the attempt from user</p>
	 * @return Attempt combination 
	 */
	public ArrayList<Integer> setAttemptComb() {
		ArrayList<Integer> combinaison = new ArrayList<Integer>();
		System.out.print("Write a combination of " + applicationContext.getNumberOfBox() + " numbers: \t");
		try {
			String combInt = ApplicationContext.getReader().readLine();
			while(combInt.length() > applicationContext.getNumberOfBox()) {
				System.out.println("Max "+applicationContext.getNumberOfBox()+" numbers. Retry!");
				combInt = ApplicationContext.getReader().readLine();
			}
			for (int i = 0; i < applicationContext.getNumberOfBox(); i++) {
				int chifre = Integer.parseInt("" + combInt.charAt(i));
				combinaison.add(chifre);
			}
		} catch (Exception e) {
			System.out.println("Input Error. Retry by using only numbers.");
			combinaison = setAttemptComb();
		}

		return combinaison;

	}
	/**
	 * This method compares two combinations to check if they are the same
	 * @param combinasion
	 * 		Combination to be compared
	 * @param combSecrete
	 * 		Secret Combination
	 * @return true if it's the same. False if not
	 */
	public boolean isSecretCobination(ArrayList<Integer> combinasion, ArrayList<Integer> combSecrete) {
		boolean winner = false;
		if (combSecrete.equals(combinasion)) {
			winner = true;
		}
		return winner;
	}
	
	/**
	 * This method gets the secret combination from the user
	 * @return ArrayList of secret combination
	 */
	public ArrayList<Integer> inputSecretComb() {
		System.out.println("Write a secret combination of " + applicationContext.getNumberOfBox() + " numbers: ");
		ArrayList<Integer> combinaison = new ArrayList<Integer>();
		try {			
			String combString = ApplicationContext.getReader().readLine();
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
	
	/**
	 * <p>This method turns a combination string into an ArrayList of integers</p>
	 * @param input
	 * 		Numeric string to be converted
	 * @return List of Integers converted.
	 */
	public ArrayList<Integer> toArrayList(String input) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int integ_temp;
		for (int i = 0; i < input.length(); i++) {
			integ_temp = Integer.parseInt(input.charAt(i)+"");
			result.add(integ_temp);
		}
		return result;
	}
	
	/**
	 * <p>This method turns a combination ArrayList into a string</p>
	 * @param input
	 * 		Combination to be transformed
	 * @return Converted String
	 */
	public String toString(ArrayList<Integer> input) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < input.size(); i++) {
			result.append(Integer.toString(input.get(i)));
		}
		return input.toString();
	}

	/**
	 * This method executes the game in challenge mode
	 */
	abstract void executeGameChallenge();
	
	/**
	 * This method executes the game in defense mode
	 */
	abstract void executeGameDefense();
	
	/**
	 * This method executes the game in duel mode
	 */
	abstract void executeGameDuel();

	/**
	 * This method evaluates the player attempt
	 * @return evaluated combination
	 */
	abstract String evaluateCombinationPlayer();

	public ArrayList<Integer> getSecretComb() {
		return secretComb;
	}

	public void setSecretComb(ArrayList<Integer> secretComb) {
		this.secretComb = secretComb;
	}
	
	

}
