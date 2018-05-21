package fr.efrei.nouvellonJaworski.launcher;

import fr.efrei.nouvellonJaworski.vue.PartieLocale;
import fr.efrei.nouvellonJaworski.vue.PartieNoeud;
import fr.efrei.nouvellonJaworski.vue.PartieTerminal;

public class Lanceur {

	public static void main(String[] args){
		int i=3;
		if(i==0) {
			PartieLocale game=new PartieLocale(100);
			game.boucleJeu(); 
		}else if(i==1){
			PartieNoeud game=new PartieNoeud(100);
			game.boucleJeu();
		}else {
			PartieTerminal game=new PartieTerminal(100);
			game.boucleJeu();
		}
	}

}
