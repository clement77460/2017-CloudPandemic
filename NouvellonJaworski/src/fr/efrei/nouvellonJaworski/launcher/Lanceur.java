package fr.efrei.nouvellonJaworski.launcher;

import fr.efrei.nouvellonJaworski.vue.Partie;
import fr.efrei.nouvellonJaworski.vue.PartieNoeud;

public class Lanceur {

	public static void main(String[] args){
		int i=1;
		if(i==0) {
			Partie game=new Partie(100);
			game.boucleJeu(); 
		}else {
			PartieNoeud game=new PartieNoeud(100);
			game.boucleJeu();
		}
	}

}
