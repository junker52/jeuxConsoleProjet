package com.project.game.games;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.project.game.application.Application;
import com.project.game.context.ApplicationContext;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import javafx.collections.transformation.SortedList;

/**
 * <p>This class execute a Mastermind game instance</p>
 * @author junker52
 *
 */
public class GameMastermind extends Game {
	
	private Map<String, Integer> help;
	private List<String> poolOptions = new ArrayList<String>();
	public static final Logger log = Logger.getLogger(GameMastermind.class);
	
	/**
	 * <p>Constructor to execute the game in one of its different modes</p>
	 * @param applicationContext 
	 * 		Used applicationContext 
	 */
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

	@SuppressWarnings("unchecked")
	@Override
	void executeGameChallenge() {
		System.out.println("Bienvenue au Mastermind || Mode Challenge");
		super.attemptCombPlayer = null;
		super.setSecretComb(super.setRandomSecretComb());
		super.showSolution(super.getSecretComb());
		for (int i = 0; i < applicationContext.getNumberOfAttemps(); i++) {
			super.attemptCombPlayer = setAttemptComb();
			if (super.attemptCombPlayer.equals(super.getSecretComb())) {				
				System.out.println("BRAVO!! You won in "+(i+1)+" attempts!!");				
				break;
			}
			log.info("Evaluating combination...");
			System.out.println(this.evaluateCombinationPlayer());
			
		}		
	}

	@Override
	void executeGameDefense() {
		System.out.println("Bienvenue au Mastermind || Mode Defenseur");
		super.attemptComb = null;
		this.poolOptions = GetAllPossibleSolutions();
		super.secretCombPlayer = SetSecretComb(poolOptions);
		int attemp = 1;
		if (super.attemptComb == null) {
			super.attemptComb = MoveComputer(poolOptions);
		}
		while(!super.attemptComb.equals(secretCombPlayer))
		{	
			if (attemp > applicationContext.getNumberOfAttemps() ) {
				System.out.println("Game Over. No more attempts. PC loses!");
				break;
			}
			this.help = GetGuessFromUser();			
			this.poolOptions = CleanPoolList(attemptComb, help, poolOptions);
			log.info("Reducing options to "+this.poolOptions.size());
			super.attemptComb = MoveComputer(poolOptions);	
			attemp++;
			if (super.attemptComb.equals(secretCombPlayer)) {
				System.out.println("Computer has found your combination in "+attemp+" attempts!!");
			} 
		}		
	}

	@Override
	void executeGameDuel() {
		System.out.println("Bienvenue au Mastermind || Mode Duel");
		super.attemptCombPlayer = null;
		super.attemptComb = null;
		//Player sets secret combination for PC
		this.poolOptions = GetAllPossibleSolutions();
		super.secretCombPlayer = SetSecretComb(poolOptions);
		//PC chooses the secret combination for player
		super.setSecretComb(super.setRandomSecretComb());
		//Showing secret combinations if it's necessary
		if (applicationContext.isModeDevelop()) {
			System.out.println("Player:");
			super.showSolution(super.secretCombPlayer);
			System.out.println("PC:");
			super.showSolution(super.getSecretComb());
		}
		
		//Starting counters...
		int count_player = 1; int count_pc = 1;
		while (count_pc < applicationContext.getNumberOfAttemps() 
				&& count_player < applicationContext.getNumberOfAttemps()) {
			//First the player...
			if(attemptCombPlayer != null) {
				System.out.println("Player goes with its last try: "+attemptCombPlayer);
			}
			super.attemptCombPlayer = setAttemptComb();
			if (super.attemptCombPlayer.equals(super.getSecretComb())) {				
				System.out.println("BRAVO!! You won in "+count_player+" attempts!!");
				break;
			}
			System.out.println(this.evaluateCombinationPlayer());
			count_player++;
			
			//Then PC...
			if (super.attemptComb != null) {
				System.out.println("PC goes to defend its secret combination: "+super.secretCombPlayer);
			}			
			super.attemptComb = MoveComputer(poolOptions);
			if (super.attemptComb.equals(super.secretCombPlayer)) {
				System.out.println("Game Over! PC found your combination in "+count_pc+" attempts!");
				break;
			}
			this.help = GetGuessFromUser();
			log.info("Reducing options to "+this.poolOptions.size());
			this.poolOptions = CleanPoolList(attemptComb, help, poolOptions);
			count_pc++;
		}
		//Messages when no more attempts
		if (count_pc >= applicationContext.getNumberOfAttemps()) {
			System.out.println("No more attemps for PC! Player wins!!");
		} else if (count_player >= applicationContext.getNumberOfAttemps()) {
			System.out.println("No more attempts for Player! PC wins!");
		}
		

	}

