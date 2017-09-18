package com.projet.jeu.jeux;

import java.io.IOException;
import java.util.ArrayList;

import com.projet.jeu.context.ApplicationContext;

public class JeuRecherche extends Jeu {

	private String evaluationJoueur=" ";
	private String evaluationComput=" ";
	protected ArrayList<Integer> combSecreteJoueur = new ArrayList<Integer>();
	protected ArrayList<Integer> combEssaiJoueur = new ArrayList<Integer>();

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
			lancerJeuDuel();
			break;
		}
	}

	@Override
	void lancerJeuChallenge() {
		System.out.println("Bienvenu au Recherche +//- || Mode Challenger");
		super.combSecrete = super.setCombSecrete();
		super.afficheSolution(super.combSecrete);
		// D'abord un loop pour les essais
		for (int i = 0; i < applicationContext.getNombreEssais(); i++) {
			super.combEssai = setCombEssai();
			if (!isSecretCobination(combEssai, combSecrete)) {
				String avancement = evaluateCombinaison(combSecrete, combEssai);
				System.out.println("Piste: " + avancement);
			} else {
				System.out.println("Bravo! Vous avez trouvé la combinaison secrete en " + (i + 1) + " essais");
				break;
			}
			if (i >= applicationContext.getNombreEssais() - 1) {
				System.out.println("MEC! Il n'y a plus d'essais possibles!");
			}
		}

	}

	@Override
	void lancerJeuDefenseur() {
		super.combEssai = null;
		System.out.println("Bienvenu au Recherche +//-  || Mode Defenseur");
		super.combSecrete = super.saissieCombSecrete();
		super.afficheSolution(super.combSecrete);
		for (int i = 0; i < applicationContext.getNombreEssais(); i++) {
			if (combEssai == null) {
				combEssai = super.setCombSecrete();
			}
			System.out.println("La combinaison de l'ordi est: " + combEssai);
			if (super.isSecretCobination(combEssai, combSecrete)) {
				System.out.println("Bravo! Vous avez trouvé la combinaison secrete en " + (i + 1) + " essais");
				break;
			}
			this.evaluationJoueur = evaluateCombinaisonJoueur();
			System.out.println("Piste: " + this.evaluationJoueur);
			super.combEssai = this.penserCombi(evaluationJoueur);
			if (i >= applicationContext.getNombreEssais() - 1) {
				System.out.println("MEC! Il n'y a plus d'essais possibles!");
			}
		}

	}

	@Override
	void lancerJeuDuel() {
		System.out.println("Bienvenu au Recherche +//-  || Mode Duel");
		//Saisir les combinaisons secretes
		super.combSecrete = super.setCombSecrete();
		this.combSecreteJoueur = super.saissieCombSecrete();
		//Printer les combis
		if (applicationContext.isModeDevelop()) {
			System.out.println("Combinaison de l'ordi:");
			super.afficheSolution(combSecrete);
			System.out.println("Combinaison du joueur:");
			super.afficheSolution(combSecreteJoueur);
		}
		combEssai = null; combEssaiJoueur = null;
		//init des compteus pour les deux jueurs
		int i_comput = 0;
		int i_joueur = 0;
		do {
			//Augmentons les compteurs
			i_comput++;
			i_joueur++;
			//D'abord l'ordinateur commence avec la combinaison secrete du joueur
			System.out.println("Oridanteur attaque, joueur defense:");
			if (combEssai == null) {
				combEssai = super.setCombSecrete();
			}
			System.out.println("L'essai de l'ordi est: " + combEssai);
			if (super.isSecretCobination(combEssai,combSecreteJoueur)) {
				System.out.println("L'ordinateur a trouvé la combinaison secrete en " + i_comput + " essais");
				break;
			}
			this.evaluationComput = evaluateCombinaisonJoueur();
			System.out.println("Piste: " + this.evaluationComput);
			super.combEssai = this.penserCombi(evaluationComput);			
			
			//Ensuite, c'est le joueur qui devine la combinaison du pc
			System.out.println("Joueur attaque, ordinateur defense:");	
			System.out.println("L'essai du joueur est: " + combEssaiJoueur+" et piste: "+evaluationJoueur);
			combEssaiJoueur = super.setCombEssai();			
			if (super.isSecretCobination(combEssaiJoueur, combSecrete)) {
				System.out.println("Bravo! Vous avez trouvé la combinaison secrete en " + i_joueur + " essais");
				break;
			}
			this.evaluationJoueur = evaluateCombinaison(combSecrete, combEssaiJoueur);
			System.out.println("Piste: " + this.evaluationJoueur);		
			
			//Verif des essais
			if (i_comput >= applicationContext.getNombreEssais() - 1) {
				System.out.println("BRAVO! L'ordinateur n'a plus d'essais!");
			} else if (i_joueur >= applicationContext.getNombreEssais() -1) {
				System.out.println("MEC! Il n'y a plus d'essais possibles!L'ordi gagne!");
			}			
			
		} while (i_comput < applicationContext.getNombreEssais() ||
				i_joueur < applicationContext.getNombreEssais());
		

	}


	@Override
	String evaluateCombinaison(ArrayList<Integer> combSecrete, ArrayList<Integer> combEssai) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < combEssai.size(); i++) {
			if (combSecrete.get(i) > combEssai.get(i)) {
				result.append("> ");
			} else if (combSecrete.get(i) == combEssai.get(i)) {
				result.append("= ");
			} else if (combSecrete.get(i) < combEssai.get(i)) {
				result.append("< ");
			}
		}
		return result.toString();
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

	public ArrayList<Integer> penserCombi(String evaluationJoueur) {
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
