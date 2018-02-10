package fr.efrei.nouvellonJaworski.vue;
import fr.efrei.nouvellonJaworski.model.entities.Statistique;
import java.util.Scanner;

import fr.efrei.nouvellonJaworski.controller.*;
public class Partie {
	private Controller controllerPartie;
	public Partie(Controller controllerPartie) {
		this.controllerPartie=controllerPartie;
	}
	public void askAction() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("******************************************");
			System.out.println("1. Try to infect someone randomly");
			System.out.println("2. try to heal someone randomly");
			System.out.println("3. try to isolate someone randomly");
			System.out.println("4. try to kill someone randomly");
			System.out.println("5. quit");
			controllerPartie.choixAction(sc.nextInt());
		}
	}
	public void printCarac(Statistique stats) {
		System.out.println(stats);
	}
}
