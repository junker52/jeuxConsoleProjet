package com.projet.jeu.jeux;

import java.util.ArrayList;

import com.projet.jeu.context.ApplicationContext;

/*
 * Classe abstraite de base pour creer les jeux Recherche et Mastermind
 * 
 * @author junker52
 */
public abstract class Jeu {

	protected ArrayList<Integer> combSecrete = new ArrayList<Integer>();
	protected ArrayList<Integer> combEssai = new ArrayList<Integer>();
	protected ApplicationContext applicationContext;

	public Jeu(ApplicationContext applicationContext) {
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
	public ArrayList<Integer> setCombSecrete() {
		ArrayList<Integer> combinaison = new ArrayList<Integer>();
		for (int i = 0; i < applicationContext.getNombreCases(); i++) {
			combinaison.add((int) (Math.random() * 10));
		}
		return combinaison;
	}

	public void afficheSolution(ArrayList<Integer> array) {
		// Gestion du mode developpeur
		if (applicationContext.isModeDevelop()) {
			System.out.println(array.toString() + " \n");
		}
	}

	public ArrayList<Integer> setCombEssai() {
		ArrayList<Integer> combinaison = new ArrayList<Integer>();
		System.out.print("Saisissez une combinaison de " + applicationContext.getNombreCases() + " chifres: \t");
		try {
			int combInt = Integer.valueOf(ApplicationContext.getLecteur().readLine());
			String combString = Integer.toString(combInt);
			for (int i = 0; i < applicationContext.getNombreCases(); i++) {
				int chifre = Integer.parseInt("" + combString.charAt(i));
				combinaison.add(chifre);
			}
		} catch (Exception e) {
			System.out.println("Erreur de saissie. Reessayez seulement avec des chifres.");
			combinaison = setCombEssai();
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

	public ArrayList<Integer> saissieCombSecrete() {
		System.out.println("Saisissez une combinaison secrete de  " + applicationContext.getNombreCases() + ": ");
		ArrayList<Integer> combinaison = new ArrayList<Integer>();
		try {
			int combInt = Integer.valueOf(ApplicationContext.getLecteur().readLine());
			String combString = Integer.toString(combInt);
			for (int i = 0; i < applicationContext.getNombreCases(); i++) {
				int chifre = Integer.parseInt("" + combString.charAt(i));
				combinaison.add(chifre);
			}
		} catch (Exception e) {
			System.out.println("Erreur de saissie. Reessayez seulement avec des chifres.");
			combinaison = saissieCombSecrete();
		}
		return combinaison;
	}

	abstract void lancerJeuChallenge();

	abstract void lancerJeuDefenseur();

	abstract void lancerJeuDuel();

	abstract String evaluateCombinaison(ArrayList<Integer> combSecrete, ArrayList<Integer> combEssai);

	abstract String evaluateCombinaisonJoueur();

}
