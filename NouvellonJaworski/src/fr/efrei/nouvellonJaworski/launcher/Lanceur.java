package fr.efrei.nouvellonJaworski.launcher;

import fr.efrei.nouvellonJaworski.vue.Partie;

public class Lanceur {

	public static void main(String[] args){
		Partie game=new Partie(100);
		game.boucleJeu();  
	}

}
