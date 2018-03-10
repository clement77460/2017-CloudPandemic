package fr.efrei.nouvellonJaworski.launcher;

import fr.efrei.nouvellonJaworski.vue.Partie;

public class Lanceur {

	public static void main(String[] args) throws InterruptedException {
		//Controller controllerPartie=new Controller(2,10);
		// generation aleatoire de la ville et de l'habitant pour la premiere infect
		//controllerPartie.lancementPartie();
		//FakeStatistique test=new FakeStatistique();
		Partie game=new Partie(100);
		game.boucleJeu(); 
	}

}
