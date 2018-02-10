package fr.efrei.nouvellonJaworski.launcher;
import fr.efrei.nouvellonJaworski.vue.*;



import fr.efrei.nouvellonJaworski.controller.*;

public class Launcher {

	public static void main(String[] args) {
		Controller controllerPartie=new Controller(2,10);
		// generation aleatoire de la ville et de l'habitant pour la premiere infect
		controllerPartie.lancementPartie();
	}

}
