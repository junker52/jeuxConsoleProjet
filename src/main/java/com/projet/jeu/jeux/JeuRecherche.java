package com.projet.jeu.jeux;

import com.projet.jeu.context.ApplicationContext;

public class JeuRecherche extends Jeu {

	public JeuRecherche(ApplicationContext a) {
		super(a);
		switch (applicationContext.getJeuMode()) {
		case CHALLENGE:
			lancerJeuChallenge();
			break;
		case DEFENSEUR:

			break;
		case DUEL:

			break;
		}
	}

	@Override
	void lancerJeuChallenge() {
		System.out.println("Bienvenu au Recherche +//-");
		// D'abord un loop pour les essais
		for (int i = 0; i < applicationContext.getNombreEssais(); i++) {
			super.combEssai = setCombEssai();
			if (!isSecretCobination(combEssai)) {
				String avancement = evaluateCombinaison();
				System.out.println("Piste: " + avancement);
			} else {
				System.out.println("Bravo! Vous avez trouvé la combinaison secrete en " +( i+1 )+ " essais");
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
}
