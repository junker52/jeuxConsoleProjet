package com.projet.jeu.jeux;

import java.io.IOException;
import java.util.ArrayList;

import com.projet.jeu.context.ApplicationContext;

public class JeuRecherche extends Jeu {

	private String evaluationJoueur;

	public JeuRecherche(ApplicationContext a) {
		super(a);
		switch (applicationContext.getJeuMode()) {
		case CHALLENGE:
			lancerJeuChallenge();
			break;
		case DEFENSEUR:
			lancerJeuDefenseur();
			break;
		case DUEL:

			break;
		}
	}

	@Override
	void lancerJeuChallenge() {
		System.out.println("Bienvenu au Recherche +//- || Mode Challenger");
		super.combSecrete = super.setCombSecrete();
		super.afficheSolution();
		// D'abord un loop pour les essais
		for (int i = 0; i < applicationContext.getNombreEssais(); i++) {
			super.combEssai = setCombEssai();
			if (!isSecretCobination(combEssai)) {
				String avancement = evaluateCombinaison();
				System.out.println("Piste: " + avancement);
			} else {
				System.out.println("Bravo! Vous avez trouvé la combinaison secrete en " + (i + 1) + " essais");
				break;
			}
		}

	}

	@Override
	String evaluateCombinaison() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < combEssai.size(); i++) {
			if (combSecrete.get(i) > combEssai.get(i)) {
				result.append("+ ");
			} else if (combSecrete.get(i) == combEssai.get(i)) {
				result.append("= ");
			} else if (combSecrete.get(i) < combEssai.get(i)) {
				result.append("< ");
			}
		}
		return result.toString();
	}

	@Override
	void lancerJeuDefenseur() {
		super.combEssai = null;
		System.out.println("Bienvenu au Recherche +//-  || Mode Defenseur");
		super.combSecrete = super.saissieCombSecrete();
		super.afficheSolution();
		for (int i = 0; i < applicationContext.getNombreEssais(); i++) {
			if (combEssai == null) {
				combEssai = super.setCombSecrete();
			}
			System.out.println("La combinaison de l'ordi est: " + combEssai);
			if (super.isSecretCobination(combEssai)) {
				System.out.println("Bravo! Vous avez trouvé la combinaison secrete en " + (i + 1) +" essais");
				break;
			}
			this.evaluationJoueur = evaluateCombinaisonJoueur();
			System.out.println("Piste: " + this.evaluationJoueur);
			super.combEssai = this.penserCombi();
		}

	}

	@Override
	void lancerJeuDuel() {
		// TODO Auto-generated method stub

	}

	@Override
	String evaluateCombinaisonJoueur() {
		System.out.println("Evaluez la combinaison proposée (>,<,=): ");
		String respo = " ";
		try {
			boolean verif_string = true;
			respo = (String) ApplicationContext.getLecteur().readLine();
			StringBuffer sb = new StringBuffer(respo);
			for (int i = 0; i < applicationContext.getNombreCases(); i++) {
				if (sb.charAt(i) != '>' && sb.charAt(i) != '<' && sb.charAt(i) != '=') {
					verif_string = false;
				}
			}
			// Si la verif ne se passe pas bien, on relance la methode.
			if (verif_string == false) {
				System.out.println("Evaluation invalide, reessayez!");
				respo = evaluateCombinaisonJoueur();
			}
		} catch (IOException e) {
			System.out.println("Evaluation invalide, reessayez!");
			respo = evaluateCombinaisonJoueur();
		}
		return respo;
	}

	public ArrayList<Integer> penserCombi() {
		ArrayList<Integer> combi = new ArrayList<Integer>();
		for (int i = 0; i < applicationContext.getNombreCases(); i++) {
			// À ce moment, on n'a pas encore récrit la donnée combEssai
			if (evaluationJoueur.charAt(i) == '>') {
				combi.add(combEssai.get(i) + 1);
			} else if (evaluationJoueur.charAt(i) == '<') {
				combi.add(combEssai.get(i) - 1);
			} else if (evaluationJoueur.charAt(i) == '=') {
				combi.add(combEssai.get(i));
			}
		}
		return combi;
	}

}
