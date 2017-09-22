package com.project.game.games;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.project.game.context.ApplicationContext;

import javafx.collections.transformation.SortedList;

public class GameMastermind extends Game {

	private String lastAttempt;
	private String secretComb;
	private String secretCombJoueur;
	private Map<String, Integer> help;
	private List<String> poolOptions = new ArrayList<String>();

	public GameMastermind(ApplicationContext applicationContext) {
		super(applicationContext);
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

	@Override
	void executeGameChallenge() {
		// TODO Auto-generated method stub

	}

	@Override
	void executeGameDefense() {
		System.out.println("Bienvenue au Mastermind || Mode Defenseur");
		this.poolOptions = GetAllPossibleSolutions();
		this.secretCombJoueur = SetSecretComb(poolOptions);
		if (this.lastAttempt == null) {
			this.lastAttempt = MoveComputer(poolOptions);
		}
		while(!this.lastAttempt.equals(secretCombJoueur))
		{		
			System.out.println(poolOptions.contains(secretCombJoueur));
			this.help = GetGuessFromUser();
			System.out.println(poolOptions.size());
			this.poolOptions = CleanPoolList(lastAttempt, help, poolOptions);
			System.out.println("After pooling "+poolOptions.size());
			this.lastAttempt = MoveComputer(poolOptions);			
		}
		if (this.lastAttempt.equals(secretCombJoueur)) {
			System.out.println("Computer has found your combination!!");
		} 
		
	}

	@Override
	void executeGameDuel() {
		// TODO Auto-generated method stub

	}

	@Override
	String evaluateCombination(ArrayList<Integer> SecretComb, ArrayList<Integer> AttemptComb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String evaluateCombinationPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	List<String> _PossibleTokens;
	String _LastMove;

	/*
	 * This method creates a list with all possible solutions
	 * 
	 * @return List of possible solutions
	 */
	public List<String> GetAllPossibleSolutions() {
		char[] _ValidDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		List<String> tokens = new ArrayList<String>();
		for (int i1 = 0; i1 < _ValidDigits.length; i1++) {
			for (int i2 = 0; i2 < _ValidDigits.length; i2++) {
				for (int i3 = 0; i3 < _ValidDigits.length; i3++) {
					for (int i4 = 0; i4 < _ValidDigits.length; i4++) {
						for (int i5 = 0; i5 < _ValidDigits.length; i5++) {
							if (i1 != i2 && i1 != i3 && i1 != i4 && i1 != i5 && i2 != i3 && i2 != i4 && i2 != i5
									&& i3 != i4 && i3 != i5 && i4 != i5) {
								tokens.add(("" + _ValidDigits[i1] + _ValidDigits[i2] + _ValidDigits[i3]
										+ _ValidDigits[i4] + _ValidDigits[i5]));
							}

						}

					}

				}
			}
		}

		return tokens;
	}

	/*
	 * This method is used to allow the user to write the guess for each computer
	 * move
	 * 
	 * @return Map collection with the well-placed characters number and
	 * wrong-placed number.
	 */
	public Map<String, Integer> GetGuessFromUser() {
		System.out.println("Well-placed?");
		Map<String, Integer> guess = new HashMap<String, Integer>();
		try {
			guess.put("well", Integer.valueOf(ApplicationContext.getReader().readLine()));
			System.out.println("bad-placed");
			guess.put("bad", Integer.valueOf(ApplicationContext.getReader().readLine()));
		} catch (IOException e) {
			System.out.println("IO error. Please retry only with numbers.");
			guess = GetGuessFromUser();
		}
		return guess;
	}

	/*
	 * This method is used by the user to set the secret combinaiton
	 * 
	 * @param poolList List with all possibles combinations
	 * 
	 * @return The validated secret combination
	 */
	public String SetSecretComb(List<String> poolList) {
		String secret = " ";
		System.out.println("Your secret combination? (5 non-repeated numbers)");
		try {
			String secret_temp = ApplicationContext.getReader().readLine();
			if (secret_temp.length() == 5 && poolList.contains(secret_temp)) {
				secret = secret_temp;
			} else {
				System.out.println("Invalid combination. Retry.");
				secret = SetSecretComb(poolList);
			}
		} catch (IOException e) {
			System.out.println("Invalid combination. Retry.");
			secret = SetSecretComb(poolList);
		}
		return secret;
	}

	/*
	 * Method for getting the first guess completly random
	 * 
	 * @param list List with all solutions
	 * 
	 * @return First computer attempt
	 */
	public String MoveComputer(List<String> list) {
		int randomOption = (int) (Math.random() * list.size());
		System.out.println("Combination : "+list.get(randomOption));
		return list.get(randomOption);
	}

	public Map<String, Integer> CompareToGetGuess(String toCompare, String compared) {
		char toCompare_char;
		int well = 0;
		int bad = 0;
		for (int i = 0; i < toCompare.length(); i++) {
			toCompare_char = toCompare.charAt(i);
			for (int j = 0; j < compared.length(); j++) {
				if (toCompare_char == compared.charAt(j)) {
					if (i == j) {
						// The same number in the same position: well-placed + 1
						well++;
					}
					// The number exists bad is not well-placed: bad-placed + 1
					bad++;
				}
			}
		}
		// New Hashmap to send the result
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		resultMap.put("well", well);
		resultMap.put("bad", bad);
		return resultMap;
	}

	// EN COURS
	public List<String> CleanPoolList(String attempt, Map<String, Integer> help, List<String> poolList) {
		Iterator<String> iterat = poolList.iterator();
		int well = help.get("well");
		int bad = help.get("bad");
		Map<String, Integer> map_temp;
		while (iterat.hasNext()) {
			String toCompare = iterat.next();
			map_temp = CompareToGetGuess(toCompare, attempt);
			if (  well != map_temp.get("well") && bad != map_temp.get("bad")) {
				iterat.remove();
			}

		}
		return poolList;

	}

}
