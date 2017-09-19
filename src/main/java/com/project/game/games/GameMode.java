package com.project.game.games;

/*
 * Enum pour gerer les 3 modes de jeu
 * 
 * @author junker52
 */
public enum GameMode {
	DEFENSE("defense"),
	CHALLENGE("challenge"),
	DUEL("duel");
	
	private String descripMode;
	
	private GameMode(String mod) {
		this.descripMode = mod;
	}

	public String getDescripMode() {
		return descripMode;
	}
	
	
}
