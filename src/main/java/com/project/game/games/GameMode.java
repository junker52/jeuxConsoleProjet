package com.project.game.games;

/**
 * Enum to manage three game modes
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
