package fr.efrei.nouvellonJaworski.launcher;

import java.util.Scanner;

import fr.efrei.nouvellonJaworski.partie.PartieLocale;
import fr.efrei.nouvellonJaworski.partie.PartieNoeud;
import fr.efrei.nouvellonJaworski.partie.PartieTerminal;

public class Lanceur {

	public static void main(String[] args){
		Scanner scanner=new Scanner(System.in);
		System.out.println("choix du type d'application :");
		System.out.println(" 0 : Partie Locale \n 1: Partie Noeud \n 2: Partie Terminale");
		switch(scanner.nextInt()) {
			case 0:
				PartieLocale game=new PartieLocale(100);
				game.boucleJeu(); 
				scanner.close();
				break;
			case 1:
				PartieNoeud gameNode=new PartieNoeud(100,"178.62.119.191",4242);
				gameNode.startSimulation();
				scanner.close();
				System.exit(0);
				break;
			default:
				new PartieTerminal(100,"178.62.119.191",8081);
				scanner.close();
				break;
		}
	}

}
