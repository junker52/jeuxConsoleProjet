package com.projet.jeu.jeux;

/*
 * Enum pour gerer les 3 modes de jeu
 * 
 * @author junker52
 */
public enum JeuMode {
	DEFENSEUR("defenseur"),
	CHALLENGE("challenge"),
	DUEL("duel");
	
	private String descripMode;
	
	private JeuMode(String mod) {
		this.descripMode = mod;
	}

	public String getDescripMode() {
		return descripMode;
	}
	
	
}
