package com.projet.jeu.jeux;

import com.projet.jeu.context.ApplicationContext;

/*
 * Classe abstraite de base pour creer les jeux Recherche et Mastermind
 * 
 * @author junker52
 */
public abstract class Jeu {
	 
	protected byte jeu_solution[] = new byte[4];
	protected byte jeu_essai[] = new byte[4];
	protected JeuMode jeu_mode;
	protected ApplicationContext applicationContext;
	
	public Jeu() {
		
	}
	//Getters et Setters
	public byte[] getJeu_solution() {
		return jeu_solution;
	}
	public void setJeu_solution(byte[] jeu_solution) {
		this.jeu_solution = jeu_solution;
	}
	public byte[] getJeu_essai() {
		return jeu_essai;
	}
	public void setJeu_essai(byte[] jeu_essai) {
		this.jeu_essai = jeu_essai;
	}
	public JeuMode getJeu_mode() {
		return jeu_mode;
	}
	public void setJeu_mode(JeuMode jeu_mode) {
		this.jeu_mode = jeu_mode;
	}
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	
	
	
	
	
	
	

}
