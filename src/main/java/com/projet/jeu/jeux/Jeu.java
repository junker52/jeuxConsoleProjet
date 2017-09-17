package com.projet.jeu.jeux;

import java.util.ArrayList;

import com.projet.jeu.context.ApplicationContext;

/*
 * Classe abstraite de base pour creer les jeux Recherche et Mastermind
 * 
 * @author junker52
 */
public abstract class Jeu {
	 
	protected ArrayList<Integer> combSecrete;
	protected ArrayList<Integer> combEssai;
	protected ApplicationContext applicationContext;
	
	public Jeu(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.combSecrete = setCombSecrete();
		
	}
	
	
	//Getters et Setters

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	
	//Methodes
	public ArrayList<Integer> setCombSecrete(){
		ArrayList<Integer> combinaison = new ArrayList<Integer>();
		for (int i = 0; i < applicationContext.getNombreCases(); i++) {
			combinaison.add((int) (Math.random() * 10));			
		}
		//Gestion du mode developpeur
		if (applicationContext.isModeDevelop()) {
			System.out.println(combinaison.toString()+" \n");
		}
		return combinaison;
	}
	
	public ArrayList<Integer> setCombEssai() {
		ArrayList<Integer> combinaison = new ArrayList<Integer>();
		System.out.print("Saisissez une combinaison de "+applicationContext.getNombreCases()+" chifres: \t");
		try {
			int combInt = Integer.valueOf(ApplicationContext.getLecteur().readLine());
			String combString = Integer.toString(combInt);
			for (int i = 0; i < applicationContext.getNombreCases(); i++) {
				int chifre =  Integer.parseInt(""+combString.charAt(i));
				combinaison.add(chifre);
			}
		} catch (Exception e) {
			System.out.println("Erreur de saissie. Reessayez seulement avec des chifres.");
			combinaison = setCombEssai();
		}
		
		
		return combinaison;
		
	}
	
	public boolean isSecretCobination(ArrayList<Integer> combinasion) {
		boolean winner = false;
		if (combSecrete.equals(combinasion)) {
			winner = true;
		} 
		return winner;
	}
	
	abstract void lancerJeuChallenge();
	abstract String evaluateCombinaison();
	
	
	
	
	
	
	
	

}