	@Override
	String evaluateCombinationPlayer() {
		int well = 0; int bad = 0; int comparedNumber;
		ArrayList attempt_temp = (ArrayList) super.attemptCombPlayer.clone();
		ArrayList secret_temp = (ArrayList) super.getSecretComb().clone();
		//first well
		log.info("Init: Attempt: "+attempt_temp);
		for (int i = 0; i < attempt_temp.size(); i++) {
			if (attempt_temp.get(i) == secret_temp.get(i)) {
				attempt_temp.set(i, "WA");
				secret_temp.set(i, "WS");
				well++;
			}
		}
		
		//Then bad
		for (int i = 0; i < attempt_temp.size(); i++) {
			for (int j = 0; j < secret_temp.size(); j++) {
				if (attempt_temp.get(i) instanceof Integer && secret_temp.get(j) instanceof Integer) {
					if (attempt_temp.get(i) == secret_temp.get(j) && i != j && attempt_temp.lastIndexOf(attempt_temp.get(i)) == i) {
						bad++;
					//Correction for doubled bad-placed numbers counting
					} else if (attempt_temp.get(i) == secret_temp.get(j) && i != j && attempt_temp.lastIndexOf(attempt_temp.get(i)) > i) {
						Integer compareNum = (Integer) attempt_temp.get(i); 
						Integer compareWithNum;
						for (int k = 0; k < attempt_temp.size(); k++) {
							if (attempt_temp.get(k) instanceof Integer) {
								compareWithNum = (Integer) attempt_temp.get(k);
								if (compareNum.intValue() == compareWithNum.intValue()) {
									attempt_temp.set(k, "DB");
									bad++;
								}
							}
						}
						
					}
				}
			}
		}
		return "Well-placed: "+well+" || Bad-placed: "+bad;
	}

	/**
	 * This method creates a list with all possible solutions
	 * 
	 * @return List of possible solutions
	 */
	public List<String> GetAllPossibleSolutions() {
		
		ArrayList<String> listString = new ArrayList<String>();
		String num_add;
		for (int i = 0; i < Math.pow(10, applicationContext.getNumberOfBox()); i++) {
			num_add = String.format("%0"+applicationContext.getNumberOfBox()+"d", i);
			listString.add(num_add);
		}
		
		return listString;
	}

	/**
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
		} catch (Exception e) {
			System.out.println("IO error. Please retry only with numbers.");
			log.error("Error in method GetGuessFromUser");
			guess = GetGuessFromUser();
		}
		return guess;
	}

	/**
	 * This method is used by the user to set the secret combinaiton
	 * 
	 * @param poolList List with all possibles combinations
	 * 
	 * @return The validated secret combination
	 */
	public ArrayList<Integer> SetSecretComb(List<String> poolList) {		
		ArrayList<Integer> result = new ArrayList<Integer>();
		System.out.println("Your secret combination? ("+ applicationContext.getNumberOfBox()+" numbers)");
		try {
			String secret_temp = ApplicationContext.getReader().readLine();
			if (secret_temp.length() == applicationContext.getNumberOfBox() && poolList.contains(secret_temp)) {
				for (int i = 0; i < secret_temp.length(); i++) {
					char integ = secret_temp.charAt(i);
					result.add(Integer.parseInt(integ+""));
				}
			} else {
				System.out.println("Invalid combination. Retry.");
				result = SetSecretComb(poolList);
			}
		} catch (IOException e) {
			System.out.println("Invalid combination. Retry.");
			log.error("Error in SetSecretComb");
			result = SetSecretComb(poolList);
		}
		return result;
	}

	/**
	 * Method for getting the first guess completly random
	 * 
	 * @param list List with all solutions
	 * 
	 * @return First computer attempt
	 */
	public ArrayList<Integer> MoveComputer(List<String> list) {
		int randomOption = (int) (Math.random() * list.size());
		System.out.println("Combination : "+list.get(randomOption));
		String result_str = list.get(randomOption);
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < result_str.length(); i++) {
			char integ = result_str.charAt(i);
			result.add(Integer.parseInt(integ+""));
		}
		return result;
	}

	/**
	 * This methods compares two combination to get a help for solve the game
	 * @param toCompare
	 * 		Combination of the pool to be compared
	 * @param compared
	 * 		Attempt combination
	 * @return Map with well and bad-placed numbers. "Well". "Bad"
	 */
	public Map<String, Integer> CompareToGetGuess(ArrayList<Integer> toCompare, ArrayList<Integer> compared) {
		int toCompare_integ;
		int well = 0;
		int bad = 0;
		for (int i = 0; i < toCompare.size(); i++) {
			toCompare_integ = toCompare.get(i);
			for (int j = 0; j < compared.size(); j++) {
				if (toCompare_integ == compared.get(j)) {
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

	/**
	 * This method cleans the pool to reduce the possible solution list
	 * @param attempt
	 * 		Last attempt
	 * @param help
	 * 		Map with well and bad-placed numbers
	 * @param poolList
	 * 		List with Possible Combinations
	 * @return Reduced List with Possible Combinations
	 */
	public List<String> CleanPoolList(ArrayList<Integer> attempt, Map<String, Integer> help, List<String> poolList) {
		Iterator<String> iterat = poolList.iterator();
		int well = help.get("well");
		int bad = help.get("bad");
		Map<String, Integer> map_temp;
		ArrayList<Integer> combi = new ArrayList<Integer>();
		while (iterat.hasNext()) {
			String toCompare = iterat.next();
			combi = toArrayList(toCompare);
			map_temp = CompareToGetGuess(combi, attempt);
			if (  well != map_temp.get("well") && bad != map_temp.get("bad")) {
				iterat.remove();
			}

		}
		return poolList;

	}


}
